package com.peknight.security.crypto.charshift;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 包含主方法、用于生成密码的类
 * 
 * @author Peknight
 *
 */
public class Generator {

	/**
	 * <p>此方法根据传入的各项参数生成密码，不会使用随机字符进行填充。</p>
	 * 
	 * <p>仅在传入的基础密码<code>basicPwd</code>的长度和所需密码长度<code>pwdLength</code>相同且复杂程度<code>complexity</code>为全键盘（3）的情况下，可以根据生成的密码<code>password</code>、应用名称<code>appName</code>和备注信息<code>remark</code>将生成的密码<code>password</code>还原为基础密码<code>basicPwd</code>；<br>
	 * 		还原方法参见antiMethodDefault()。<br>
	 * </p>
	 * 
	 * <p>生成密码的步骤如下：<br>
	 * 		1）将传入的字符串转换为字符型数组以便操作，然后转换特殊字符为常规字符；<br>
	 * 		2）根据密码的复杂程度<code>complexity</code>截取密码字符集<code>characters</code>；<br>
	 * 		3）根据备注信息<code>remark</code>计算出一个长度为<code>n</code>的整型数组<code>intRemark</code>用于操作各个字符型数组的拆分与其部分子数组的倒序；<br>
	 * 		4）根据<code>intRemark</code>的值将<code>characters</code>、<code>chaoracterArr</code>（<code>Characters.CHARACTER</code>转换的数组）重组（拆分、部分子数组倒序、重组）（<code>characterArr</code>重组成两个新数组）；<br>
	 * 		5）调整待操作的数组的长度为所需的密码长度<code>pwdLength</code>；<br>
	 * 		6）根据由<code>Characters.CHARACTER</code>生成的两个新数组对基础密码<code>charBaisPwd</code>和应用名称<code>charAppName</code>移位；<br>
	 * 		7）根据<code>intRemark</code>的值将基础密码<code>charBasicPwd</code>和应用名称<code>charAppName</code>重组（拆分、部分子数组倒序、重组）；<br>
	 * 		8）根据<code>charAppName</code>对<code>charBasicPwd</code>移位；<br>
	 * 		9）将新生成的<code>charBasicPwd</code>重组；<br>
	 * 		10）将生成的<code>charBasicPwd</code>转换为字符串<code>password</code>返回。<br>
	 * </p>
	 * 
	 * @param basicPwd
	 * 			传入的基础密码
	 * @param appName
	 * 			传入的应用名称
	 * @param remark
	 * 			传入的备注信息
	 * @param pwdLength
	 * 			所需的密码长度
	 * @param complexity
	 * 			密码的复杂程度
	 * @return
	 * 			返回生成的密码
	 */
	public static String version_1(String basicPwd, String appName, String remark, int pwdLength, int complexity) {
		//1）将传入的字符串转换为字符型数组以便操作,然后转换特殊字符为常规字符
		char[] charBasicPwd = basicPwd.toCharArray();
		char[] charAppName = appName.toCharArray();
		char[] charRemark = remark.toCharArray();
		
		charBasicPwd = Algorithms.toChar(charBasicPwd);
		charAppName = Algorithms.toChar(charAppName);
		charRemark = Algorithms.toChar(charRemark);
		
		//2）根据密码的复杂程度complexity截取密码字符集characters
		char[] characters = Characters.getCharacters(complexity).toCharArray();
		
		//将Characters.CHARACTER转换为数组类型
		char[] characterArr = Characters.CHARACTER.toCharArray();
		
		//设定重组的总次数
		int n = 6;
		
		//3）根据备注信息remark计算出一个长度为n的整型数组intRemark用于操作各个字符型数组的拆分与其部分子数组的倒序
		int[][] intRemark = Algorithms.setRemark(charRemark, pwdLength, n);
				
		//4）根据intRemark的值将characters、chaoracterArr重组（拆分、部分子数组倒序、重组）（characterArr重组成两个新数组）
		characters = Algorithms.setRecombine(characters, intRemark, 0);
		char[] chsBasicPwd = Algorithms.setRecombine(characterArr, intRemark, 1);
		char[] chsAppName = Algorithms.setRecombine(characterArr, intRemark, 2);
		
		//5）调整待操作的数组的长度为所需的密码长度pwdLength
		charBasicPwd = SetLength.setLength(charBasicPwd, pwdLength);
		charAppName = SetLength.setLength(charAppName, pwdLength);
		chsBasicPwd = SetLength.setLength(chsBasicPwd, pwdLength);
		chsAppName = SetLength.setLength(chsAppName, pwdLength);

		//6）根据由Characters.CHARACTER生成的两个新数组对基础密码charBasicPwd和应用名称charAppName移位
		charBasicPwd = CharShift.charArrShift(charBasicPwd, chsBasicPwd, characters);
		charAppName = CharShift.charArrShift(charAppName, chsAppName, characters);

		//7）根据intRemark的值将待基础密码charBasicPwd和应用名称charAppName重组（拆分、部分子数组倒序、重组）
		charBasicPwd = Algorithms.setRecombine(charBasicPwd, intRemark, 3);
		charAppName = Algorithms.setRecombine(charAppName, intRemark, 4);
		
		//8）根据charAppName对charBasicPwd移位
		charBasicPwd = CharShift.charArrShift(charBasicPwd, charAppName, characters);

		//9）将生成的charBasicPwd重组
		charBasicPwd = Algorithms.setRecombine(charBasicPwd, intRemark, 5);

		//10）将生成的charBasicPwd转换为字符串password返回
		String password = new String(charBasicPwd);
		return password;
	}
	
