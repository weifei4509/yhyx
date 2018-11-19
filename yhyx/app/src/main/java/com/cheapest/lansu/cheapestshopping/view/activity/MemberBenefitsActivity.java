package com.cheapest.lansu.cheapestshopping.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RewardEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfListEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfareEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 会员福利
 */
public class MemberBenefitsActivity extends BaseActivity {
   private int page=1;
   private int  size=10;

    RecyclerView rlv;
   private TextView tvAmount;
   private TextView tvZailushang;
   private TextView tvShoudao;
   private LinearLayout linZhuandao;
   RewardsAdapter rewardsAdapter;
    private List<WelfListEntity.DatasBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("会员福利");
        rlv = findViewById(R.id.rlv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);

        rewardsAdapter = new RewardsAdapter(R.layout.item_rewards, mList);
        View header = LayoutInflater.from(this).inflate(R.layout.header_benefits, null, false);
        tvAmount=header.findViewById(R.id.tv_amount);
        tvZailushang=header.findViewById(R.id.tv_zailushang);
        tvShoudao=header.findViewById(R.id.tv_shoudao);
        linZhuandao=header.findViewById(R.id.lin_zhuanru);
        rewardsAdapter.addHeaderView(header);
        rewardsAdapter.openLoadAnimation();
        ;
        rewardsAdapter.setUpFetchEnable(true);
        rewardsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                     getList();
            }
        }, rlv);
        rlv.setAdapter(rewardsAdapter);
        rewardsAdapter.setEmptyView(R.layout.view_empty_browse);
        rewardsAdapter.setHeaderAndEmpty(true);  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());
        linZhuandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //积分转入余额


            }
        });
        getBenift();
        getList();

    }

    private void getList() {
        RetrofitFactory.getInstence().API()
                .welfaresList(CacheInfo.getUserID(this),page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WelfListEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<WelfListEntity> t) throws Exception {

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

    private void getBenift() {
        RetrofitFactory.getInstence().API()
                .welfare(CacheInfo.getUserID(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WelfareEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<WelfareEntity> t) throws Exception {
                        tvAmount.setText(t.getData().getTotalBalance()+"");
                        tvZailushang.setText(t.getData().getGoingBalance()+"");
                        tvShoudao.setText(t.getData().getBalance()+"");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_memberbenefits;
    }

    class RewardsAdapter extends BaseQuickAdapter<WelfListEntity.DatasBean, BaseViewHolder> {
        public RewardsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WelfListEntity.DatasBean item) {
            helper.setText(R.id.tv_title, item.getDetail());
            helper.setText(R.id.tv_value, item.getMoney()+"");
            helper.setText(R.id.tv_time, item.getModifyTime());
        }
    }


}
