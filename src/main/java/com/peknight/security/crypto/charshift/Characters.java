package com.peknight.security.crypto.charshift;

import java.util.Arrays;

/**
 * 字符集类：存储字符集，并提供一个简单的字符集操作方法
 *
 * @author PeKnight
 *
 * Created by PeKnight on 2017/6/14.
 */
public class Characters {
    public static final String NUM = "0123456789";
    public static final String LOWER_LETTER = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String SYMBOL = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String CHARACTER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String CUSTOM = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%&*+-<=>?@^_~";
    public static final int[] COMPLEXITY = {10, 36, 62, 95};

    /** 根据需求的密码复杂程度截取字符集合 */
    public static String getCharacters(int complexity) {
        if (complexity == 4)
            return CUSTOM;
        char[] newCharArr = Arrays.copyOf(CHARACTER.toCharArray(), COMPLEXITY[complexity]);
        String characters = new String(newCharArr);
        return characters;
    }
}