	/**
	 * <p>此方法为methodDefault()的还原方法，将其生成的密码根据各项参数还原为基础密码。</p>
	 * 
	 * <p>此方法在基础密码与生成密码长度不一致与基础密码中存在特殊字符时会出现严重失真，因此此种情况不建议使用此方法。</p>
	 * 
	 * <p>此方法在密码复杂程度较小时可能会产生失真，这种情况下慎用此方法。</p>
	 * 
	 * <p>还原的操作步骤：<br>
	 * 		1）将传入的字符串转换为字符型数组以便操作，然后转换特殊字符为常规字符；<br>
	 * 		2）根据密码的复杂程度<code>complexity</code>截取密码字符集<code>characters</code>；<br>
	 * 		3）根据备注信息<code>remark</code>计算出一个长度为n的整型数组<code>intRemark</code>用于操作各个字符型数组的拆分与其部分子数组的倒序；<br>
	 * 		4）根据<code>intRemark</code>的值将<code>characters</code>、<code>chaoracterArr</code>重组（拆分、部分子数组倒序、重组）（<code>characterArr</code>重组成两个新数组）；<br>
	 * 		5）调整待操作的数组的长度为所需的密码长度<code>pwdLength</code>；<br>
	 * 		6）根据由<code>Characters.CHARACTER</code>生成的新数组对应用名称<code>charAppName</code>移位；<br>
	 * 		7）根据<code>intRemark</code>的值将<code>charAppName</code>重组（拆分、部分子数组倒序、重组）；<br>
	 * 		8）还原<code>charPwd</code>为最后一次重组之前的状态；<br>
	 * 		9）还原<code>charPwd</code>为最后一次移位之前的状态，如<code>characters</code>并非全键盘字符集合则本步骤有可能会失真，集合越小失真越严重；<br>
	 * 		10）还原<code>charPwd</code>为第一次重组之前的状态<br>
	 * 		11）还原<code>charPwd</code>为第一次移位之前的状态，如<code>characters</code>并非全键盘字符集合则本步骤有可能会失真，集合越小失真越严重；<br>
	 * 		11A）还原<code>charPwd</code>为调整长度之前的状态，如果密码长度与原密码长度不匹配一定会产生失真；<br>
	 * 		12）将还原后的字符转换为字符串<code>basicPwd</code>返回。<br>
	 * </p>
	 * 
	 * @param password
	 * 				生成的密码
	 * @param appName
	 * 				传入的应用名称
	 * @param remark
	 * 				传入的备注信息
	 * @param basicPwdLength
	 * 				基础密码长度
	 * @param complexity
	 * 				密码的复杂程度
	 * @return
	 * 				返回还原的基础密码
	 */
	public static String antiVersion_1(String password, String appName, String remark, int basicPwdLength, int complexity) {
		//1）将传入的字符串转换为字符型数组以便操作，然后转换特殊字符为常规字符
		char[] charPwd = password.toCharArray();
		char[] charAppName = appName.toCharArray();
		char[] charRemark = remark.toCharArray();
		
		charAppName = Algorithms.toChar(charAppName);
		charRemark = Algorithms.toChar(charRemark);
		
		//2）根据密码的复杂程度complexity截取密码字符集characters
		char[] characters = Characters.getCharacters(complexity).toCharArray();
		
		//将Characters.CHARACTER转换为数组类型
		char[] characterArr = Characters.CHARACTER.toCharArray();
		
		//设定重组的总次数
		int n = 6;
		
		//3）根据备注信息remark计算出一个长度为n的整型数组intRemark用于操作各个字符型数组的拆分与其部分子数组的倒序
		int[][] intRemark = Algorithms.setRemark(charRemark, charPwd.length, n);
		
		//4）根据intRemark的值将characters、chaoracterArr重组（拆分、部分子数组倒序、重组）（characterArr重组成两个新数组）
		characters = Algorithms.setRecombine(characters, intRemark, 0);
		char[] chsBasicPwd = Algorithms.setRecombine(characterArr, intRemark, 1);
		char[] chsAppName = Algorithms.setRecombine(characterArr, intRemark, 2);
		
		//5）调整待操作的数组的长度为所需的密码长度pwdLength
		charAppName = SetLength.setLength(charAppName, charPwd.length);
		chsBasicPwd = SetLength.setLength(chsBasicPwd, charPwd.length);
		chsAppName = SetLength.setLength(chsAppName, charPwd.length);
		
		//6）根据由Characters.CHARACTER生成的新数组对应用名称charAppName移位
		charAppName = CharShift.charArrShift(charAppName, chsAppName, characters);
		
		//7）根据intRemark的值将charAppName重组（拆分、部分子数组倒序、重组）
		charAppName = Algorithms.setRecombine(charAppName, intRemark, 4);

		//8）还原charPwd为最后一次重组之前的状态
		charPwd = Algorithms.antiSetRecombine(charPwd, intRemark, 5);
		
		//9）还原charPwd为最后一次移位之前的状态，如characters并非全键盘字符集合则本步骤有可能会失真，集合越小失真越严重
		char[][] charPwdArr = CharShift.antiCharArrShift(charPwd, charAppName, characters);
		charPwd = CharShift.ergodicCharArr(charPwdArr)[0];
		
		//10）还原charPwd为第一次重组之前的状态
		charPwd = Algorithms.antiSetRecombine(charPwd, intRemark, 3);

		//11）还原charPwd为第一次移位之前的状态，如characters并非全键盘字符集合则本步骤有可能会失真，集合越小失真越严重
		charPwdArr = CharShift.antiCharArrShift(charPwd, chsBasicPwd, characters);
		charPwd = CharShift.ergodicCharArr(charPwdArr)[0];

		//11A）还原charPwd为调整长度之前的状态，如果密码长度与原密码长度不匹配一定会产生失真
		charPwd = SetLength.antiFillLength(charPwd, basicPwdLength);
		
		//12）将还原后的字符转换为字符串basicPwd返回
		String basicPwd = new String(charPwd);
		return basicPwd;
	}
	/**
	 * 控制台运行
	 */
	public static void console() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入基础密码");
		String basicPwd = scan.nextLine().trim();
		System.out.println("请输入应用名称");
		String appName = scan.nextLine().trim();
		System.out.println("请输入备注信息");
		String remark = scan.nextLine().trim();
		int pwdLength;
		int complexity;
		do {
			System.out.println("请输入密码位数");
			pwdLength = scan.nextInt();
		} while (pwdLength <= 0);	
		do {
			System.out.println("请输入复杂程度\n  0 - 纯数字\n  1 - 小写字母+数字\n  2 - 大小写字母+数字\n  3 - 全键盘\n  4 - 自定义区域");
			complexity = scan.nextInt();
		} while (complexity < 0 || complexity > 4);
		scan.close();
		String password = version_1(basicPwd, appName, remark, pwdLength, complexity);
		System.out.println("生成的密码为：\n" + password);
	
	}
	
	@SuppressWarnings("unchecked")
	public static void generator4XML(String xmlPath) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("请输入请求密码信息");
//		String desc = scanner.nextLine();
//		scanner.close();
		try {
			SAXReader reader = new SAXReader();
			File properties = new File(xmlPath);
			Document doc = reader.read(properties);
			Element root = doc.getRootElement();
			List<Element> users = root.elements();
			for (Element user : users) {
				String basicPwd = user.elementText("basicpwd");
				List<Element> applications = user.element("applications").elements("application");
				for (Element application : applications) {
					String description = application.elementText("description");
//					if (desc.equals(description)) {
						String appName = application.elementText("appname");
						String remark = application.elementText("remark");
						int pwdLength = Integer.parseInt(application.elementText("pwdlength"));
						int complexity = Integer.parseInt(application.elementText("complexity"));
						String password = version_1(basicPwd, appName, remark, pwdLength, complexity);
						System.out.println(description);
						System.out.println(password);
						System.out.println();
//						break;
//					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		generator4XML("src/com/peknight/passwordgenerator/applications.xml");
	}
}