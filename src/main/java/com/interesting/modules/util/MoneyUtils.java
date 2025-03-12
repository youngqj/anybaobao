package com.interesting.modules.util;


import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuanzeyu
 * @since 2022/3/23 20:44
 */
public class MoneyUtils {
    private static String result = null;
    private static final String[] st1 = {"", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine",};
    private static final String[] st2 = {"hundred", "thousand", "million", "billion"};
    private static final String[] st3 = {"ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen",};
    private static final String[] st4 = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy",
            "eighty", "ninety"};
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
    private static final char[] RMB_NUMS = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static final String[] UNITS1 = {"元", "角", "分", "整"};
    private static final String[] UNITS2 = {"", "拾", "佰", "仟"};
    private static final String[] UNITS3 = {"", "万", "亿"};

    public static void main(String[] args) {
        // 金额千分位
        System.out.println(fmtMicrometer("111.12"));
        // 中文大写金额
        System.out.println(convertChineseAmount("111.0"));
        // 英文金额
        System.out.println(convertEnglishAmount(39270, "USD"));
    }

    /**
     * 数字金额(元、分)转为英文
     * @param number 数字金额
     * @param currency 币种
     * @return 英文金额
     */
    public static String convertEnglishAmount(double number, String currency) {
        if ("USD".equals(currency)) {
            return convert(number, "dollars", "cents only");
        } else {
            return convert(number, "dollars", "cents only");
        }
    }

    public static String convert(double number, String dollarsUnit, String centsUnit) {
        int b1 = (int) number;
        String s1 = convert(b1, dollarsUnit);
        double b2 = number - b1;
        if (b2 != 0) {
            // 金额取两位小数
            int cents = (int) Math.round(b2 * 100);
            String s2 = convert(cents, centsUnit);
            s1 += " and " + s2;
            // s1 += " and " + cents + " " + centsUnit;
        } else {
            s1 += " only";
        }
        return s1;
    }

    private static String convert(int number, String unit) {
        int n = 1;
        result = "";
        while (number != 0) {
            number = convertSeg(number, n);
            n++;
        }
        result = result.trim().replaceAll("  ", " ");
        if (unit == null || unit.length() == 0) {
            return result;
        }
        return result + " " + unit;
    }

    private static int convertSeg(int number, int n) {
        int word;
        switch (n) {
            case 1:
                word = number % 100;
                pass(word);
                if (number > 100 && number % 100 != 0) {
                    show("and ");
                }
                number /= 100;
                break;

            case 2:
                word = number % 10;
                if (word != 0) {
                    show(" ");
                    show(st2[0]);
                    show(" ");
                    pass(word);
                }
                number /= 10;
                break;
            case 3:
                word = number % 1000;
                if (number < 1000) {
                    show(" ");
                    show(st2[1]);
                    show(" ");
                    int n1 = 1;
                    while (number != 0) {
                        number = convertSeg(number, n1);
                        n1++;
                    }
                } else if (word != 0) {
                    show(" ");
                    show(st2[1]);
                    show(" ");
                    pass(word);
                }
                number /= 1000;
                break;

            case 4:
                word = number % 1000;
                if (number < 1000) {
                    show(" ");
                    show(st2[2]);
                    show(" ");
                    int n2 = 1;
                    while (number != 0) {
                        number = convertSeg(number, n2);
                        n2++;
                    }
                } else if (word != 0) {
                    show(" ");
                    show(st2[2]);
                    show(" ");
                    pass(word);
                }
                number /= 1000;
                break;

            case 5:
                word = number % 100;
                if (word != 0) {
                    show(" ");
                    show(st2[3]);
                    show(" ");
                    pass(word);
                }
                number /= 100;
                break;
            default: {
                break;
            }
        }
        return number;
    }

    private static void pass(int number) {
        int word, q;
        if (number < 10) {
            show(st1[number]);
        } else if (number < 20) {
            show(st3[number - 10]);
        } else if (number < 100) {
            word = number % 10;
            if (word == 0) {
                q = number / 10;
                show(st4[q - 2]);
            } else {
                q = number / 10;
                show(st1[word]);
                show(" ");
                show(st4[q - 2]);
            }
        } else {
            int n1 = 1;
            while (number != 0) {
                number = convertSeg(number, n1);
                n1++;
            }
        }
    }

    private static void show(String s) {
        String st;
        st = result;
        result = s;
        result += st;
    }

    /**
     * 将金额（整数部分等于或少于 12 位，小数部分 2 位）转换为中文大写形式.
     *
     * @param amount 金额数字
     * @return 中文大写
     * @throws IllegalArgumentException
     */
    public static String convertChineseAmount(String amount) throws IllegalArgumentException {
        amount = fmtMicrometer(amount);
        // 去掉分隔符
        amount = amount.replace(",", "");
        // 验证金额正确性
        if ("0.00".equals(amount)) {
            throw new IllegalArgumentException("金额不能为零.");
        }
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);
        if (!matcher.find()) {
            throw new IllegalArgumentException("输入金额有误.");
        }
        // 整数部分
        String integer = matcher.group(1);
        // 小数部分
        String fraction = matcher.group(2);

        String result = "";
        if (!"0".equals(integer)) {
            // 整数部分
            result += integerToRmb(integer) + UNITS1[0];
        }
        if ("00".equals(fraction)) {
            // 添加[整]
            result += UNITS1[3];
        } else if (fraction.startsWith("0") && "0".equals(integer)) {
            // 去掉分前面的[零]
            result += fractionToRmb(fraction).substring(1);
        } else {
            // 小数部分
            result += fractionToRmb(fraction);
        }

        return result;
    }

    /**
     * 将金额小数部分转换为中文大写
     *
     * @param fraction fraction
     * @return String
     */
    private static String fractionToRmb(String fraction) {
        // 角
        char jiao = fraction.charAt(0);
        // 分
        char fen = fraction.charAt(1);
        return (RMB_NUMS[jiao - '0'] + (jiao > '0' ? UNITS1[1] : ""))
                       + (fen > '0' ? RMB_NUMS[fen - '0'] + UNITS1[2] : "");
    }

    /**
     * 将金额整数部分转换为中文大写
     *
     * @param integer integer
     * @return String
     */
    private static String integerToRmb(String integer) {
        StringBuilder buffer = new StringBuilder();
        // 从个位数开始转换
        int i, j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 当 n 是 0 且 n 的右边一位不是 0 时，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(RMB_NUMS[0]);
                }
                // 插入[万]或者[亿]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0'
                                || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(UNITS3[j / 4]);
                    }
                }
            } else {
                if (j % 4 == 0) {
                    // 插入[万]或者[亿]
                    buffer.append(UNITS3[j / 4]);
                }
                // 插入[拾]、[佰]或[仟]
                buffer.append(UNITS2[j % 4]);
                // 插入数字
                buffer.append(RMB_NUMS[n - '0']);
            }
        }
        return buffer.reverse().toString();
    }

    /**
     * 金额千分位
     * @param text 文本
     * @return String
     */
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.00");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }
}
