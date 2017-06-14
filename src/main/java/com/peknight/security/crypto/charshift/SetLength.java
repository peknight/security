package com.peknight.security.crypto.charshift;

import java.util.Arrays;
import java.util.Random;

/**
 * 调整数组长度类：提供填充、缩短数组长度的方法，会造成数据不同程度的失真
 * 
 * @author Peknight
 *
 */
public class SetLength {

	/**
	 * <p>使用空格" "或随机字符填充数组到指定长度，如传入数组长度大于指定长度则进行截取。</p>
	 * 
	 * <p>此方法的反函数为antiFillLength()，此方法亦可作为自身反函数，不过不推荐。</p>
	 * 
	 * <p>操作方法简单无脑，不想解释。</p>
	 * 
	 * @param charArr
	 * 				待填充的数组
	 * @param length
	 * 				指定长度
	 * @param SYMBOL
	 * 				填充用的字符集
	 * @param random
	 * 				为true随机填充，为false使用空格填充
	 * @return
	 * 				填充后的数组
	 */
	public static char[] fillLength(char[] charArr, int length, String SYMBOL, boolean random) {
		//声明新数组newCharArr长度为length
		char[] newCharArr = Arrays.copyOf(charArr, length);
		
		//要填充的长度n
		int n = newCharArr.length-charArr.length;
		
		//如果n小于0，输出警告信息，如果用此方法加密信息应避免这种情况
		if (n < 0) {
			System.out.println("WARNING! 所需填充数组长度溢出，程序截取了指定长度!");
		}
		
		//声明变量index用于控制填充字符的下标
		int index = 0;
		
		//为待填充的位置赋值
		for (int i = 0; i < n; i++) {
			//如果传入的random为true则填充随机字符
			if (random) {
				Random rand = new Random();
				index = rand.nextInt(SYMBOL.length());
			}
			newCharArr[charArr.length+i] = SYMBOL.charAt(index);
		}
		return newCharArr;
	}
	
	/**
	 * <p>将被填充的数组截取为原数组。</p>
	 * 
	 * <p>此方法是fillLength()与addLength()的反函数。</p>
	 * 
	 * <p>操作方法简单无脑，不想解释。</p>
	 * 
	 * @param newCharArr
	 * 				填充后的数组长度
	 * @param length
	 * 				填充前的长度
	 * @return
	 * 				还原为填充前的数组
	 */
	public static char[] antiFillLength(char[] newCharArr, int length) {
		if (newCharArr.length < length) {
			System.out.println("WARNING！ 还原长度时出错, 数据失真无法恢复");
			return fillLength(newCharArr, length, Characters.SYMBOL, false);
		}
		char[] charArr = Arrays.copyOf(newCharArr, length);
		return charArr;
	}
	
	/**
	 * <p>在<code>Characters.CHARACTER</code>的范围内取数组内某段字符的平均值。</p>
	 * 
	 * <p>此方法不提供反函数。</p>
	 * 
	 * <p>方法：对要取平均值的字符在<code>Characters.CHARACTER</code>中对应的下标取平均值，返回下标平均值对应的字符。</p>
	 * 
	 * @param charArr
	 * 				取值的源数组
	 * @param fromIndex
	 * 				取值位置的起始下标
	 * @param step
	 * 				要取平均值的长度
	 * @return
	 * 				返回字符平均值
	 */
	public static char setAverChar(char[] charArr, int fromIndex, int step) {
		//防止错误调用，错误调用返回空格' '
		if (step <= 0) {
			System.out.println("WARNING! 取字符平均值时出错! 请检查调用代码的合理性");
			return ' ';
		}
		
		//对要取平均值的字符在Characters.CHARACTER中对应的下标取平均值，返回下标平均值对应的字符
		int sum = 0;
		for (int i = fromIndex; i < fromIndex+step; i++) {
			sum += Characters.CHARACTER.indexOf(charArr[i]);
		}
		char averChar = Characters.CHARACTER.charAt(sum/step);
		return averChar;
	}
	
