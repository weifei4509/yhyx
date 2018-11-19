package com.cheapest.lansu.cheapestshopping.model.http;

import com.cheapest.lansu.cheapestshopping.model.entity.AddressListModel;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.model.entity.BalanceRecordEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BannerEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BrandEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CityInfoModel;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollctionEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CommodityEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.GoodDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MemberEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MessageEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.OrderEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.OverFlowEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.PropagandaEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RecomEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RewardEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SProductModel;
import com.cheapest.lansu.cheapestshopping.model.entity.ScoreEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ScoreListEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninModel;
import com.cheapest.lansu.cheapestshopping.model.entity.SplashEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TbkGoodsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TeamEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VersionEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VipEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfListEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfareEntity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


/*
 * 文件名：HttpService
 * 描    述：
 * 作    者：lansu
 * 时    间：2018/4/26 11:05
 * 版    权： lansus
 */
public interface RetrofitService {


	/**
	 * 用户注册
	 *
	 * @param mobile        手机
	 * @param smscode       验证码
	 * @param password      密码
	 * @param recommendCode 邀请码
	 * @return 用户信息
	 */
	@FormUrlEncoded
	@POST("api/auth/register")
	Observable<BaseEntity<UserEntity>> register(@Field("mobile") String mobile, @Field("smscode") String smscode, @Field("password") String password, @Field("recommendCode") String recommendCode);


	@FormUrlEncoded
	@POST("api/auth/thirdLogin")
	Observable<BaseEntity<UserEntity>> thirdLogin(@Field("thirdPartyType") int thirdPartyType, @Field("thirdPartyUid") String thirdPartyUid, @Field("nickname") String nickname, @Field("thirdPartyHeadpic") String thirdPartyHeadpic);


	/**
	 * 获取验证码
	 *
	 * @param mobile 手机号
	 */

	@GET("api/auth/smscode")
	Observable<BaseEntity<String>> getSms(@Query("mobile") String mobile);

	/**
	 * 获取启动页图片
	 */
	@GET("api/system/bootPages")
	Observable<BaseEntity<List<SplashEntity>>> bootPages();


	@GET()
	Call<ResponseBody> get(@Url String url);


	/**
	 * 获取关于我们的信息
	 */
	@GET("api/system/aboutus")
	Observable<BaseEntity<String>> aboutus();

	/**
	 * 通过密码登录
	 *
	 * @param mobile     手机号
	 * @param password   密码
	 * @param mobileCode 极光推送设备码
	 * @return 用户信息
	 */
	@FormUrlEncoded
	@POST("api/auth/login")
	Observable<BaseEntity<UserEntity>> loginByPassword(@Field("mobile") String mobile, @Field("password") String password, @Field("mobileCode") String mobileCode);

	/**
	 * 通过验证码方式登录
	 * * @param mobile 手机号
	 *
	 * @param smscode    验证码
	 * @param mobileCode 极光推送设备号
	 * @return 用户信息
	 */
	@FormUrlEncoded
	@POST("api/auth/smscodeLogin")
	Observable<BaseEntity<UserEntity>> loginBySms(@Field("mobile") String mobile, @Field("smscode") String smscode, @Field("mobileCode") String mobileCode);

	/**
	 * 忘记密码
	 *
	 * @param mobile   手机号
	 * @param smscode  验证码
	 * @param password 密码
	 * @return 用户信息
	 */
	@FormUrlEncoded
	@POST("api/auth/forget")
	Observable<BaseEntity<UserEntity>> forgetPassWord(@Field("mobile") String mobile, @Field("smscode") String smscode, @Field("password") String password);

	/**
	 * 更新用户信息
	 *
	 * @param userId 用户Id
	 * @return 用户信息
	 */

	@GET("api/personal/userInfo")
	Observable<BaseEntity<UserEntity>> updateUserInfo(@Query("userId") String userId);

