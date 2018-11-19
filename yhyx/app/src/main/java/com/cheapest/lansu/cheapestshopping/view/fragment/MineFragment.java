package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.OverFlowEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.AboutUsActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.BinderPhoneActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.BrowseActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.CollectAndReferActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ConsumptionPointsActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.CustomerCenterActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.FeedBackActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.LoginActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MemberBenefitsActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MemberRankActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MemberRewardsActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MyInformationActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MyTeamActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MyWalletActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.PropagandaActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.SettingActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ShaiShaiActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ToBeVIPActivity;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;
import com.cheapest.lansu.cheapestshopping.view.dialog.ShareDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的界面
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

	@Bind(R.id.iv_user_icon)
	CircleImageView ivUserIcon;
	@Bind(R.id.tv_user_name)
	TextView tvUserName;
	//	@Bind(R.id.iv_user_vip)
//	ImageView ivUserVip;
	@Bind(R.id.tv_invate_code)
	TextView tvInvateCode;
	@Bind(R.id.rl_invate)
	RelativeLayout rlInvate;
	@Bind(R.id.tv_jiangli)
	TextView tvJiangli;
	@Bind(R.id.tv_jifen)
	TextView tvJifen;
	@Bind(R.id.tv_fuli)
	TextView tvFuli;
