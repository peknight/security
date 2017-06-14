package com.peknight.security.crypto.charshift;

import java.util.Arrays;

/**
 * 字符移位操作类: 封装了对字符移位操作的方法
 *
 * @author PeKnight
 *
 * Created by PeKnight on 2017/6/14.
 */
public class CharShift {
    /**
     * <p>根据字符<code>charShift</code>对字符<code>charArr</code>移位。</p>
     *
     * <p>当传入的取值字符集并非为完整的字符集时，移位会造成部分失真</p>
     *
     * <p>此方法与antiCharShift()方法互为反函数。</p>
     *
     * <p>操作步骤：<br>
     * 		1）计算<code>charArr</code>和<code>charShift</code>在<code>Characters.CHARACTER</code>中对应的下标；<br>
     * 		2）上面两个下标之和对取值字符集长度取余作为新下标，找到该下标在取值字符集中对应的字符返回。<br>
     * </p>
     * @param charArr
     * 				被移位的字符
     * @param charShift
     * 				计算移位长度的字符
     * @param characters
     * 				移位后取值的字符集
     * @return
     * 				返回移位后的字符
     */
    public static char charShift(char charArr, char charShift, char[] characters) {
        //1）计算charArr和charShift在Characters.CHARACTER中对应的下标
        int arrIndex = Characters.CHARACTER.indexOf(charArr);
        int shiftIndex = Characters.CHARACTER.indexOf(charShift);

        //2）上面两个下标之和对取值字符集长度取余作为新下标，找到该下标在取值字符集中对应的字符返回
        int newIndex = (arrIndex+shiftIndex)%characters.length;
        char newCharArr = characters[newIndex];
        return newCharArr;
    }

    /**
     * <p>根据字符串<code>charShift[]</code>对字符串<code>charArr[]</code>移位。</p>
     *
     * <p>当传入的取值字符集并非为完整的字符集时，移位会造成部分失真</p>
     *
     * <p>此方法与antiCharArrShift()方法互为反函数</p>
     *
     * <p>操作步骤：<br>
     * 		1）声明<code>newCharArr</code>用于存储移位后的数组（避免对传入参数造成影响）；<br>
     * 		2）判断两字符串长度是否匹配，如不匹配，调整<code>charShift</code>长度并输出警告信息；<br>
     * 		3）每个字符依次移位。<br>
     * </p>
     *
     * @param charArr
     * 				被移位的字符串
     * @param charShift
     * 				计算移位长度的字符串
     * @param characters
     * 				移位后取值的字符集
     * @return
     * 				返回移位后的字符串
     */
    public static char[] charArrShift(char[] charArr, char[] charShift, char[] characters) {
        //1）声明newCharArr用于存储移位后的数组（避免对传入参数造成影响）
        char[] newCharArr = new char[charArr.length];

		/*
		 * 2）判断两字符串长度是否匹配，如不匹配，调整charShift长度并输出警告信息
		 * 		本步骤仅用于测试时长度不匹配的情况，正常应用中应当主动避免该情况
		 */
        if (charShift.length != charArr.length) {
            charShift = SetLength.fillLength(charShift, charArr.length, Characters.SYMBOL, false);
            System.out.println("WARNING! 移位时字符串长度不匹配! 程序已自动匹配!");
        }

        //3）每个字符依次移位
        for (int i = 0; i < charArr.length; i++) {
            newCharArr[i] = charShift(charArr[i], charShift[i], characters);
        }
        return newCharArr;
    }

    /**
     * <p>根据字符<code>charShift</code>对移位后的字符<code>newCharArr</code>进行还原</p>
     *
     * <p>当传入的取值字符集并非为完整的字符集时，由于存在部分失真，还原时会出现多种情况，因此返回一个数组用于存储所有可能的情况</p>
     *
     * <p>此方法与charShift()方法互为反函数。</p>
     *
     * <p>操作步骤：<br>
     * 		1）计算<code>newCharArr</code>在取值字符集中对应的下标；<br>
     * 		2）计算<code>charShift</code>在<code>Characters.CHARACTER</code>中对应的下标；<br>
     * 		3）计算还原字符最多的情况数<code>n</code>；<br>
     * 		4）声明长度为<code>n</code>的字符型数组<code>charArr</code>用于存储所有可能的情况，整型变量<code>length</code>用于存储有效情况的数量；<br>
     * 		5）遍历所有情况，计算对应的下标，并判断是否合理，合理则存入数组中；<br>
     * 		6）将<code>charArr</code>的长度调整为合理长度返回。<br>
     *
     * @param newCharArr
     * 				移位后的字符
     * @param charShift
     * 				计算移位长度的字符
     * @param characters
     * 				移位后取值的字符集
     * @return
     * 				返回移位前所有可能的字符
     */
    public static char[] antiCharShift(char newCharArr, char charShift, char[] characters) {
        //1）计算newCharArr在取值字符集中对应的下标
        String strCharacters = new String(characters);
        int newArrIndex = strCharacters.indexOf(newCharArr);

        //2）计算charShift在Characters.CHARACTER中对应的下标
        int shiftIndex = Characters.CHARACTER.indexOf(charShift);

        //3）计算还原字符最多的情况数n
        int n = Characters.CHARACTER.length()*2/strCharacters.length();

        //如果n*strCharacters.length()加上newArrIndex比2倍的Characters.CHARACTER.length()小，则n加1
        if (n*strCharacters.length()+newArrIndex < Characters.CHARACTER.length()*2) {
            n++;
        }

        //4）声明长度为n的字符型数组charArr用于存储所有可能的情况，整型变量length用于存储有效情况的数量
        char[] charArr = new char[n];
        int length = 0;

        //5）遍历所有情况，计算对应的下标，并判断是否合理，合理则存入数组中
        for (int i = 0; i < n; i++) {
            int recArrIndex = strCharacters.length()*i+newArrIndex-shiftIndex;
            if (recArrIndex >= 0 && recArrIndex < Characters.CHARACTER.length()) {
                charArr[length++] =  Characters.CHARACTER.charAt(recArrIndex);
            }
        }

        //如果计算的合理长度为0，可能是测试数据不合里造成的，正常运行如出现此报错应重新考虑次算法是否合理
        if (length == 0) {
            System.out.println("ERROR! 还原移位时发生未知错误! 请检查代码antiCharShift()代码的合理性!");
        }

        //6）将charArr的长度调整为合理长度返回
        charArr = Arrays.copyOf(charArr, length);
        return charArr;
    }

