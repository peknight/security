package com.peknight.security.crypto.charshift;

/**
 * 拆分重组类：封装了对字符型数组拆分及重组的方法，并提供了反函数。
 * 
 * @author Peknight
 *
 */
public class Recombine {
	/**
	 * <p>将<code>charArr</code>数组拆分为<code>groupNum</code>个子数组。</p>
	 * 
	 * <p>此方法与antiSplit()方法互为反函数</p>
	 * 
	 * <p>操作步骤如下：<br>
	 * 		1）如果数组长度不超过拆分组数，更改拆分组数为数组长度，并输出警告信息；<br>
	 * 		2）声明数组型数组<code>subCharArr</code>用于存储拆分后的数组；<br>
	 * 		3）计算原数组长度对分组数的商与模；<br>
	 * 		4）前<code>mod</code>（模值）组的子数组长度设为<code>step+1</code>（商值+1），剩余的子数组长度设为<code>step</code>（商值），依次将<code>charArr</code>中的元素赋给子数组的每一个元素；<br>
	 * </p>
	 * 
	 * @param charArr
	 * 				待拆分的数组
	 * @param groupNum
	 * 				拆分的子数组数
	 * @return
	 * 				返回拆分后的数组
	 */
	public static char[][] split(char[] charArr, int groupNum) {
		//1）如果数组长度不超过拆分组数，更改拆分组数为数组长度，并输出警告信息
		if (charArr.length <= groupNum) {
			groupNum = charArr.length;
			System.out.println("WARNING! 拆分时分组过细，可能导致重组失去意义");
		}
		
		//2）声明数组型数组subCharArr用于存储拆分后的数组
		char[][] subCharArr = new  char[groupNum][];
		
		//3）计算原数组长度对分组数的商与模
		int step = charArr.length/groupNum;
		int mod = charArr.length%groupNum;
		
		//4）前mod（模值）组的子数组长度设为step+1（商值+1），剩余的子数组长度设为step（商值），依次将charArr中的元素赋给子数组的每一个元素
		for (int i = 0; i < mod; i++) {
			subCharArr[i] = new char[step+1];
			System.arraycopy(charArr, step*i+i, subCharArr[i], 0, step+1);
		}
		for (int i = mod; i < subCharArr.length; i++) {
			subCharArr[i] = new char[step];
			System.arraycopy(charArr, step*i+mod, subCharArr[i], 0, step);
		}
		return subCharArr;
	}
	
	/**
	 * <p>将被拆分的数组合并。</p>
	 * 
	 * <p>此方法与split()方法互为反函数</p>
	 * 
	 * <p>操作步骤如下：<br>
	 * 		1）计算所有子数组的长度和<code>length</code>；<br>
	 * 		2）声明<code>charArr</code>用于存储合并后的数组；<br>
	 * 		3）将每个子数组中的元素依次存入<code>charArr</code>中；<br>
	 * </p>
	 * 
	 * @param subCharArr
	 * 				被拆分的数组
	 * @return
	 * 				合并后的数组
	 */
	public static char[] antiSplit(char[][] subCharArr) {
		//1）计算所有子数组的长度和length
		int length = 0;
		for (int i = 0; i < subCharArr.length; i++) {
			length += subCharArr[i].length;
		}
		
		//2）声明charArr用于存储合并后的数组
		char[] charArr = new char[length];
		
		//3）将每个子数组中的元素依次存入charArr中
		int index = 0;
		for (int i = 0; i < subCharArr.length; i++) {
			System.arraycopy(subCharArr[i], 0, charArr, index, subCharArr[i].length);
			index += subCharArr[i].length;
		}
		return charArr;
	}
		
	/**
	 * <p>将被拆分的数组重新组合。</p>
	 * 
	 * <p>此方法与antiRecombine()方法互为反函数</p>
	 * 
	 * <p>操作步骤如下：<br>
	 * 		1）计算所有子数组的长度和<code>length</code>；<br>
	 * 		2）声明<code>charArr</code>用于存储重组后的数组；<br>
	 * 		3）抽出每个子数组的第一个数依次传入<code>newCharArr</code>，再抽出第二个、第三个依次类推；<br>
	 * </p>
	 * 
	 * @param subCharArr
	 * 				被拆分的数组
	 * @return
	 * 				重组后的数组
	 */
	public static char[] recombine(char[][] subCharArr) {
		//1）计算所有子数组的长度和length
		int length = 0;
		for (int i = 0; i < subCharArr.length; i++) {
			length += subCharArr[i].length;
		}
		
		//2）声明charArr用于存储重组后的数组
		char[] newCharArr = new char[length];
		
		//计算重组后数组长度对子数组数的商与模
		int step = newCharArr.length/subCharArr.length;
		int mod = newCharArr.length%subCharArr.length;
		
		//3）抽出每个子数组的第一个数依次传入newCharArr，再抽出第二个、第三个依次类推
		int index = 0;
		for (int i = 0; i < step; i++) {
			for (int j = 0; j < subCharArr.length; j++) {
				newCharArr[index++] = subCharArr[j][i];
			}
		}
		for (int i = 0; i < mod; i++) {
			newCharArr[index++] = subCharArr[i][step]; 
		}
		return newCharArr;
	}
	
	/**
	 * <p>还原重新组合的数组。</p>
	 * 
	 * <p>此方法与recombine()方法互为反函数</p>
	 * 
	 * <p>操作步骤如下：<br>
	 * 		1）如果数组长度不超过拆分组数，更改拆分组数为数组长度；<br>
	 * 		2）声明<code>subCharArr</code>用于存储还原后的子数组；<br>
	 * 		3）以<code>groupNum</code>为间隔为每个子数组赋值。<br>
	 * </p>
	 * 
	 * @param charArr
	 * 				重新组合的数组
	 * @param groupNum
	 * 				被拆分前的组数
	 * @return
	 * 				还原后的子数组
	 */
	public static char[][] antiRecombine(char[] charArr, int groupNum) {
		//1）如果数组长度不超过拆分组数，更改拆分组数为数组长度
		if (charArr.length <= groupNum) {
			groupNum = charArr.length;
		}
		
		//2）声明subCharArr用于存储还原后的子数组
		char[][] subCharArr = new char[groupNum][];
		
		//计算重组后数组长度对子数组数的商与模
		int step = charArr.length/groupNum;
		int mod = charArr.length%groupNum;
		
		//为每个子数组初始化
		for (int i = 0; i < mod; i++) {
			subCharArr[i] = new char[step+1];
		}
		for (int i = mod; i < subCharArr.length; i++) {
			subCharArr[i] = new char[step];
		}
		
		//3）以groupNum为间隔为每个子数组赋值
		for (int i = 0; i < subCharArr.length; i++) {
			for (int j = 0; j < subCharArr[i].length; j++) {
				subCharArr[i][j] = charArr[i+j*groupNum];
			}
		}
		return subCharArr;
	}
	
	/**
	 * <p>将数组倒序排列</p>
	 * 
	 * <p>此方法本身即是自己的反函数。</p>
	 * 
	 * <p>操作方法简单无脑，不提供注释</p>
	 * 
	 * @param charArr
	 * 				传入的数组
	 * @return
	 * 				倒序的数组
	 */
	public static char[] reverse(char[] charArr) {
		char[] charRev = new char[charArr.length];
		for (int i = 0; i < charArr.length; i++) {
			charRev[i] = charArr[charArr.length-1-i];
		}
		return charRev;
	}
}