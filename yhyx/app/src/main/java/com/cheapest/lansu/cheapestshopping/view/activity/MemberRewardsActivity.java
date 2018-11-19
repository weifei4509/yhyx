package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RewardEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 会员奖励页面
 */
public class MemberRewardsActivity extends BaseActivity {
    private int page=1;
    private int size=10;
    private TextView tvAmount;
    @Bind(R.id.rlv)
    RecyclerView rlv;
    RewardsAdapter rewardsAdapter;
    private List<RewardEntity.DatasBean> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("会员奖励");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);
        rewardsAdapter = new RewardsAdapter(R.layout.item_rewards, mList);

        rewardsAdapter.openLoadAnimation();
        rewardsAdapter.setUpFetchEnable(false);
        rewardsAdapter.setEnableLoadMore(true);
        rewardsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
             getList();

            }
        }, rlv);
        rlv.setAdapter(rewardsAdapter);
        View header = LayoutInflater.from(this).inflate(R.layout.header_member_reward, null, false);
        rewardsAdapter.addHeaderView(header);
       tvAmount=header.findViewById(R.id.tv_total);
        getAmount();
        getList();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rewardsAdapter.setEmptyView(R.layout.view_empty_browse);
        rewardsAdapter.setHeaderAndEmpty(true);
    }

    /**
     * 获取列表
     */
    private void getList() {
        RetrofitFactory.getInstence().API()
                .rewardsList(CacheInfo.getUserID(this),page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RewardEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<RewardEntity> t) throws Exception {

                        if (mList.size()>= t.getData().getTotal()) {
                            //数据全部加载完毕
                            rewardsAdapter.loadMoreEnd();
                        } else {
                            rewardsAdapter.addData(t.getData().getDatas());
                            //   mCurrentCounter = mQuickAdapter.getData().size();
                            rewardsAdapter.loadMoreComplete();
                            page++;
                        }





                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    //** 获取总额
    private void getAmount() {
        RetrofitFactory.getInstence().API()
                .rewardAmount(CacheInfo.getUserID(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<String> t) throws Exception {
                            tvAmount.setText(t.getData());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_member_rewards;


    }
    class RewardsAdapter extends BaseQuickAdapter<RewardEntity.DatasBean, BaseViewHolder> {
        public RewardsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RewardEntity.DatasBean item) {
               helper.setText(R.id.tv_title, item.getDescribe());
               helper.setText(R.id.tv_value, item.getMoney()+"");
               helper.setText(R.id.tv_time, item.getCreateTime());
        }
    }
}
