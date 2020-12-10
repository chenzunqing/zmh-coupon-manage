package com.zmh.coupon.util;


import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Wei.Guang
 * @create 2018-06-06 13:15
 **/
public class StringUtil {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";
    public static final String mailPattern = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final Pattern subAccountPattern = Pattern.compile("^[a-zA-Z][0-9a-zA-Z]{7,15}$");
    public static final Pattern subPasswordPattern = Pattern.compile("^[0-9a-zA-Z]{6,16}$");
    public static final int DEFAULT_LENGTH = 5;


    /**
     * 首字母变小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * 判断是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * 判断是否空白
     *
     * @param str
     * @return
     */
    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否不是空白
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断多个字符串全部是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkValue为 null 或者为 "" 时返回 defaultValue
     *
     * @param checkValue
     * @param defaultValue
     * @return
     */
    public static String isEmpty(String checkValue, String defaultValue) {
        return isEmpty(checkValue) ? defaultValue : checkValue;
    }

    /**
     * 字符串不为 null 而且不为 "" 并且等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndEquelsOther(String str, String other) {
        if (isEmpty(str)) {
            return false;
        }
        return str.equals(other);
    }

    /**
     * 字符串不为 null 而且不为 "" 并且不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndNotEquelsOther(String str, String... other) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < other.length; i++) {
            if (str.equals(other[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEquelsOther(String str, String... other) {
        for (int i = 0; i < other.length; i++) {
            if (other[i].equals(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为空
     *
     * @param strings
     * @return
     */
    public static boolean isNotEmpty(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较字符相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean equals(String value, String equals) {
        if (isAllEmpty(value, equals)) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return value.equals(equals);
    }

    /**
     * 比较字符串不相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean isNotEquals(String value, String equals) {
        return !equals(value, equals);
    }

    public static String[] split(String content, String separatorChars) {
        return splitWorker(content, separatorChars, -1, false);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * 消除转义字符
     *
     * @param str
     * @return
     */
    public static String escapeXML(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch (c) {
                case '\u00FF':
                case '\u0024':
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                default:
                    if (c >= '\u0000' && c <= '\u001F') {
                        break;
                    }
                    if (c >= '\uE000' && c <= '\uF8FF') {
                        break;
                    }
                    if (c >= '\uFFF0' && c <= '\uFFFF') {
                        break;
                    }
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串中特定模式的字符转换成map中对应的值
     *
     * @param s   需要转换的字符串
     * @param map 转换所需的键值对集合
     * @return 转换后的字符串
     */
    public static String replace(String s, Map<String, Object> map) {
        StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
        int cursor = 0;
        for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf("}", start)) != -1; ) {
            ret.append(s.substring(cursor, start)).append(map.get(s.substring(start + 2, end)));
            cursor = end + 1;
        }
        ret.append(s.substring(cursor, s.length()));
        return ret.toString();
    }

    public static String replace(String s, Object... objs) {
        if (objs == null || objs.length == 0) {
            return s;
        }
        if (s.indexOf("{}") == -1) {
            return s;
        }
        StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
        int cursor = 0;
        int index = 0;
        for (int start; (start = s.indexOf("{}", cursor)) != -1; ) {
            ret.append(s.substring(cursor, start));
            if (index < objs.length) {
                ret.append(objs[index]);
            } else {
                ret.append("{}");
            }
            cursor = start + 2;
            index++;
        }
        ret.append(s.substring(cursor, s.length()));
        return ret.toString();
    }

    /**
     * 字符串格式化工具,参数必须以{0}之类的样式标示出来.大括号中的数字从0开始。
     *
     * @param source 源字符串
     * @param params 需要替换的参数列表,写入时会调用每个参数的toString().
     * @return 替换完成的字符串。如果原始字符串为空或者参数为空那么将直接返回原始字符串。
     */
    public static String replaceArgs(String source, Object... params) {
        if (params == null || params.length == 0 || source == null || source.isEmpty()) {
            return source;
        }
        StringBuilder buff = new StringBuilder(source);
        StringBuilder temp = new StringBuilder();
        int startIndex = 0;
        int endIndex = 0;
        String param = null;
        for (int count = 0; count < params.length; count++) {
            if (params[count] == null) {
                param = null;
            } else {
                param = params[count].toString();
            }

            temp.delete(0, temp.length());
            temp.append("{");
            temp.append(count);
            temp.append("}");
            while (true) {
                startIndex = buff.indexOf(temp.toString(), endIndex);
                if (startIndex == -1) {
                    break;
                }
                endIndex = startIndex + temp.length();

                buff.replace(startIndex, endIndex, param == null ? "" : param);
            }
            startIndex = 0;
            endIndex = 0;
        }
        return buff.toString();
    }

    public static String substringBefore(final String s, final String separator) {
        if (isEmpty(s) || separator == null) {
            return s;
        }
        if (separator.isEmpty()) {
            return "";
        }
        final int pos = s.indexOf(separator);
        if (pos < 0) {
            return s;
        }
        return s.substring(0, pos);
    }

    public static String substringBetween(final String str, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        final int start = str.indexOf(open);
        if (start != -1) {
            final int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        final int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 转换为字节数组
     *
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 产生len长度的随机字符串
     *
     * @param len
     * @return
     */
    public static String generateStr(int len) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerStr(int length) {
        return generateMixStr(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperStr(int length) {
        return generateMixStr(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroStr(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixedLengthStr(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroStr(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 判断字符串是否包含某些字符
     *
     * @param sourceStr 源字符串
     * @param separator
     * @return
     */
    public static boolean contains(String sourceStr, String separator) {
        if (StringUtil.isBlank(sourceStr)) {
            return false;
        }
        return sourceStr.trim().contains(separator);
    }

    /**
     * 生成随机用户名，数字和字母组成
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return "tmz_" + val;
    }

    /**
     * 判断url是否为网络地址
     *
     * @param url
     * @return
     */
    public static boolean isNetUrl(String url) {
        boolean reault = false;
        if (url != null) {
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("rtsp") || url.toLowerCase().startsWith("mms")) {
                reault = true;
            }
        }
        return reault;
    }

    public static String generateUserName(String phone) {
        String str = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return phone + "_" + str;
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public static String generateOrderNo(String type) {
        String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Random random = new Random();
        String num = generateRandomNum(5);
        return type + temp + num;
    }

    /**
     * 生成订单号
     * @param type
     * @param sequence
     * @return
     */
    public static String generateTGOrderNo(String type, long sequence) {
        StringBuilder sb = new StringBuilder();
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        sb.append(type).append(timeStr).append(getSequence(sequence));
        return sb.toString();
    }

    /**
     * chinapay相关操作生成订单号
     *
     * @return
     */
    public static String generateDateTimeNo() {
        String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String num = generateRandomNum(5);
        return temp + num;
    }

    public static String generateRandomNum(int count) {
        Random random = new Random();
        StringBuffer flag = new StringBuffer();
        for (int i = 0; i < count; i++) {
            flag.append(numberChar.charAt(random.nextInt(9)));
        }
        return flag.toString();
    }

    /**
     * 获取方法中指定注解的value值返回
     *
     * @param method               方法名
     * @param validationParamValue 注解的类名
     * @return
     */
    public static String getMethodAnnotationOne(Method method, String validationParamValue) {
        String retParam = null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                String str = parameterAnnotations[i][j].toString();
                if (str.indexOf(validationParamValue) > 0) {
                    retParam = str.substring(str.indexOf("=") + 1, str.indexOf(")"));
                }
            }
        }
        return retParam;
    }

    /**
     * 邮箱格式判断
     *
     * @param email
     * @return
     */
    public static Boolean isMail(String email) {
        if (isBlank(email)) {
            return Boolean.FALSE;
        }
        return Pattern.matches(mailPattern, email);
    }

    /**
     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s
     * @return
     */
    public static String removeAllBlank(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("[　*| *| *|//s*]*", "");
        }
        return result;
    }

    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s
     * @return
     */
    public static String trim(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
        }
        return result;
    }

    /**
     * 根据键值对填充字符串，如("hello {name}",{name:"xiaoming"})
     *
     * @param content
     * @param map
     * @return
     */
    public static String renderString(String content, Map<String, String> map) {
        Set<Map.Entry<String, String>> sets = map.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue());
        }
        return content;
    }




    public static String replace(String str, String content, int startIndex, int endIndex){
        return str.substring(0, startIndex) + content + str.substring(endIndex);
    }

    /**
     * 判断是否是英文数字组成
     * @param str
     * @return
     */
    public static boolean isPassEnligsh(final String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return Boolean.FALSE;
        }
        return subPasswordPattern.matcher(str).matches();
    }

    /**
     * 判断是否是英文数字组成
     * @param str
     * @return
     */
    public static boolean isAccountEnligsh(final String str) {
        if (Objects.isNull(str) || str.length() == 0) {
            return Boolean.FALSE;
        }
        return subAccountPattern.matcher(str).matches();
    }

    public static String getSequence(long seq) {
        String str = String.valueOf(seq);
        int len = str.length();
        if (len >= DEFAULT_LENGTH) {
            // 取决于业务规模,应该不会到达5
            return str;
        }
        return String.format("%05d", seq);
    }



    public static void main(String[] args) {
        System.out.println(isAccountEnligsh("e183454545"));
    }
}
