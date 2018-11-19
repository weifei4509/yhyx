package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RecomEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TeamEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;
import com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamDirectFragment extends Fragment {

   private int page=1;
   private int size=10;
   private int  type;
    @Bind(R.id.rlv)
    RecyclerView rlv;
    private IndirectAdapter rewardsAdapter;
    private List<TeamEntity.DatasBean> mList=new ArrayList<>();



    public TeamDirectFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_normal, container, false);
        type=getArguments().getInt(Constants.INTENT_KEY_TYPE);
        ButterKnife.bind(this, view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);
        rewardsAdapter = new IndirectAdapter(R.layout.item_team, mList);
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
        rewardsAdapter.setEmptyView(R.layout.view_empty);
        com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
               DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());

   getList();
        return  view;
    }

    private void getList() {
        RetrofitFactory.getInstence().API()
                .recommendTeams(CacheInfo.getUserID(getActivity()),type,1,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TeamEntity>(getActivity()) {
                    @Override
                    protected void onSuccees(final BaseEntity<TeamEntity> t) throws Exception {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    class IndirectAdapter extends BaseQuickAdapter<TeamEntity.DatasBean, BaseViewHolder> {
        public IndirectAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TeamEntity.DatasBean item) {

                helper.setText(R.id.tv_id, item.getNickname());


            if (type==1){
                helper.setText(R.id.tv_name,"VIP会员") ;
            }else {
                helper.setText(R.id.tv_name,"普通会员") ;
            }
            helper.setText(R.id.tv_time, item.getCreateTime());
            Glide.with(getActivity()).load(item.getHeadpicUrl()).error(R.mipmap.icon_tuxiang_2).into((CircleImageView) helper.getView(R.id.iv_team));
        }
    }
}
