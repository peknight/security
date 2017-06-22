/**
 * MIT License
 *
 * Copyright (c) 2017-2027 PeKnight(JKpeknight@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