    /**
     * <p>根据字符串<code>charShift</code>对移位后的字符串<code>newCharArr</code>进行还原</p>
     *
     * <p>当传入的取值字符集并非为完整的字符集时，由于存在部分失真，还原时会出现多种情况，因此返回一个数组的数组用于存储所有可能的情况</p>
     *
     * <p>此方法与charArrShift()方法互为反函数</p>
     *
     * <p>操作步骤：<br>
     * 		1）声明<code>charArr</code>用于存储移位后的数组（避免对传入参数造成影响）；<br>
     * 		2）判断两字符串长度是否匹配，如不匹配，调整<code>charShift</code>长度并输出警告信息；<br>
     * 		3）每个字符依次还原。<br>
     * </p>
     *
     * @param newCharArr
     * 				移位后的字符串
     * @param charShift
     * 				计算移位长度的字符串
     * @param characters
     * 				移位后取值的字符集
     * @return
     * 				返回移位前每个字符所有可能的情况
     */
    public static char[][] antiCharArrShift(char[] newCharArr, char[] charShift, char[] characters) {
        //1）声明charArr用于存储移位后的数组（避免对传入参数造成影响）
        char[][] charArr = new char[newCharArr.length][];

        //2）判断两字符串长度是否匹配，如不匹配，调整charShift长度并输出警告信息
        if (charShift.length != newCharArr.length) {
            charShift = SetLength.fillLength(charShift, newCharArr.length, Characters.SYMBOL, false);
            System.out.println("WARNING! 还原时字符串长度不匹配! 程序已自动匹配!");
        }
        //3）每个字符依次还原
        for (int i = 0; i < newCharArr.length; i++) {
            charArr[i] = antiCharShift(newCharArr[i], charShift[i], characters);
        }
        return charArr;
    }

    /**
     * <p>遍历<code>antiCharArrShift</code>方法还原出来的字符串数组组合的所有情况</p>
     *
     * <p>此方法暂时想不到反函数的意义，故暂不提供反函数</p>
     *
     * <p>此方法在失真严重且数组长度较长的时候会非常的...你懂的...慎用。</p>
     *
     * <p>操作步骤：<br>
     * 		1）计算有多少种组合方式，存为<code>n</code>；<br>
     * 		2）声明<code>ergCharArr</code>用于存储所有的组合方式；<br>
     * 		3）遍历每一种组合方式存入<code>ergCharArr</code>中。<br>
     * </p>
     *
     * @param charArr
     * 			每个字符所有可能的情况的数组
     * @return
     * 			所有的组合方式的数组
     */
    public static char[][] ergodicCharArr(char[][] charArr) {
        //1）计算有多少种组合方式，存为n
        int n = 1;
        for (int i = 0; i < charArr.length; i++) {
            n *= charArr[i].length;
        }

        //2）声明ergCharArr用于存储所有的组合方式
        char[][] ergCharArr = new char[n][charArr.length];

        //3）遍历每一种组合方式存入ergCharArr中
        //定义一个变量level表示每层循环拆分赋值的长度
        int level = n;

        //外循环：遍历charArr的每个字符数组
        for (int i = 0; i < charArr.length; i++) {
			/*
			 * 内循环：为ergCharArr[j][i]的依次赋值（i固定）
			 *
			 * 第一个字符赋值将所有n个ergCharArr数组拆分为charArr[0].length组，每组长度为level
			 * 每组分别由charArr[0]中的数据赋值
			 *
			 * 以后每个字符的赋值都将上一个字符所分的组每组再分为charArr[i].length组，每组的长度level=level/charArr[i].length
			 *
			 * level*charArr[i].length为上一个字符赋值操作进行拆分的每组长度（记为a）
			 *
			 * j%a为ergCharArr[j][i]在上一个字符赋值操作拆分的某一组中的位置（类似于下标）（记为b）
			 *
			 * b/level为ergCharArr[j][i]在当前字符赋值操作拆分的小组位置（类似于下标）（记为c）
			 *
			 * 将charArr[i][c]赋值给ergCharArr[j][i]即可
			 *
			 * 如看不懂，可以通过测试查看运行效果帮助理解
			 *
			 */
            level /= charArr[i].length;
            for (int j = 0; j < n; j++) {
				/*
				 * 尼玛嗨这行代码我改了一万遍运行才不报错
				 * 这一行大概是这个程序最难想的一行代码了
				 */
                ergCharArr[j][i] = charArr[i][j%(level*charArr[i].length)/level];
            }

        }
        return ergCharArr;
    }
}
