package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BrandEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.view.custom.ShopGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BrandSpecialActivity extends BaseActivity {

    @Bind(R.id.rlv)
    RecyclerView rlv;

    private HomeAdapter homeAdapter;
    private int page=1;
    private List<BrandEntity.DatasBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("品牌特区");
        LinearLayoutManager llm = new LinearLayoutManager(this);
          llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);
        homeAdapter = new HomeAdapter(R.layout.item_brand_special, mList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setUpFetchEnable(false);
        rlv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
             getData();
            }
        }, rlv);
        rlv.setAdapter(homeAdapter);
        getData();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_brand_special;
    }
    class HomeAdapter extends BaseQuickAdapter<BrandEntity.DatasBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BrandEntity.DatasBean item) {

            ShopGridView shopGridView=((ShopGridView) helper.getView(R.id.shop_view));
           shopGridView.setData(item.getCommodityList(),item.getName(),item.getId());
        }
    }


    public void getData(){
        RetrofitFactory.getInstence().API()
                .brandSellerList(page, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BrandEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<BrandEntity> t) throws Exception {

                        if (mList.size() >= t.getData().getTotal()) {
                            //数据全部加载完毕
                            homeAdapter.loadMoreEnd();
                        } else {
                            homeAdapter.addData(t.getData().getDatas());
                            //   mCurrentCounter = mQuickAdapter.getData().size();
                            homeAdapter.loadMoreComplete();
                            page++;

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

}
