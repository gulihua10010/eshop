package cn.jianwoo.eshop.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * 各种id生成策略
 */
public class IDUtils {
	private   static  final String ORDER_TIME="yyyyMMddHHmmsssss";

	/**
     * 图片名生成
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}
	
	/**
	 * 商品id生成
	 */
	public static long genItemId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}
	private   static  String randomNum(int len){
		String s="";
		for ( int i=0;i<len;i++){
			Random random=new Random();
			s+=random.nextInt(10);
		}
		return  s;
	}
	public  static  String genOrderId(){
		SimpleDateFormat sdf=new SimpleDateFormat(ORDER_TIME);
		Calendar calendar=Calendar.getInstance();
		String orderid=sdf.format(calendar.getTime())+randomNum(10);
		return  orderid;

	}
	public   static  String randomChar(int len){
		String s="";
		String word="0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
		for ( int i=0;i<len;i++){
			Random random=new Random();
			int ii=random.nextInt(word.length());
			s+=word.charAt(ii);
		}
		return  s;
	}

}