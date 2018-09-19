package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daixiaohu on 2018/3/6.
 */
public class CommonUtils {
    /**
     * 生成32位随机数
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    /**
     * 数据写入文件
     * @param rows
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean createTxtFile(List<String> rows, String filePath, String fileName) {
        boolean flag = true;
        try {
            String fullPath = filePath + fileName + ".txt";
            File file = new File(fullPath);
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file = new File(fullPath);
            file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(rows);
            oos.close();

       /*     FileWriter out = new FileWriter("D:\\1.txt", true);
            out.write("abc");
            out.write("\r\n");
            out.write("def");
            out.flush();
            out.close();*/


        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean  checkNum(String text) {
        Pattern patternSfzhm1 = Pattern
                .compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        Pattern patternSfzhm2 = Pattern
                .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher matcherSfzhm1 = patternSfzhm1.matcher(text);
        Matcher matcherSfzhm2 = patternSfzhm2.matcher(text);
        if(!matcherSfzhm1.find() && !matcherSfzhm2.find())
            return false;
        else return true;

    }

}
