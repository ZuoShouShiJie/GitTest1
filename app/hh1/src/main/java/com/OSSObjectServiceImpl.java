package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;

@Service
public class OSSObjectServiceImpl  {/*
    private static final Logger LOGGER        = Logger.getLogger(OSSObjectServiceImpl.class);
    private static final long   PART_SIZE     = ResourceUtil.getPartSize() * 1024 * 1024L;   // 每个Part的大小，最小为5MB
    private static final int    CONCURRENCIES = ResourceUtil.getConcurrencies();             // 上传Part的并发线程数。

    @Override
    public PageReturnVO putObject(File file, String key) throws InterruptedException, FileNotFoundException {
        PageReturnVO prVO = new PageReturnVO();
        LOGGER.info("开始获取client...");
        OSSClient client = OSSUtils.getOSSClient();
        LOGGER.info("获取client成功...");
        String oldFileName = file.getName();
        //      String extName = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
        //      String newFileName=UUID. randomUUID().toString();
        String bucketName = ResourceUtil.getBucketName();

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        InputStream input = new FileInputStream(file);
        LOGGER.info("开始上传oss...");
        client.putObject(bucketName, key, input, objectMeta);
        LOGGER.info("上传oss成功...");

        prVO.setStrUrl(ResourceUtil.getCdnDomain().concat(key));
        prVO.setStrFileName(oldFileName);
        return prVO;
    }

    @Override
    public PageReturnVO getObject(String path, String key) throws InterruptedException, FileNotFoundException {
        PageReturnVO prVO = new PageReturnVO();
        LOGGER.info("开始获取client...");
        OSSClient client = OSSUtils.getOSSClient();
        LOGGER.info("获取client成功...");
        String bucketName = ResourceUtil.getBucketName();

        LOGGER.info("下载oss...");
        client.getObject(new GetObjectRequest(bucketName, key), new File(path));
        LOGGER.info("下载oss成功...");
        prVO.setStrUrl(ResourceUtil.getCdnDomain().concat(File.separator).concat(key));
        prVO.setStrFileName(path);
        return prVO;
    }

    @Override
    public void deleteFile(String key) {
        LOGGER.info("开始获取client...");
        OSSClient client = OSSUtils.getOSSClient();
        LOGGER.info("获取client成功...");
        String bucketName = ResourceUtil.getBucketName();
        LOGGER.info("开始删除对象..." + key);
        client.deleteObject(bucketName, key);
        LOGGER.info("删除对象..." + key + "成功");
    }

    @Override
    public PageReturnVO uploadBigFile(File file, String key) throws InterruptedException, FileNotFoundException {
        PageReturnVO prVO = new PageReturnVO();
        LOGGER.info("开始获取client...");
        OSSClient client = OSSUtils.getOSSClient();
        LOGGER.info("获取client成功...");
        String oldFileName = file.getName();
        //String extName = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
        //String newFileName=UUID.randomUUID().toString();
        String bucketName = ResourceUtil.getBucketName();
        int partCount = calPartCount(file);

        LOGGER.info("开始上传分块文件...");
        if (partCount <= 1) {
            return putObjectNotJpg(file, key);
        }
        String uploadId = initMultipartUpload(client, bucketName, key);
        ExecutorService pool = Executors.newFixedThreadPool(CONCURRENCIES);
        List<PartETag> eTags = Collections.synchronizedList(new ArrayList<PartETag>());

        for (int i = 0; i < partCount; i++) {
            long start = PART_SIZE * i;
            long curPartSize = PART_SIZE < file.length() - start ? PART_SIZE : file.length() - start;
            pool.execute(new UploadPartThread(client, bucketName, key, file, uploadId, i + 1, PART_SIZE * i, curPartSize, eTags));
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        }

        if (eTags.size() != partCount) {
            throw new RuntimeException("Multipart上传失败，有Part未上传成功。");
        }

        completeMultipartUpload(client, bucketName, key, uploadId, eTags);
        LOGGER.info("上传分块文件成功...");
        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365 * (ResourceUtil.getExpiration()));
        prVO.setStrUrl(client.generatePresignedUrl(bucketName, key, expiration).toString().split("\\?")[0]);
        prVO.setStrFileName(oldFileName);
        return prVO;
    }

    // 根据文件的大小和每个Part的大小计算需要划分的Part个数。
    private static int calPartCount(File f) {
        int partCount = (int) (f.length() / PART_SIZE);
        if (f.length() % PART_SIZE != 0) {
            partCount++;
        }
        return partCount;
    }

    // 初始化一个Multi-part upload请求。
    private static String initMultipartUpload(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        InitiateMultipartUploadRequest initUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult initResult = client.initiateMultipartUpload(initUploadRequest);
        String uploadId = initResult.getUploadId();
        return uploadId;
    }

    // 完成一个multi-part请求。
    private static void completeMultipartUpload(OSSClient client, String bucketName, String key, String uploadId, List<PartETag> eTags) throws OSSException, ClientException {
        //为part按partnumber排序
        Collections.sort(eTags, new Comparator<PartETag>() {
            public int compare(PartETag arg0, PartETag arg1) {
                PartETag part1 = arg0;
                PartETag part2 = arg1;

                return part1.getPartNumber() - part2.getPartNumber();
            }
        });

        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, eTags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private PageReturnVO putObjectNotJpg(File file, String key) throws InterruptedException, FileNotFoundException {
        PageReturnVO prVO = new PageReturnVO();
        OSSClient client = OSSUtils.getOSSClient();
        String oldFileName = file.getName();
        //String extName = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
        //String newFileName=UUID.randomUUID().toString();
        String bucketName = ResourceUtil.getBucketName();

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        InputStream input = new FileInputStream(file);
        client.putObject(bucketName, key, input, objectMeta);

        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365 * (ResourceUtil.getExpiration()));
        prVO.setStrUrl(client.generatePresignedUrl(bucketName, key, expiration).toString().split("\\?")[0]);
        prVO.setStrFileName(oldFileName);
        return prVO;
    }
*/
}
