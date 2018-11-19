package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BalanceRecordEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RewardEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ScoreEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ScoreListEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 消费积分
 */
public class ConsumptionPointsActivity extends BaseActivity {
    RewardsAdapter rewardsAdapter;
   private ScoreEntity scoreEntity;
    @Bind(R.id.rlv)
    RecyclerView rlv;
    private int page=1;
    private int size=10;
private TextView tvTotal;
private TextView tvInWay;
private TextView tvGetPints;
private TextView tvZhuanhuan;
private TextView tvTransfer;
    private List<ScoreListEntity.DatasBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("消费积分");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);

        rewardsAdapter = new RewardsAdapter(R.layout.item_rewards, mList);
        View header = LayoutInflater.from(this).inflate(R.layout.header_view_points, null, false);
        tvTotal=header.findViewById(R.id.tv_total);
        tvGetPints=header.findViewById(R.id.tv_shoudaojifen);
        tvInWay=header.findViewById(R.id.tv_zailushang);
        tvZhuanhuan=header.findViewById(R.id.tv_zhuanhuan);
         tvTransfer=header.findViewById(R.id.tv_transfer);
        rewardsAdapter.addHeaderView(header);
        rewardsAdapter.openLoadAnimation();
        rewardsAdapter.setUpFetchEnable(false);
        rewardsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getList();
            }
        }, rlv);
        rlv.setAdapter(rewardsAdapter);
        rewardsAdapter.setEmptyView(R.layout.view_empty_browse);
        rewardsAdapter.setHeaderAndEmpty(true);  com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(this,
                com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());
       getPoints();
        tvTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      if (scoreEntity.getCurrentScore()< AppConfig.MIN_SCORE){
                          ToastUtils.showShort(ConsumptionPointsActivity.this,"积分满100以上可兑换");
                          return;
                      }
                      showProgressDialog();
                RetrofitFactory.getInstence().API()
                        .convertScore(CacheInfo.getUserID(ConsumptionPointsActivity.this))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<ScoreEntity>(ConsumptionPointsActivity.this) {
                            @Override

                            protected void onSuccees(final BaseEntity<ScoreEntity> t) throws Exception {
                                discussProgressDialog();
                                scoreEntity=t.getData();
                                tvTotal.setText(String.format("%s", t.getData().getConvertedAmount()));
                                tvGetPints.setText(t.getData().getCurrentScore()+"");
                                tvInWay.setText(t.getData().getGoingScore()+"");
                                tvZhuanhuan.setText(t.getData().getCurrentScore()+"");





                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                           discussProgressDialog();
                            }
                        });


            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_consumption_points;


    }

    private void getList() {
        RetrofitFactory.getInstence().API()
                .scores(CacheInfo.getUserID(this),page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ScoreListEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<ScoreListEntity> t) throws Exception {

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

    public void getPoints() {

        RetrofitFactory.getInstence().API()
                .score(CacheInfo.getUserID(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ScoreEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<ScoreEntity> t) throws Exception {
                        scoreEntity=t.getData();
                       tvTotal.setText(String.format("%s", t.getData().getConvertedAmount()));
                       tvGetPints.setText(t.getData().getCurrentScore()+"");
                       tvInWay.setText(t.getData().getGoingScore()+"");
                       tvZhuanhuan.setText(t.getData().getCurrentScore()+"");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    class RewardsAdapter extends BaseQuickAdapter<ScoreListEntity.DatasBean, BaseViewHolder> {
        public RewardsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ScoreListEntity.DatasBean item) {
            helper.setText(R.id.tv_title, item.getDescribe());
            helper.setText(R.id.tv_value, item.getScore() + "");
            helper.setText(R.id.tv_time, item.getCreateTime());
        }
    }
}
