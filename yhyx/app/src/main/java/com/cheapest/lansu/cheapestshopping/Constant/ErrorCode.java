package com.cheapest.lansu.cheapestshopping.Constant;

/*
* 文件名：ErrorCode
* 描    述：
* 作    者：lansu
* 时    间：2018/5/7 17:31
* 版    权： 云杉智慧新能源技术有限公司版权所有
*/
public   class ErrorCode {
/**
 *
 * 401	未登录
 400	请求参数错误
 500	意料之外的错误，未处理的错误
 701	当前账户已在其他手机登陆，您已被迫下线，请重新登陆
 702	发送短信验证码失败	发送短信验证码失败
 703	短信验证码错误	无效的短信验证码
 801	手机号已经注册	当前手机号已经注册

 *
 *
 */
   public static  int  Not_Login=401;
   public static  int  Param_Error=400;
   public static  int  Other_Error=500;
   public static  int  Forced_Logoff=701;
   public static  int  GetSms_Fail=702;
   public static  int  Sms_Error=703;
   public static  int  Phone_Exist=801;











}