//	@Bind(R.id.tv_qianbao)
//	TextView tvQianbao;

	@Bind(R.id.tv_zhuce_vip)
	TextView tv_zhuce_vip;

	public MineFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_mine, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.tv_feedback, R.id.rl_share_app, R.id.iv_rank_list, R.id.rl_invate, R.id.tv_setting, R.id.lin_my_info, R.id.lin_my_collect, R.id.lin_liulan, R.id.rl_member_rewards, R.id.rl_jifen, R.id.rl_member_fuli, R.id.rl_my_wallet, R.id.rl_shengji_vip, R.id.tv_my_team, R.id.rl_bangding_phone, R.id.rl_xuan_chuan, R.id.rl_shaishai, R.id.rl_about_us, R.id.rl_customer, R.id.tv_copy})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_feedback: //意见反馈
				if (UserInfoUtils.isSharedLogin(getActivity()))
					startActivity(new Intent(getActivity(), FeedBackActivity.class));
				else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.iv_rank_list://查看排行榜
				startActivity(new Intent(getActivity(), MemberRankActivity.class));
				break;

			case R.id.rl_share_app://分享App
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					Bundle args = new Bundle();
					args.putString("title", "省惠优选优惠券APP");
					args.putString("content", "不花冤枉钱，先领券再消费，能省的绝不多花一分，海量商品最高返佣60%");
					args.putString("iconUrl", "https://thumbnail0.baidupcs.com/thumbnail/98727ca874586d4cf88e35becabad59f?fid=3192106415-250528-663858165530004&time=1533232800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-0kn9N%2FElyJZVam2tkMgnAgVx8rg%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=4965297418795555052&dp-callid=0&size=c710_u400&quality=100&vuk=-&ft=video");
					args.putString("shareUrl", "http://jiuchiwl.cn/other/recommend?recommend=" + CacheInfo.getUserInfoFromCache(getActivity()).getRecommendCode());
					ShareDialog.newInstance(getActivity(), args).show(this.getFragmentManager(), "shareApp");
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_invate:
//                if (UserInfoUtils.isSharedLogin(getActivity())) {
//                    startActivity(new Intent(getActivity(), InvateActivity.class));
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//                }
				break;
			case R.id.tv_setting:
				startActivity(new Intent(getActivity(), SettingActivity.class));
				break;

			case R.id.lin_my_info:
				if (UserInfoUtils.isSharedLogin(getActivity()))
					startActivity(new Intent(getActivity(), MyInformationActivity.class));
				else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.lin_my_collect:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					Intent intent = new Intent(getActivity(), CollectAndReferActivity.class);
					startActivity(intent);
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.lin_liulan:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					Intent intent = new Intent(getActivity(), BrowseActivity.class);
					startActivity(intent);
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_member_rewards:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), MemberRewardsActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.rl_jifen:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), ConsumptionPointsActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_member_fuli:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), MemberBenefitsActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_my_wallet:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), MyWalletActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_shengji_vip:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), ToBeVIPActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.tv_my_team:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), MyTeamActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}


				break;
			case R.id.rl_bangding_phone:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), BinderPhoneActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_xuan_chuan:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), PropagandaActivity.class));
				} else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.rl_shaishai:
				if (UserInfoUtils.isSharedLogin(getActivity())) {
					startActivity(new Intent(getActivity(), ShaiShaiActivity.class));
				} else {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
				break;

			case R.id.rl_about_us:
				startActivity(new Intent(getActivity(), AboutUsActivity.class));
				break;

			case R.id.rl_customer:
//				callCustomer();
				startActivity(new Intent(getActivity(), CustomerCenterActivity.class));
				break;

			case R.id.tv_copy:
				ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
				if (CacheInfo.getUserInfoFromCache(getActivity()) != null) {
					cm.setText(CacheInfo.getUserInfoFromCache(getActivity()).getRecommendCode() + "");
					Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_LONG).show();
				}
				break;
		}
	}

	@Override
	public void onResume() {

		super.onResume();
		if (UserInfoUtils.isSharedLogin(getActivity())) {
			updateUserInfo(); //更新用户信息
			getOverFlow();

		} else {
			tvUserName.setText("登录/注册");
			Glide.with(getActivity()).load(R.mipmap.icon_tuxiang).asBitmap().into(ivUserIcon);

//			ivUserVip.setVisibility(View.GONE);
			rlInvate.setVisibility(View.GONE);
			tv_zhuce_vip.setVisibility(View.GONE);
			tvFuli.setText("0.00");
			tvJiangli.setText("0.00");
			tvJifen.setText("0");
//			tvQianbao.setText("0.00");
		}


	}

	private void getOverFlow() {
		RetrofitFactory.getInstence().API()
				.overview(CacheInfo.getUserID(getActivity()))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<OverFlowEntity>(getActivity()) {
					@Override

					protected void onSuccees(final BaseEntity<OverFlowEntity> t) throws Exception {
						tvFuli.setText(t.getData().getWelfare() + "");
						tvJiangli.setText(t.getData().getReward() + "");
						tvJifen.setText(t.getData().getScore() + "");
//						tvQianbao.setText(t.getData().getBalance() + "");
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});

	}

	/**
	 * 更新用户信息
	 */
	private void updateUserInfo() {
		Log.i("token", CacheInfo.getUserToken(getActivity()));
		RetrofitFactory.getInstence().API()
				.updateUserInfo(CacheInfo.getUserID(getActivity()))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<UserEntity>(getActivity()) {
					@Override
					protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
						UserInfoUtils.saveUserInfo(t.getData(), getActivity());
						tvInvateCode.setText(t.getData().getRecommendCode() + "");
						tvUserName.setText(t.getData().getNickname());
						Glide.with(getActivity()).load(t.getData().getHeadpicUrl()).asBitmap().error(R.mipmap.icon_tuxiang).placeholder(R.mipmap.icon_tuxiang).into(ivUserIcon);

						if (t.getData().isViped() == 1) {
							tv_zhuce_vip.setVisibility(View.VISIBLE);
							tv_zhuce_vip.setText("VIP会员");
//							ivUserVip.setVisibility(View.VISIBLE);
//							ivUserVip.setImageResource(R.mipmap.viphuiyua);
						} else {
							tv_zhuce_vip.setVisibility(View.VISIBLE);
							tv_zhuce_vip.setText("注册会员");
//							ivUserVip.setVisibility(View.GONE);
//							ivUserVip.setImageResource(R.mipmap.memeber_normal);
						}
						//  rlInvate.setVisibility(View.VISIBLE);


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}


}
