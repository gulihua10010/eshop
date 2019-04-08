package cn.jianwoo.eshop.sso.web.config;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "c57f7c1f9daf244c3ae62f0e7c6ea6c6";
	private static final String geetest_key = "1ffcf1559c1eae2cfedea93e635a70fd";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
