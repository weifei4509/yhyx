package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MemberEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.view.adapter.MemberRankAdapter;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/5 0005  0:53
 * @email 441293364@qq.com
 * @TODO <p/>
 * ** *** ━━━━━━神兽出没━━━━━━
 * ** ***       ┏┓　　  ┏┓
 * ** *** 	   ┏┛┻━━━┛┻┓
 * ** *** 　  ┃　　　　　　　┃
 * ** *** 　　┃　　　━　　　┃
 * ** *** 　　┃　┳┛　┗┳　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┃　　　┻　　　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┗━┓　　　┏━┛
 * ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 * ** *** 　　　　┃　　　┃
 * ** *** 　　　　┃　　　┗━━━┓
 * ** *** 　　　　┃　　　　　　　┣┓
 * ** *** 　　　　┃　　　　　　　┏┛
 * ** *** 　　　　┗┓┓┏━┳┓┏┛
 * ** *** 　　　　  ┃┫┫  ┃┫┫
 * ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class MemberRankActivity extends BaseActivity {

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;

	MemberRankAdapter rankAdapter;

//	@Bind(R.id.tv_user_name_01)
//	TextView tv_user_name_01;
//	@Bind(R.id.tv_user_name_02)
//	TextView tv_user_name_02;
//	@Bind(R.id.tv_user_name_03)
//	TextView tv_user_name_03;
//
//	@Bind(R.id.tv_reward_num_01)
//	TextView tv_reward_num_01;
//	@Bind(R.id.tv_reward_num_02)
//	TextView tv_reward_num_02;
//	@Bind(R.id.tv_reward_num_03)
//	TextView tv_reward_num_03;

	View header = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("会员排行榜");

		initView();
		initData();

	}

	@Override
	public int bindLayout() {
		return R.layout.activity_member_rank;
	}

	private void initView() {
		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(llm);

		rankAdapter = new MemberRankAdapter();
		header = LayoutInflater.from(mContext).inflate(R.layout.header_member_rank_layout, null, false);


		rankAdapter.addHeaderView(header);
		rankAdapter.openLoadAnimation();
		rankAdapter.setUpFetchEnable(true);

		recyclerView.setAdapter(rankAdapter);
	}

	private void initData() {
		getMemberRankList();
	}

	/**
	 * 获取会员排行榜
	 */
	private void getMemberRankList() {
		RetrofitFactory.getInstence().API().rankings().subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<MemberEntity>>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<List<MemberEntity>> t) throws Exception {
						if (t.getData() != null && t.getData().size() > 0) {
							List<MemberEntity> datas = t.getData();
							int size = datas.size();
							for (int i = 0; i < 3; i++) {
								if (i < size && header != null) {
									MemberEntity memberEntity = datas.get(i);
									ImageView face = header.findViewById(R.id.iv_user_face_01 + i);
									if (Util.isNotEmpty(memberEntity.getHeadpicUrl()) && memberEntity.getHeadpicUrl().startsWith("http")) {
										Glide.with(mContext).load(memberEntity.getHeadpicUrl()).asBitmap().error(R.mipmap.icon_tuxiang_2).placeholder(R.mipmap.icon_tuxiang_2).into(face);
									} else {
										Glide.with(mContext).load(R.mipmap.icon_tuxiang_2).asBitmap().into(face);
									}
									((TextView) header.findViewById(R.id.tv_user_name_01 + i)).setText(memberEntity.getNickname());
									((TextView) header.findViewById(R.id.tv_reward_num_01 + i)).setText(memberEntity.getMoney());
								}
							}

							rankAdapter.setNewData(datas.subList(3, datas.size()));

						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}
}
