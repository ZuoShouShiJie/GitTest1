import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by daixiaohu on 2018/8/9.
 */
public class Test2  {
    public static void main(String[] args) throws Exception {

        // 使用反射,遍历某个类的所有的构造方法,属性,方法,打印到控制台

        System.out.println("输入要遍历的类：");
        Scanner can = new Scanner(System.in);

        String str = can.next();//cn.zhangwendi.day7.Student

        String[] str1 = str.split("#");
        Class clazz = Class.forName(str1[0]);

        //遍历构造方法
        System.out.println("构造方法：");
        Constructor[] con=clazz.getDeclaredConstructors();
        for (Constructor con2 : con) {
            System.out.println(con2.getName());
        }

        //遍历方法
        System.out.println("方法：");
        Method[] meth=clazz.getDeclaredMethods();
        for (Method meth2 : meth) {
            System.out.println(meth2.getName());
        }

        //遍历属性
        Field[] field=clazz.getDeclaredFields();
        System.out.println("属性：");

        for (Field field2 : field) {
            field2.setAccessible(true);
            System.out.println(field2);
        }

    }

    public static final String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }
}
