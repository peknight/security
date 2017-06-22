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
 * 算法类：封装了Generator类中所需的常用运算方法及部分方法的反函数
 * 
 * @author Peknight
 *
 */
public class Algorithms {
	
	/**
	 * <p>此方法将数组中的非常规字符转换为常规字符。</p>
	 * 
	 * <p>此方法不提供反函数。</p>
	 * 
	 * <p>操作步骤：<br>
	 * 		1）声明一个新数组<code>newCharArr</code>用于存储转换完成的数组，避免影响传入的参数；<br>
	 * 		2）依次判断每个字符，如果字符不在字符集<code>Characters.CHARACTER</code>中，将该字符对应的int值对字符集长度取模，以模为下标在字符集中找到对应字符。<br>
	 * </p>
	 * 
	 * @param charArr
	 * 			待转换的数组
	 * @return
	 * 			转换完的数组
	 */
	public static char[] toChar(char[] charArr) {
		//1）声明一个新数组newCharArr用于存储转换完成的数组，避免影响传入的参数
		char[] newCharArr = Arrays.copyOf(charArr, charArr.length);
		
		//2）依次判断每个字符，如果字符不在字符集Characters.CHARACTER中，将该字符对应的int值对字符集长度取模，以模为下标在字符集中找到对应字符
		for (int i = 0; i < newCharArr.length; i++) {
			int index = Characters.CHARACTER.indexOf(charArr[i]);
			if (index == -1) {
				newCharArr[i] = Characters.CHARACTER.charAt((int) charArr[i]%Characters.CHARACTER.length());
			}
		}
		return newCharArr;
	}
	
	/**
	 * <p>此方法根据传入的备注信息计算出一系列整型数用于操作待加密/解密的数组进行拆分和重组。</p>
	 * 
	 * <p>此方法对数据操作造成的失真很严重，反函数没有任何意义，所以本类中没有提供该方法的反函数。</p>
	 * 
	 * <p>计算步骤如下：<br>
	 * 		1）调整传入的备注信息<code>charRemark</code>的长度为<code>n*2</code>，存入<code>newCharRemark</code>；<br>
	 * 		2）声明一个整型数组<code>intRemark</code>存储运算所得数据；<br>
	 * 			每个子数组的第一个数据用于计算待操作的数组所需拆分的组数，第二个数据用于计算将哪些子数组进行倒序操作；<br>
	 * 			计算<code>newCharRemark</code>每个字符按顺序对应<code>intRemark</code>数组中的每个数据；<br>
	 * 			每个子数组的第一个数据直接存储上述运算结果；<br>
	 * 			每个子数组的第二个数据使用上述运算结果对第一个数据加一再取模（为0则不进行倒序操作）；<br>
	 * 			这样运算的目的是使数组的重组有意义。<br>
	 * 		3）返回计算所得的<code>intRemark</code>数组。<br>
	 * </p>
	 * 
	 * @param charRemark
	 * 				传入的备注信息
	 * @param pwdLength
	 * 				所需的密码长度
	 * @param n
	 * 				重组的总次数
	 * @return
	 * 				返回计算所得的int[][]型数组
	 */
	public static int[][] setRemark(char[] charRemark, int pwdLength, int n) {
		//如果传入的密码长度小于2，暂时将其设置为2，防止后面的运算中被除数为0
		if (pwdLength < 2) {
			pwdLength = 2;
		}
		
		//1）调整传入的备注信息charRemark的长度为n*2，存入newCharRemark（避免对传入参数造成影响）
		char[] newCharRemark = SetLength.setLength(charRemark, n*2);
				
		//2）声明一个整型数组intRemark存储运算所得数据
		int[][] intRemark = new int[n][2];	
		
		/*
		 * 每个子数组的第一个数据用于计算待操作的数组所需拆分的组数，第二个数据用于计算将哪些子数组进行倒序操作
		 * 
		 * 计算方式：
		 * 		计算newCharRemark每个字符按顺序对应intRemark数组中的每个数据
		 * 		计算每个字符在Characters.CHARACTER对应的下标，用下标值对所需的密码长度的一半取模再加一
		 * 			每个子数组的第一个数据直接存储上述运算结果
		 * 			每个子数组的第二个数据使用上述运算结果对第一个数据加一再取模（为0则不进行倒序操作）
		 * 		这样运算的目的是使数组的重组有意义
		 */
		for (int i = 0; i < newCharRemark.length; i++) {
			if (i%2 == 0) {
				intRemark[i/2][0] = Characters.CHARACTER.indexOf(newCharRemark[i])%(pwdLength/2)+1;
			} else {
				intRemark[i/2][1] = (Characters.CHARACTER.indexOf(newCharRemark[i])%(pwdLength/2)+1)%(intRemark[i/2][0]+1);
			}
		}
		
		//3）返回计算所得的intRemark数组
		return intRemark;
	}
	
