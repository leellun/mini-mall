package com.newland.mall.utils;


import com.newland.mall.exception.BusinessException;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * assert工具类
 * Author: leell
 * Date: 2022/12/6 17:44:59
 */
public class AssertUtil {
    /**
     * 断言 expression 是否为false
     * 如果true,  则抛出BusinessException异常
     *
     * @param expression
     * @param message
     */
    public static void isFalse(boolean expression, String message) {
        cn.hutool.core.lang.Assert.isFalse(expression, () -> new BusinessException(message));
    }

    /**
     * 断言 expression 是否为: true
     * 如果为false, 则抛出BusinessException异常
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        cn.hutool.core.lang.Assert.isTrue(expression, () -> new BusinessException(message));
    }

    public static void isNotTrue(boolean expression, String message) {
        cn.hutool.core.lang.Assert.isTrue(!expression, () -> new BusinessException(message));
    }


    /**
     * 断言对象必须为 null {@code null} ，如果不为{@code null} 抛出指定类型异常
     *
     * @param object
     * @param message
     * @return
     */
    public static <T> void isNull(T object, String message) {
        cn.hutool.core.lang.Assert.isNull(object, () -> new BusinessException(message));
    }


    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出指定类型异常
     *
     * @param object
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T notNull(T object, String message) {
        return cn.hutool.core.lang.Assert.notNull(object, () -> new BusinessException(message));
    }

    /**
     * 断言字符串不为空字符串 ，如果空字符串抛出指定类型异常
     *
     * @param str
     * @param message
     * @return
     */
    public static String notBlank(String str, String message) {
        return cn.hutool.core.lang.Assert.notBlank(str, () -> new BusinessException(message));
    }

    /**
     * params是否包含t，如果不包含抛出异常
     *
     * @param t
     * @param message
     * @param params
     * @param <T>
     */
    public static <T> void includeItem(T t, T[] params, String message) {
        isTrue(Arrays.stream(params).collect(Collectors.toList()).contains(t), message);
    }

    /**
     * 正则检查
     *
     * @param str       源字符串
     * @param regx      正则表达式
     * @param message
     * @param <T>
     */
    public static <T> void checkRegx(String str, String regx, String message) {
        Pattern pattern = Pattern.compile(regx);
        isTrue(pattern.matcher(str).find(), message);
    }

    /**
     * 断言value是否在min与max之间，如果不在则抛出异常
     *
     * @param value
     * @param min
     * @param max
     * @param message
     * @return
     */
    public static int checkBetween(int value, int min, int max, String message) {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw new BusinessException(message);
        }
    }
}
