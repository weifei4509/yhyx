package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RecomEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.Util;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTeamActivity extends BaseActivity {


	@Bind(R.id.tv_vip_zhijie)
	TextView tvVipZhijie;
	@Bind(R.id.tv_vip_jianjie)
	TextView tvVipJianjie;
	@Bind(R.id.tv_putong_zhijie)
	TextView tvPutongZhijie;
	@Bind(R.id.tv_putong_jianjie)
	TextView tvPutongJianjie;

	@Bind({R.id.tv_tuijianren})
	TextView tv_tuijianren;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("我的团队");
		RetrofitFactory.getInstence().API()
				.recommendTeam(CacheInfo.getUserID(this))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<RecomEntity>(this) {
					@Override
					protected void onSuccees(final BaseEntity<RecomEntity> t) throws Exception {
						tvVipZhijie.setText(t.getData().getDirectViped() + "");
						tvVipJianjie.setText(t.getData().getIndirectViped() + "");
						tvPutongZhijie.setText(t.getData().getDirectUnviped() + "");
						tvPutongJianjie.setText(t.getData().getIndirectUnviped() + "");

						String tuijianren = t.getData().getMineRecommend();
						if (Util.isEmpty(tuijianren)) {
							tv_tuijianren.setText("无");
						} else {
							tv_tuijianren.setText(tuijianren);
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_my_team;
	}

	@OnClick({R.id.rl_vip, R.id.rl_normal})
	public void onViewClicked(View view) {
		Intent intent = new Intent(this, TeamDetailActivity.class);
		switch (view.getId()) {

			case R.id.rl_vip:
				intent.putExtra(Constants.INTENT_KEY_TEAM_TYPE, TeamDetailActivity.VIP);
				startActivity(intent);

				break;
			case R.id.rl_normal:
				intent.putExtra(Constants.INTENT_KEY_TEAM_TYPE, TeamDetailActivity.Normal);
				startActivity(intent);
				break;
		}
	}
}
