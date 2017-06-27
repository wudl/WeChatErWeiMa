package com.plan.my.mytoolslibrary.toolutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 正则判断
 *
 * Created by wudl on 2016/9/9 15:17
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class RegexUtils {

    private static final String TAG = RegexUtils.class.getSimpleName();

    /**
     * 只允许字母和数字 [^a-zA-Z0-9]
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 [^a-zA-Z0-9]
        String regEx = "[^A-Za-z0-9]+$"; // 只允许英文字符和数字
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 手机号码检查
     */
    public static Boolean phonenumberCheck(String phoneNumber) {

        // 表达式对象
        Pattern p = Pattern.compile("^(1)[0-9]{10}$");

        // 创建 Matcher 对象
        Matcher m = p.matcher(phoneNumber);

        if (!m.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 是否全数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 固话号码检查
     */
    public static Boolean telPhoneCheck(String phoneNumber) {
        // 表达式对象
        Pattern p = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})$");

        // 创建 Matcher 对象
        Matcher m = p.matcher(phoneNumber);

        if (!m.matches()) {
            return false;
        }
        return true;
    }


    /**
     * Email检查
     */
    public static Boolean checkEmail(String email) {
        // 表达式对象     ^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$ ");
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

        // 创建 Matcher 对象
        Matcher m = p.matcher(email);

        if (!m.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 验证密码（帐号管理）
     *
     * @param pwd
     * @return
     */
    public static Boolean checkPwd(String pwd) {

        // 表达式对象
        Pattern p = Pattern.compile("[0-9a-zA-Z]{6,}$");

        // 创建 Matcher 对象
        Matcher m = p.matcher(pwd);

        if (!m.matches()) {
            return false;
        }
        return true;
    }


}
