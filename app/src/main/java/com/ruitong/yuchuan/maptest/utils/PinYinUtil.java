package com.ruitong.yuchuan.maptest.utils;

/**
 * Created by Administrator on 2016/11/16.
 */

import com.orhanobut.logger.Logger;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author  : Robert robert@xiaobei668.com
 * @version : 1.00
 * Create Time : 2011-3-22-下午07:04:30
 * Description :
 *              处理汉字和对应拼音转换的工具类
 * History：
 *  Editor       version      Time               Operation    Description*
 *
 *
 */
public class PinYinUtil {

    /**
     *
     * @param src
     * @return
     * author  : Robert
     * about version ：1.00
     * create time   : 2011-3-22-下午07:04:27
     * Description ：
     *             传入汉字字符串，拼接成对应的拼音,返回拼音的集合
     */
    public static Set<String> getPinYinSet(String src){
        Set<String> lstResult = new HashSet<String>();
        char[] t1 = null;  //字符串转换成char数组
        t1 = src.toCharArray();

        //①迭代汉字
        for(char ch : t1){
            String s[] = getPinYin(ch);
            Set<String> lstNew = new HashSet<String>();
            //②迭代每个汉字的拼音数组
            for(String str : s){
                if(lstResult.size()==0){
                    lstNew.add(str);
                }else{
                    for(String ss : lstResult){
                        ss += str;
                        lstNew.add(ss);
                    }
                }
            }
            lstResult.clear();
            lstResult = lstNew;
        }

        return lstResult;
    }

    public static void main(String[] args) {
        Set<String> lst = PinYinUtil.getPinYinSet("迭代每个汉字的拼音数组,该分享来自程序员之家");
        for (String string : lst) {
            Logger.i(string);
        }
    }

    /**
     *
     * @param src
     * @return
     * author  : Robert
     * about version ：1.00
     * create time   : 2011-3-22-下午02:21:42
     * Description ：
     *             传入中文汉字，转换出对应拼音
     *             注：出现同音字，默认选择汉字全拼的第一种读音
     */
    public static String getPinYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                // Logger.i(t1[i]);
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4;
    }

    /**
     * @param src
     * @return
     * author  : Robert
     * about version ：1.00
     * create time   : 2011-3-22-下午02:52:35
     * Description ：
     *             将单个汉字转换成汉语拼音，考虑到同音字问题，返回字符串数组的形式
     */
    public static String[] getPinYin(char src){
        char[] t1 = {src};
        String[] t2 = new String[t1.length];

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);

        // 判断能否为汉字字符
        if (Character.toString(t1[0]).matches("[\\u4E00-\\u9FA5]+")) {
            try {
                // 将汉字的几种全拼都存到t2数组中
                t2 = PinyinHelper.toHanyuPinyinStringArray(t1[0], t3);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            // 如果不是汉字字符，则把字符直接放入t2数组中
            t2[0] = String.valueOf(src);
        }
        return t2;
    }

    /**
     *
     * @param src
     * @return
     * author  : Robert
     * about version ：1.00
     * create time   : 2011-3-22-下午03:03:02
     * Description ：
     *             传入没有多音字的中文汉字，转换出对应拼音
     *             注：如果传入的中文中有任一同音字都会返回字符串信息：false
     */
    public static String getNoPolyphone(String src){
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                // Logger.i(t1[i]);
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
                    if(t2.length>1){
                        return "false";
                    }else{
                        t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                    }
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4;
    }


}