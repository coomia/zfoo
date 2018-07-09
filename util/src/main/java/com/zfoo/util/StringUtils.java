package com.zfoo.util;

/**
 * Operations on {@link String} that are null safe and thread safe.
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 11:12
 */
public abstract class StringUtils {

    public static final String EMPTY = "";//The empty String

    public static final String SPACE = " ";//A String for a space character.

    public static final String COMMA = ",";// [com·ma || 'kɒmə] n.  逗点; 逗号
    public static final String COMMA_REGEX = ",|，";

    public static final String PERIOD = ".";// 句号

    public static final String LEFT_SQUARE_BRACKET = "[";//左方括号

    public static final String RIGHT_SQUARE_BRACKET = "]";//右方括号

    public static final String COLON = ":";//冒号[co·lon || 'kəʊlən]
    public static final String COLON_REGEX = ":|：";

    public static final String SEMICOLON = ";";//分号['semi'kәulәn]
    public static final String SEMICOLON_REGEX = ";|；";

    public static final String QUOTATION_MARK = "\"";//引号[quo·ta·tion || kwəʊ'teɪʃn]

    public static final String ELLIPSIS = "...";//省略号

    public static final String EXCLAMATION_POINT = "!";//感叹号

    public static final String DASH = "-";//破折号

    public static final String QUESTION_MARK = "?";//问好

    public static final String HYPHEN = "-";//连接号，连接号与破折号的区别是，连接号的两头不用空格

    public static final String SLASH = "/";//斜线号

    public static final String BACK_SLASH = "\\";//反斜线号

    public static final String VERTICAL_BAR = "|";// 竖线
    public static final String VERTICAL_BAR_REGEX = "\\|";

    public static final String SHARP = "#";
    public static final String SHARP_REGEX = "\\#";

    public static final String DOLLAR = "$";// 美元符号

    public static final String MULTIPLE_HYPHENS = "-----------------------------------------------------------------------";


    public static final int INDEX_NOT_FOUND = -1;//Represents a failed index search.


    // Empty checks
    //-----------------------------------------------------------------------

    /**
     * Checks if a CharSequence is empty ("") or null.
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * </pre>
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    /**
     * StringUtils.isBlank(null)=true
     * StringUtils.isBlank("")=true
     * StringUtils.isBlank("    ")=true
     * StringUtils.isBlank(" b ")=false
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        if (isEmpty(cs)) {
            return true;
        }
        int length = cs.length();
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


    // Case conversion
    //-----------------------------------------------------------------------

    /**
     * 首字母小写(capitalize  ['kæpɪtlaɪz] vt.以大写字母写,【根】cap＝head（头）)
     *
     * @param str 被转换的字符串，可以为null
     * @return 首字母小写的字符串，如果str为null，则返回null
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    /**
     * Capitalize a {@code String}, changing the first letter to
     * upper case as per {@link Character#toUpperCase(char)}.
     * No other letters are changed.
     *
     * @param str the {@code String} to capitalize, may be {@code null}
     * @return the capitalized {@code String}, or {@code null} if the supplied
     * string is {@code null}
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(0)));
        }
        sb.append(str.substring(1));
        return sb.toString();
    }


    // SubStringAfter/SubStringBefore
    //-----------------------------------------------------------------------

    /**
     * 从第一个分隔符开始分割
     * <p>Gets the substring before the first occurrence of a separator.
     * The separator is not returned.</p>
     * <p>
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * A {@code null} separator will return the input string.</p>
     * <p>
     * <p>If nothing is found, the string input is returned.</p>
     * <p>
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring before the first occurrence of the separator,
     * {@code null} if null String input
     * @since 2.0
     */
    public static String substringBeforeFirst(final String str, final String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String substringBeforeLast(final String str, final String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 从第一个分隔符开始分割
     * <p>Gets the substring after the first occurrence of a separator.
     * The separator is not returned.</p>
     * <p>
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * A {@code null} separator will return the empty string if the
     * input string is not {@code null}.</p>
     * <p>
     * <p>If nothing is found, the empty string is returned.</p>
     * <p>
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the first occurrence of the separator,
     * {@code null} if null String input
     * @since 2.0
     */
    public static String substringAfterFirst(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * <p>Gets the substring after the last occurrence of a separator.
     * The separator is not returned.</p>
     * <p>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the last occurrence of the separator,
     * {@code null} if null String input
     * @since 2.0
     */
    public static String substringAfterLast(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

}
