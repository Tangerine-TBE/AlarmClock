package com.example.module_usercenter.utils;


import com.example.module_base.base.BaseApplication;
import com.example.module_base.util.PackageUtil;

public class Contents {

    //一键登录密钥
    //正式版
    public static final String OAUTH="y+czmTYnrQxWjdb3ISbKZDo5Sy2ILDcvkHiV48RxiMHtVHA0g5msOElKeSzpbjACu0cSpMHVp9usRwMWwo8V6pRLpidoShvbPFh17P6UR314xxJj9PVSQc3KcITk6173MS8Z3wdp1FvnFWWGccvKxOcsHHRJVTjovaTNpo4D1j+F/t+lmz86IUxV6XqbJiqvOcbzfLYkdUrIQ6i6s8TRGxas6/0TVMXNa6Hwn41rzx/tUNXpvjMzk/MTRz66wqn7ok9pvaAeBPkwkUFtj0AmVcd20o+dqcZXPSmBzzxVoyZm9NDCnKY9iKIZdsJMazpT";
    //广告测试版
   // public static final String OAUTH="ERp1eKYioAS2vWah9le0FRXuM3HHB231sz4Fyc6CdgziGaveJOJehkP330uGtE0NqZCMcIyhrN0CipHmvWt7uHWoKaG6Cs2yfy6a+arR4NaZ7gQQOrc7st0HukYpVVWPtrlb4PcT8LLlsEUypASJsy4daWf12pbplNv7pP6ely0QnBvsLzHWdWrozWQcwaZAN3N3MH9YkaWBSi/8NDu7vbtEWW5uyJmn0qK58i9DNAZBvbM38kjRQFoZSbWXUu1hYtIsNg/9/6QXKJLtnOVqXh5BWZ5IwMuvsRRXEa56KKNiGP32DYnZoQ==";
    //包名
    public static final String APP_PACKAGE = PackageUtil.getAppProcessName(BaseApplication.Companion.getAppContext());

    public static final String PLATFORM_KEY = "CHANNEL";//平台
    public static final String APP_NAME = "APP_NAME";//app名字


    //是否需要后台广告
    public static final String BACK_SP="back_sp";
    public static final String NOT_BACK="not_back";


    //是否是购买页面
    public static final String BUY_PAGER_SP="buy_pager_sp";
    public static final String BUY_PAGER="buy_pager";

    //是否在功能页2VIP页
    public static final String TO_BUY="to_buy";


    //注册页面参数

    public static final String ACTIVITY="activity_type";
    public static final String REGISTER="register";
    public static final String CHANGE_PWD="change_pwd";



    //注册、登陆参数
    public static final String SERVICE = "service";//接口名
    public static final String MOBILE = "mobile";//电话
    public static final String PACKAGE = "package";//包名
    public static final String SIGNATURE = "signature";//验证码
    public static final String NONCE = "nonce";//随机数
    public static final String TIMESTAMP = "timestamp";//时间戳
    public static final String TOKEN = "^x389fhfeahykge";//token值
    public static final String QQ_TYPE = "1";//QQ
    public static final String WECHAT_TYPE = "0";//微信
    public static final String OPENID = "openId";//openId
    public static final String TYPE = "type";//openId
    public static final String CODE = "code";//验证码
    public static final String VER = "ver";//软件版本
    public static final String PLATFORM = "platform";//平台
    public static final String PASSWORD = "password";//密码

    //支付参数
    public static final String TRADE = "trade";//订单号
    public static final String SUBJECT = "subject";//标题
    public static final String PRICE = "price";//价格
    public static final String BODY = "body";//内容
    //购买信息
    public static final String VIP13 = "VIP13";
    public static final String VIP12 = "VIP12";
    public static final String VIP3 = "VIP3";
    public static final String VIP1 = "VIP1";

    public static final String VIP_title_13 = "永久卡";
    public static final String VIP_title_12 = "一年";
    public static final String VIP_title_3 = "三个月";
    public static final String VIP_title_1 = "一个月";

    public static final double VIP_price_13 = 26;
    public static final double VIP_price_12 = 25;
    public static final double VIP_price_3 = 24;
    public static final double VIP_price_1 = 23;

/*    //测试
    public static final double VIP_price_13 = 0.01;
    public static final double VIP_price_12 = 0.01;
    public static final double VIP_price_3 =0.01;
    public static final double VIP_price_1 = 0.01;*/

    //储存用户信息
    public static final String LOCAL_TYPE = "2";//本地登陆
    public static final String USER_INFO = "user_info";
    public static final String USER_ID = "id";
    public static final String USER_IS_LOGIN = "isLogin";
    public static final String USER_ID_TYPE= "id_type";
    public static final String USER_ACCOUNT= "user_account";
    public static final String USER_PWD= "user_pwd";
    public static final String USER_THIRDLY_OPENID= "user_thirdly_openid";
    public static final String USER_VIP_LEVEL = "vip_level";
    public static final String USER_VIP_TIME = "vip_time";
    public static final String USER_LOGIN_TIME = "user_login_time";


    //QQ  id
    public static final String QQ_ID = "1109742333";

    //微信
    public static final String WECHAT_APP_ID = "wx1708ad8d4a6e4f8f";
    public static final String WECHAT_SECRET = "afdbbe33ed530310eed0b8863578ae28";
    public static final String WXAPPID = "appid";
    public static final String WXSECRET = "secret";
    public static final String WXACODE = "code";
    public static final String WXTYPE = "grant_type";





    /*--------------/注册接口-----------*/
    //微信
    public static final String WECHAT_URL = "https://api.weixin.qq.com/sns/oauth2/";
    //域名
    public static final String USER_URL = "https://www.aisou.club";
    //支付
    public static final String PAY_WX_URL = "http://www.aisou.club/pay/wxh5/dafa.php?";
    public static final String PAY_ALI_URL = "http://www.aisou.club/pay/aliv2/wappay/pay.php?";
    ////////////////////SeverName///////////////////
    //注册验证码
    public static final String GET_CODE = "passport.regcode";
    //找回密码验证码
    public static final String GET_FIND_PWD_CODE = "passport.findPassword";
    //校验验证码
    public static final String CHECK_CODE = "passport.checkcode";
    //注册
    public static final String ADD_USER = "passport.registerByMobile";
    //登录
    public static final String LOGIN = "passport.loginMobile";
    //找回密码
    public static final String FIND_PWD = "passport.setPassByFind";
    //注销账号
    public static final String DELETE_USER  ="passport.unregister";
    //ali_pay
    public static final String ALI_PAY = "ALI";
    //wx_pay
    public static final String WX_PAY = "WX";
    //验证QQ微信是否注册
    public static final String CHECK_THIRD = "passport.checkThird";
    //第三方注册
    public static final String REGISTER_BY_THIRD = "passport.registerByThird";
    //第三方登录
    public static final String LOGIN_THIRD = "passport.loginThird";
    //获取手机号码
    public static final String GET_PHONE = "passport.getMobile";

    public static final String URL_KEY="url_key";


}