	/**
	 * 修改密码
	 *
	 * @param mobile      手机号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @return resultCode
	 */
	@FormUrlEncoded
	@POST("api/auth/updatePassword")
	Observable<BaseEntity<String>> updatePassword(@Field("mobile") String mobile, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

	/**
	 * 注销用户
	 *
	 * @param userId 用户Id
	 * @return result
	 */
	@FormUrlEncoded
	@POST("api/auth/logout")
	Observable<BaseEntity<String>> logout(@Field("userId") String userId);

	/**
	 * 绑定手机号
	 *
	 * @param userId 用户id
	 * @return string
	 */
	@FormUrlEncoded
	@POST("api/auth/bindPhone")
	Observable<BaseEntity<UserEntity>> bindPhone(@Field("userId") String userId, @Field("mobile") String mobile, @Field("smscode") String smscode);


	/**
	 * 用户升级
	 * 1-用户端(默认) 2-商家端
	 * machineType	int		1-安卓（默认） 2-IOS
	 */
	@FormUrlEncoded
	@POST("api/system/version")
	Observable<BaseEntity<VersionEntity>> version(@Field("appType") String appType, @Field("machineType") String smscode);

	/**
	 * 修改用户信息
	 *
	 * @param options
	 * @return
	 */
	@FormUrlEncoded
	@POST("api/personal/edit")
	Observable<BaseEntity<UserEntity>> editInfo(@FieldMap Map<String, String> options);


	/**
	 * k客服电话
	 */
	@POST("api/system/contact")
	Observable<BaseEntity<String>> contact();

	/**
	 * 获取收藏列表
	 */

	@GET("api/personal/collections")
	Observable<BaseEntity<CollctionEntity>> collections(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/**
	 * 增加和删除收藏列表
	 */
	@FormUrlEncoded
	@POST("api/personal/collection/add")
	Observable<BaseEntity<String>> addCollections(@Field("customerId") String customerId, @Field("type") int type, @Field("targetId") int targetId);

	/**
	 * 获取收浏览列表
	 */

	@GET("api/personal/visitList")
	Observable<BaseEntity<CollctionEntity>> visitLis(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);


	/**
	 * 删除浏览列表
	 */
	@POST()
	Observable<BaseEntity<String>> deleteVisit(@Url String url);

	/**
	 * 获取详情
	 */
	@GET()
	Observable<BaseEntity<GoodDetailEntity>> getGoodDetail(@Url String url, @Query("userId") String userId);

	/*
	 * 获取消息列表
	 */

	@GET("api/personal/msgs")
	Observable<BaseEntity<MessageEntity>> getMsgs(@Query("page") int page, @Query("size") int size, @Query("userId") String userId, @Query("role") int role);

	/**
	 * 删除浏览列表
	 */
	@FormUrlEncoded
	@POST()
	Observable<BaseEntity<String>> deleteMsg(@Url String url, @Field("userId") String userid);

	/**
	 * bannert图信息
	 *
	 * @param type
	 * @return
	 */
	@GET("api/system/banners")
	Observable<BaseEntity<List<BannerEntity>>> getBanner(@Query("type") int type);

	/**
	 * 特色商品分类
	 */
	@GET("api/system/commoditySpecialCategories")
	Observable<BaseEntity<CategoriesEntity>> commoditySpecialCategories(@Query("page") int page, @Query("size") int size);

	/**
	 * 商品分类
	 */
	@GET("api/system/commodityCategories")
	Observable<BaseEntity<CategoriesEntity>> commodityCategories(@Query("page") int page, @Query("size") int size);


	/**
	 * 提现须知
	 */
	@GET("api/system/fetchNotice")
	Observable<BaseEntity<String>> fetchNotice();


	/**
	 * 上传文件
	 *
	 * @param part
	 * @return
	 */
	@Multipart
	@POST("ajax/upfile")
	Observable<BaseEntity<String>> upfile(@Part MultipartBody.Part part);


	/**
	 * 必买清单
	 */
	@GET("api/commodity/needBuyedList")
	Observable<BaseEntity<ProductEntity>> needBuyedList(@Query("page") int page, @Query("size") int size);


	/**
	 * 9.9专场列表
	 */
	@GET("api/commodity/nineCommodityList")
	Observable<BaseEntity<ProductEntity>> nineCommodityList(@Query("page") int page, @Query("size") int size);

	/**
	 * 今日推荐商品列表列表
	 */
	@GET("api/commodity/todayRecommendedCommodityList")
	Observable<BaseEntity<ProductEntity>> todayRecommendedCommodityList(@Query("type") String type, @Query("sortType") String sortType, @Query("orderType") String orderType, @Query("page") int page, @Query("size") int size);

	/**
	 * 品牌专区
	 */
	@GET("api/commodity/brandSellerList")
	Observable<BaseEntity<BrandEntity>> brandSellerList(@Query("page") int page, @Query("size") int size);

	/*
	 会员奖励列表
	 */
	@GET("api/personal/rewards")
	Observable<BaseEntity<RewardEntity>> rewardsList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/*
	  会员福利列表
	  */
	@GET("api/personal/welfares")
	Observable<BaseEntity<WelfListEntity>> welfaresList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);


	/**
	 * 会员奖励总额
	 */

	@GET("api/personal/reward")
	Observable<BaseEntity<String>> rewardAmount(@Query("userId") String userId);

	/**
	 * 会员福利概览
	 */

	@GET("api/personal/welfare")
	Observable<BaseEntity<WelfareEntity>> welfare(@Query("userId") String userId);

	/**
	 * 我的钱包
	 */

	@GET("api/personal/balance")
	Observable<BaseEntity<String>> getBalance(@Query("userId") String userId);

	/*
	  获取我的钱包纪录列表
	  */
	@GET("api/personal/balances")
	Observable<BaseEntity<BalanceRecordEntity>> balances(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/*
		升级vip说明
		*/
	@GET("api/personal/vipExplain")
	Observable<BaseEntity<String>> vipExplain(@Query("userId") String userId);

	/*
		 团队人数
		 */
	@GET("api/personal/recommendTeam")
	Observable<BaseEntity<RecomEntity>> recommendTeam(@Query("userId") String userId);

	/*
		 团队列表
		 */
	@GET("api/personal/recommendTeams")
	Observable<BaseEntity<TeamEntity>> recommendTeams(@Query("userId") String userId, @Query("type") int type, @Query("role") int role, @Query("page") int page, @Query("size") int size);

	/**
	 * 品牌专区
	 */
	@GET("api/commodity/brandCommodityList")
	Observable<BaseEntity<ProductEntity>> brandCommodityList(@Query("brandSellerId") String brandSellerId, @Query("sortType") int sortType, @Query("orderType") int orderType, @Query("page") int page, @Query("size") int size);

	/**
	 * 分类商品列表
	 * categoryId 	int 		分类id
	 * type 	int 		宝贝类型 1-淘宝 2-天猫 不传则为全部
	 * sortType 	int 		排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	 * orderType 	int 		排序 方式 1-升序 2-降序 默认1
	 * page 	int 		分页参数
	 * size 	int 		分页参数
	 */
	@GET("api/commodity/commodityList")
	Observable<BaseEntity<ProductEntity>> commodityList(@Query("categoryId") String categoryId, @Query("type") String type, @Query("sortType") int sortType, @Query("orderType") int orderType, @Query("page") int page, @Query("size") int size);


	/**
	 * 分类商品列表
	 * categoryId 	int 		分类id
	 * type 	int 		宝贝类型 1-淘宝 2-天猫 不传则为全部
	 * sortType 	int 		排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	 * orderType 	int 		排序 方式 1-升序 2-降序 默认1
	 * page 	int 		分页参数
	 * size 	int 		分页参数
	 */
	@GET("api/commodity/commodityList")
	Observable<BaseEntity<ProductEntity>> SearchCommodityList(@Query("keywords") String keywords, @Query("type") String type, @Query("sortType") int sortType, @Query("orderType") int orderType, @Query("page") int page, @Query("size") int size);


	/**
	 * 特色分类商品列表
	 * categoryId 	int 		分类id
	 * type 	int 		宝贝类型 1-淘宝 2-天猫 不传则为全部
	 * sortType 	int 		排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	 * orderType 	int 		排序 方式 1-升序 2-降序 默认1
	 * page 	int 		分页参数
	 * size 	int 		分页参数
	 */
	@GET("api/commodity/specialCommodityList")
	Observable<BaseEntity<ProductEntity>> specialCommodityList(@Query("specialCategoryId") String specialCategoryId, @Query("type") String type, @Query("sortType") int sortType, @Query("orderType") int orderType, @Query("page") int page, @Query("size") int size);


	/**
	 * 提交晒晒
	 */
	@FormUrlEncoded
	@POST("api/personal/harvest")
	Observable<BaseEntity<String>> harvest(@Field("image") String image, @Field("customerId") String userid, @Field("content") String content);

	/**
	 * 成为VIp
	 */
	@FormUrlEncoded
	@POST("api/personal/upgradeVip")
	Observable<BaseEntity<VipEntity>> upgradeVip(@Field("userId") String userId, @Field("payType") String payType);

	/**
	 * /**
	 * 第三方授权
	 */
	@FormUrlEncoded
	@POST("api/personal/fetch")
	Observable<BaseEntity<String>> fetch(@Field("userId") String userId, @Field("money") double money);

	/**
	 * 获取物料
	 */

	@GET("api/personal/adteams")
	Observable<BaseEntity<PropagandaEntity>> adteams(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);


	/**
	 * 获取指定链接
	 */
	@GET("api/commodity/clickUrl")
	Observable<BaseEntity<String>> clickUrl(@Query("userId") String userId, @Query("commodityId") int commodityId);

	/**
	 * 获取指定链接
	 */
	@GET("api/commodity/clickUrl")
	Observable<BaseEntity<String>> clickUrl(@Query("userId") String userId, @Query("itemId") String itemId);

	/**
	 * 消费积分
	 *
	 * @param userId 用户id
	 */
	@GET("api/personal/score")
	Observable<BaseEntity<ScoreEntity>> score(@Query("userId") String userId);

	/**
	 * 消费积分详细
	 *
	 * @param userId 用户id
	 */
	@GET("api/personal/scores")
	Observable<BaseEntity<ScoreListEntity>> scores(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/**
	 * 消费积分详细
	 *
	 * @param userId 用户id
	 */
	@FormUrlEncoded
	@POST("api/personal/convertScore")
	Observable<BaseEntity<ScoreEntity>> convertScore(@Field("userId") String userId);

	/**
	 * 积分钱包等信息概览
	 *
	 * @param userId 用户id
	 */

	@GET("api/personal/overview")
	Observable<BaseEntity<OverFlowEntity>> overview(@Query("userId") String userId);


	/**
	 * 二期接口
	 */

	/**
	 * 省惠快报列表
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/news/articles")
	Observable<BaseEntity<NewsEntity>> newsList(@Query("page") int page, @Query("size") int size);

	/**
	 * 新闻详情
	 *
	 * @param url
	 * @return
	 */
	@GET()
	Observable<BaseEntity<NewsEntity.DatasBean>> newsDetail(@Url String url, @Query("userId") String userId);

	/**
	 * 点赞/取消点赞
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	@POST("api/news/article/praise")
	Observable<BaseEntity<String>> newsPraise(@Query("id") int id, @Query("userId") String userId);

	/**
	 * 商品兑换列表
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/sign/swapGoogsList")
	Observable<BaseEntity<ExchangeEntity>> swapGoogsLists(@Query("page") int page, @Query("size") int size);

	/**
	 * 商品兑换详情
	 *
	 * @param url
	 * @return
	 */
	@GET()
	Observable<BaseEntity<ExchangeEntity.DatasBean>> swapGoogs(@Url String url);

	/**
	 * 兑换商品
	 *
	 * @param userId
	 * @param addressId
	 * @param goodsId
	 * @param quantity
	 * @param remark
	 * @return
	 */
	@POST("api/sign/swap")
	Observable<BaseEntity<String>> swap(@Query("userId") String userId, @Query("addressId") String addressId, @Query("goodsId") String goodsId, @Query("quantity") int quantity, @Query("remark") String remark);

	/**
	 * 获取拼团列表
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/group/groupList")
	Observable<BaseEntity<CollageEntity>> groupList(@Query("page") int page, @Query("size") int size);

	/**
	 * 获取拼团订单
	 *
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/group/groupOrders")
	Observable<BaseEntity<OrderEntity>> groupOrders(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/**
	 * 模块商品分类（爱生活/品牌精选）
	 *
	 * @param module
	 * @return
	 */
	@GET("api/commodity/moduleCategories")
	Observable<BaseEntity<List<ClassifyEntity>>> moduleCategories(@Query("module") int module);

	/**
	 * 商品列表（普通商品列表-特色商品列表-模块商品-今日推荐-进口优选）
	 *
	 * @param categoryId
	 * @param subCategoryId
	 * @param specialCategoryId
	 * @param module
	 * @param imported
	 * @param needBuyed
	 * @param keywords
	 * @param type
	 * @param sortType
	 * @param orderType
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> commodities(@Query("categoryId") String categoryId, @Query("subCategoryId") String subCategoryId, @Query("specialCategoryId") String specialCategoryId, @Query("module") int module, @Query("imported") int imported, @Query("needBuyed") int needBuyed, @Query("keywords") String keywords, @Query("type") String type, @Query("sortType") int sortType, @Query("orderType") int orderType, @Query("page") int page, @Query("size") int size);

	/**
	 * 必买清单
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> needBuyedList(@Query("needBuyed") int needBuyed, @Query("page") int page, @Query("size") int size);

	/**
	 * 首页特色列表
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> featureList(@Query("specialCategoryId") String specialCategoryId, @Query("sortType") int sortType, @Query("type") String type, @Query("page") int page, @Query("size") int size);


	@GET("api/commodity/commodities")
	Observable<BaseEntity<CommodityEntity>> commoditiyList(@Query("module") int module, @Query("imported") int imported, @Query("needBuyed") int needBuyed, @Query("page") int page, @Query("size") int size);

	/**
	 * 商品分类 （一级/二级/热卖）
	 *
	 * @param pid
	 * @return
	 */
	@GET("api/commodity/categories")
	Observable<BaseEntity<List<ClassifyEntity>>> categories(@Query("pid") String pid);

	/**
	 * 商品分类 （一级/二级/热卖）
	 *
	 * @return
	 */
	@GET("api/commodity/categories")
	Observable<BaseEntity<List<ClassifyEntity>>> categories();

	/**
	 * 拼团详情
	 *
	 * @param url
	 * @return
	 */
	@GET()
	Observable<BaseEntity<CollageDetailEntity>> collageDetail(@Url String url);

	/**
	 * 查看签到信息
	 *
	 * @param userId
	 * @return
	 */
	@GET("api/sign/survey")
	Observable<BaseEntity<SigninEntity>> survey(@Query("userId") String userId);

	/**
	 * 用戶簽到
	 *
	 * @param userId
	 * @return
	 */
	@POST("api/sign/signin")
	Observable<BaseEntity<String>> signin(@Query("userId") String userId);


	/**
	 * 某月签到天数列表
	 *
	 * @param userId
	 * @param date
	 * @return
	 */
	@GET("api/sign/signins")
	Observable<BaseEntity<List<SigninModel>>> signins(@Query("userId") String userId, @Query("date") String date);

	/**
	 * 会员排行榜
	 *
	 * @return
	 */
	@GET("api/personal/rankings")
	Observable<BaseEntity<List<MemberEntity>>> rankings();

	/**
	 * 意见反馈
	 *
	 * @param userId
	 * @param customerName
	 * @param content
	 * @return
	 */
	@POST("api/system/feedback/add")
	Observable<BaseEntity<String>> addFeedBack(@Query("customerId") String userId, @Query("customerName") String customerName, @Query("content") String content);

	/**
	 * 省市区下拉查询
	 *
	 * @param levelType
	 * @param parentId
	 * @return
	 */
	@GET("api/system/selectCities")
	Observable<BaseEntity<List<CityInfoModel>>> selectCities(@Query("levelType") String levelType, @Query("parentId") String parentId);

	/**
	 * 添加收货地址
	 *
	 * @param customerId
	 * @param deliveryMobile
	 * @param deliveryName
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @param address
	 * @return
	 */
	@POST("api/personal/delivery/add")
	Observable<BaseEntity<String>> addAddress(@Query("customerId") String customerId, @Query("deliveryMobile") String deliveryMobile, @Query("deliveryName") String deliveryName, @Query("provinceId") String provinceId, @Query("cityId") String cityId, @Query("areaId") String areaId, @Query("address") String address);

	/**
	 * 更新收货地址
	 *
	 * @param id
	 * @param customerId
	 * @param deliveryMobile
	 * @param deliveryName
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @param address
	 * @return
	 */
	@POST("api/personal/delivery/update")
	Observable<BaseEntity<String>> updateAddress(@Query("id") String id, @Query("customerId") String customerId, @Query("deliveryMobile") String deliveryMobile, @Query("deliveryName") String deliveryName, @Query("provinceId") String provinceId, @Query("cityId") String cityId, @Query("areaId") String areaId, @Query("address") String address);

	/**
	 * 获取收货地址
	 *
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/personal/deliveries")
	Observable<BaseEntity<AddressListModel>> getAddressList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

	/**
	 * 刪除收貨地址
	 *
	 * @param url
	 * @return
	 */
	@POST()
	Observable<BaseEntity<String>> deleteAddress(@Url String url);

	/**
	 * 开启拼团
	 *
	 * @param groupId
	 * @param userId
	 * @param addressId
	 * @return
	 */
	@POST("api/group/open")
	Observable<BaseEntity<CollageDetailEntity.CustomerGroupModel>> openCollage(@Query("groupId") int groupId, @Query("userId") String userId, @Query("addressId") String addressId);

	/**
	 * 品牌進口列表橫向滾動
	 *
	 * @param imported
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getImportedList(@Query("imported") int imported, @Query("page") int page, @Query("size") int size);

//
//	http://jiuchiwl.cn/api/commodity/commodities?subCategoryId=52&sortType=0&page=1&size=10

	/**
	 * 分类下的二级列表
	 *
	 * @param subCategoryId
	 * @param sortType
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getClassifyList(@Query("subCategoryId") String subCategoryId, @Query("sortType") int sortType, @Query("type") String type, @Query("page") int page, @Query("size") int size);

	/**
	 * 首页大牌二级列表
	 *
	 * @param specialCategoryId
	 * @param page
	 * @param size
	 * @return
	 */
	//	http://jiuchiwl.cn/api/commodity/commodities?specialCategoryId=6&categoryId=27&page=1&size=10
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getProductList(@Query("specialCategoryId") String specialCategoryId, @Query("categoryId") String categoryId, @Query("page") int page, @Query("size") int size);

	/**
	 * 省惠优选排行榜
	 *
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getProductRankList(@Query("categoryId") String categoryId, @Query("page") int page, @Query("size") int size);

	/**
	 * 愛生活
	 *
	 * @param module
	 * @param type
	 * @param sortType
	 * @param page
	 * @param size
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getLoveLifeList(@Query("module") int module, @Query("type") String type, @Query("sortType") String sortType, @Query("page") int page, @Query("size") int size);


	//	http://jiuchiwl.cn/api/commodity/commodities?categoryId=27&subCategoryId=&type=&sortType=&page=1&size=4
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getHomeProductList(@Query("categoryId") String categoryId, @Query("subCategoryId") String subCategoryId, @Query("type") String type, @Query("sortType") String sortType, @Query("page") int page, @Query("size") int size);

	/**
	 * 全网搜索
	 *
	 * @param keyword
	 * @param pageNum
	 * @return
	 */
	@GET("api/commodity/tbkSearch")
	Observable<BaseEntity<List<SProductModel>>> searchWholeList(@Query("keyword") String keyword, @Query("pageNum") int pageNum);


	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> SearchCommodityList(@Query("keywords") String keywords, @Query("type") String type, @Query("sortType") int sortType, @Query("page") int page, @Query("size") int size);


	/**
	 * 获取淘宝客详情
	 */
	@GET("api/commodity/tbkDetail")
	Observable<BaseEntity<TbkGoodsEntity>> getTbkGoodDetail(@Query("itemId") String itemId);

	/**
	 * 爱生活模块数据列表
	 *
	 * @param moduleCategoryId
	 * @param sortType
	 * @param type
	 * @param page
	 * @param size
	 * @param module
	 * @return
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getLoveLifeList(@Query("moduleCategoryId") String moduleCategoryId, @Query("sortType") int sortType, @Query("type") String type, @Query("page") int page, @Query("size") int size, @Query("module") int module);

	/**品牌精选
	 *
	 */
	@GET("api/commodity/commodities")
	Observable<BaseEntity<ProductEntity>> getPinPaiList(@Query("module") int module, @Query("page") int page, @Query("size") int size);

}