	/**
	 * <p>缩短数组的长度。</p>
	 * 
	 * <p>此方法失真严重，不提供反函数</p>
	 * 
	 * <p>操作步骤：<br>
	 * 		1）声明<code>subCharArr</code>数组存储缩短之后的数组；<br>
	 * 		2）将<code>charArr</code>分为<code>length</code>份，前mod份长为<code>step+1</code>，其余长为<code>step</code>份，对每份取平均值。<br>
	 * </p>
	 * 
	 * @param charArr
	 * 				待缩短的数组
	 * @param length
	 * 				缩短后数组长度
	 * @return
	 * 				缩短后的数组
	 */
	public static char[] subLength(char[] charArr, int length) {
		//防止错误调用，如错误调用，自动跳转到调整char型数组长度方法
		if (charArr.length < length) {
			System.out.println("WARNING! 错误调用缩短数组方法，请检查代码合理性!");
			return setLength(charArr, length);
		}
		
		//1）声明subCharArr数组存储缩短之后的数组
		char[] subCharArr = new char[length];
		
		//计算数组长度对缩短后长度的商与模
		int step = charArr.length/length;	
		int mod = charArr.length%length;
		
		//2）将charArr分为length份，前mod份长为step+1，其余长为step份，对每份取平均值
		for (int i = 0; i < mod; i++) {
			subCharArr[i] = setAverChar(charArr, step*i+i, step+1);
		}
		for (int i = mod; i < length; i++) {
			subCharArr[i] = setAverChar(charArr, step*i+mod, step);
		}
		return subCharArr;
	}
	
	/**
	 * <p>通过平铺的方式补充数组的长度，不足的地方用subLength()方法填充。</p>
	 * 
	 * <p>此方法在正常调用时不存在失真，此时与fillLength()共用一个反函数antiFillLength()。</p>
	 * 
	 * <p>操作步骤：<br>
	 * 		1）声明<code>addCharArr</code>存储新数组；<br>
	 * 		2）计算增加之后的长度对数组长度的商与模；<br>
	 * 		3）以平铺的方式填充新数组，如果模不为0，则将为填充的位置填充一个缩短的原数组。<br>
	 * </p>
	 * 
	 * <p>备用方法：不推荐使用。</p>
	 * 
	 * @deprecated
	 * 
	 * @param charArr
	 * 				待补充的数组
	 * @param length
	 * 				补充后的数组长度
	 * @return
	 * 				补充后的数组
	 */
	public static char[] addLength(char[] charArr, int length) {
		//1）声明addCharArr存储新数组
		char[] addCharArr = new char[length];
		
		//2）计算增加之后的长度对数组长度的商与模
		int step = length/charArr.length;
		int mod = length%charArr.length;
		
		//3）以平铺的方式补充新数组，如果模不为0，则将未补充的位置补充一个缩短的原数组
		for (int i = 0; i < step; i++) {
			System.arraycopy(charArr, 0, addCharArr, i*charArr.length, charArr.length);
		}
		if (mod != 0 ) {
			char[] subCharArr = subLength(charArr, mod);
			System.arraycopy(subCharArr, 0, addCharArr, length-mod, subCharArr.length);
		}
		return addCharArr;
	}
	
	/**
	 * <p>调整数组的长度。</p>
	 * 
	 * <p>此方法失真严重，不提供反函数。</p>
	 * 
	 * <p>操作步骤：<br>
	 * 		1）计算增加之后的长度对数组长度的商与模；<br>
	 * 		2）如果模不为零，<code>step</code>加1；<br>
	 * 		3）声明<code>setCharArr</code>长度为原长度的<code>step</code>倍，平铺填充原数组；<br>
	 * 		4）将<code>setCharArr</code>缩短到指定长度。<br>
	 * </p>
	 * 
	 * @param charArr
	 * 				待调整的数组
	 * @param length
	 * 				调整后的数组长度
	 * @return
	 * 				调整后的数组
	 */
	public static char[] setLength(char[] charArr, int length) {
		//1）计算增加之后的长度对数组长度的商与模
		int step = length/charArr.length;
		int mod = length%charArr.length;
		
		//2）如果模不为零，step加1
		if (mod != 0) {
			step++;
		}
		
		//3）声明setCharArr长度为原长度的step倍，平铺填充原数组
		char[] setCharArr = new char[charArr.length*(step)];
		for (int i = 0; i < step; i++) {
			System.arraycopy(charArr, 0, setCharArr, i*charArr.length, charArr.length);
		}
		
		//4）将setCharArr缩短到指定长度
		if (setCharArr.length != length)
			setCharArr = subLength(setCharArr, length);
		return setCharArr;
	}
}