	/**
	 * <p>根据<code>intRemark</code>值将传入的<code>subCharArr</code>部分子数组倒序。</p>
	 * 
	 * <p>此方法本身即是自己的反函数。</p>
	 * 
	 * <p>操作步骤如下：<br>
	 * 		1)复制原数组<code>subCharArr</code>到新数组<code>newSubCharArr</code>（避免对传入参数造成影响）；<br>
	 * 		2)若传入的<code>intRemark</code>值为0，则不进行倒序操作,直接返回数组<code>newSubCharArr</code>；<br>
	 * 		3)遍历所有子数组，如果子数组的下标加一的值能被传入的<code>intRemark</code>值整除，则将该子数组倒序；<br>
	 * 		4)返回新数组<code>newSubCharArr</code>。<br>
	 * 
	 * @param subCharArr
	 * 				待操作的数组
	 * @param intRemark
	 * 				备注信息值
	 * @return
	 * 				返回操作完成的数组
	 */
	public static char[][] setReverse(char[][] subCharArr, int intRemark) {
		//1）复制原数组subCharArr到新数组newSubCharArr（避免对传入参数造成影响）
		char[][] newSubCharArr = Arrays.copyOf(subCharArr, subCharArr.length);
		
		//2）若传入的intRemark值为0，则不进行倒序操作，直接返回数组newSubCharArr
		if (intRemark == 0) {
			return newSubCharArr;
		}
		
		//3）遍历所有子数组，如果子数组的下标加一的值能被传入的intRemark值整除，则将该子数组倒序
		for (int i = 0; i < newSubCharArr.length; i++) {
			if ((i+1)%intRemark == 0) {
				newSubCharArr[i] = Recombine.reverse(newSubCharArr[i]);
			}
		}
		
		//4）返回新数组newSubCharArr
		return newSubCharArr;
	}
	
	/**
	 * <p>根据<code>intRemark</code>将传入的数组<code>charArr</code>拆分、部分子数组倒序、重新组合。</p>
	 * 
	 * <p>此方法简单的调用三个函数，具体操作方式请参见被调用各方法的注释文档。</p>
	 * 
	 * <p>此方法与antiSetCombine()方法互为反函数。</p>
	 * 
	 * @param charArr
	 * 				待操作的数组
	 * @param intRemark
	 * 				拆分倒序参数
	 * @param index
	 * 				所需拆分倒序参数的下标
	 * @return
	 * 				返回操作完成的新数组
	 */
	public static char[] setRecombine(char[] charArr, int[][] intRemark, int index) {
		char[][] subCharArr = Recombine.split(charArr, intRemark[index][0]);	//拆分
		subCharArr = Algorithms.setReverse(subCharArr, intRemark[index][1]);	//倒序部分子数组
		char[] newCharArr = Recombine.recombine(subCharArr);					//重组
		return newCharArr;
	}
	
	/**
	 * <p>根据<code>intRemark</code>将待恢复的新数组<code>newCharArr</code>反重组、部分子数组倒序、反拆分。</p>
	 * 
	 * <p>此方法简单的调用三个函数，具体操作方式请参见被调用各方法的注释文档。</p>
	 * 
	 * <p>此方法与setCombine()方法互为反函数。</p>
	 * 
	 * @param newCharArr
	 * 				待恢复的新数组
	 * @param intRemark
	 * 				拆分倒序参数
	 * @param index
	 * 				所需拆分倒序参数的下标
	 * @return
	 * 				返回恢复的原数组
	 */
	public static char[] antiSetRecombine(char[] newCharArr, int[][] intRemark, int index) {
		char[][] subCharArr = Recombine.antiRecombine(newCharArr, intRemark[index][0]);	//反重组
		subCharArr = Algorithms.setReverse(subCharArr, intRemark[index][1]);			//倒序部分子数组
		char[] charArr = Recombine.antiSplit(subCharArr);								//反拆分
		return charArr;
	}
}
