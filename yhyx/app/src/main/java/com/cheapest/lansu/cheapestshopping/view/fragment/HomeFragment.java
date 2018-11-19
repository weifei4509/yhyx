package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BannerEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TextUtil;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.HomeMenuTwoActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.LoveLifeActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.NewsListActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ShareNoticeActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.WebActivity;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.cheapest.lansu.cheapestshopping.view.custom.GradientRecycleView;
import com.cheapest.lansu.cheapestshopping.view.custom.IScrollViewListener;
import com.cheapest.lansu.cheapestshopping.view.custom.ObservableScrollView;
import com.cheapest.lansu.cheapestshopping.view.custom.TipView;
import com.cheapest.lansu.cheapestshopping.view.fragment.strategy.StrategyActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页界面
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.tv_kuaibao_title)
    TextView tv_kuaibao_title;
    @Bind(R.id.tipView)
    TipView tipView;

    @Bind(R.id.tv_bimai)
    TextView tvBimai;
    private int dy;  //上次滑动的距离
    @Bind(R.id.iv_header_home_pinpaizhuanqu)
    ImageView ivHeaderHomePinpaizhuanqu;
    @Bind(R.id.iv_header_home_xinzhuang)
    ImageView ivHeaderHomeXinzhuang;
    @Bind(R.id.iv_header_home_chaodijia)
    ImageView ivHeaderHomeChaodijia;
    @Bind(R.id.iv_header_home_renqi)
    ImageView ivHeaderHomeRenqi;

    @Bind(R.id.iv_channel_img_01)
    ImageView iv_channel_img_01;

    @Bind(R.id.iv_channel_img_02)
    ImageView iv_channel_img_02;

    @Bind(R.id.iv_channel_img_03)
    ImageView iv_channel_img_03;

    @Bind(R.id.iv_channel_img_04)
    ImageView iv_channel_img_04;


    private HomeAdapter homeAdapter;
    private List<ProductEntity.DatasBean> mList = new ArrayList<>();
    float title_height;
    float head_height;
    @Bind(R.id.fragment_home_banner)
    Banner fragmentHomeBanner;

    GradientRecycleView rlv;
    //	private ViewPager viewPager;
//	private LinearLayout points;
    private int page = 1;
    List<String> list1 = new ArrayList<String>();
    private float alpha = 0;
    private int totalPage;
    private ImageView[] ivPoints;

    ObservableScrollView scrollView;
    ImageView iv_zhiding;

    TwinklingRefreshLayout refreshlayout;

    @Bind(R.id.iv_aishenghuo_bg)
    ImageView iv_aishenghuo_bg;

    @Bind(R.id.tv_aishenghuo_title)
    TextView tvShopViewTitle;


    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        iv_zhiding = view.findViewById(R.id.iv_zhiding);
        tv_kuaibao_title = view.findViewById(R.id.tv_kuaibao_title);
        refreshlayout = view.findViewById(R.id.refreshlayout);
        scrollView = view.findViewById(R.id.scrollView);
        rlv = view.findViewById(R.id.rlv);
        title_height = getActivity().getResources().getDimension(R.dimen.title_height);
        head_height = getActivity().getResources().getDimension(R.dimen.head_height);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rlv.setLayoutManager(gridLayoutManager);
        homeAdapter = new HomeAdapter(R.layout.item_choice_pro, mList);
//		View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_home_pro, null, false);
//
//		homeAdapter.addHeaderView(header);
        homeAdapter.openLoadAnimation();
        homeAdapter.setUpFetchEnable(true);
//		homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//			@Override
//			public void onLoadMoreRequested() {
//				getNeedBuyedList();
//			}
//		}, rlv);

//		viewPager = header.findViewById(R.id.view_paper);
//		points = header.findViewById(R.id.points);

        scrollView.setScrollViewListener(new IScrollViewListener() {

            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                Log.e("TAG", "y->" + y);
                Log.e("TAG", "oldy->" + oldy);

                if (y > head_height) {
                    EventBus.getDefault().post(String.valueOf(1));
                } else {
                    float alpha = Math.min(1f, (float) y / head_height);//(float) (firstHeight - height));
                    EventBus.getDefault().post(String.valueOf(alpha));
                }

                if (y > head_height * 3) {
                    iv_zhiding.setVisibility(View.VISIBLE);
                } else {
                    iv_zhiding.setVisibility(View.GONE);
                }
            }
        });

        iv_zhiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				scrollView.scrollTo(0, 0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });


        refreshlayout.setEnableLoadmore(true);
        refreshlayout.setEnableRefresh(true);
        refreshlayout.setAutoLoadMore(true);
        refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {
                super.onLoadMore(refresh);
                getNeedBuyedList();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                list1.clear();
                initeData();
                refreshLayout.setEnableLoadmore(true);
            }
        });

//		rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//			}
//
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//				int position = layoutManager.findFirstVisibleItemPosition();
//				if (position > 0) {
//					EventBus.getDefault().post(String.valueOf(1));
//				} else {
//					View firstView = layoutManager.findViewByPosition(position);
//					int top = firstView.getTop();
//					int height = (int) title_height;
//					int firstHeight = firstView.getHeight();
//					//setAlpha(Math.min(1f, (float) -top / (float) (firstHeight - height)));
//					float alpha = Math.min(1f, (float) -top / head_height);//(float) (firstHeight - height));
//					EventBus.getDefault().post(String.valueOf(alpha));
//
//				}
//			}
//
//		});


        rlv.setAdapter(homeAdapter);
//     com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
//             com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
//        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
//        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductEntity.DatasBean datasBean = (ProductEntity.DatasBean) adapter.getItem(position);
                if (datasBean != null) {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_ID, datasBean.getId());
                    intent.putExtra(Constants.INTENT_KEY_URL, datasBean.getIconUrl());
                    startActivity(intent);
                }
            }
        });

        ButterKnife.bind(this, view.findViewById(R.id.layout_header));
//		ButterKnife.bind(this, header);
        iv_aishenghuo_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoveLifeActivity.class);
                startActivity(intent);
            }
        });
        return view;
        //设置图片加载器
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initeData();          //初始化首页数据

        fragmentHomeBanner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        fragmentHomeBanner.isAutoPlay(true);
        //设置轮播时间
        fragmentHomeBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        fragmentHomeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //  StatusBarUtil.setTranslucentForImageView(getActivity(),0,fragmentHomeBanner);
        TextUtil.setTextType(getActivity(), tvBimai);
        TextUtil.setTextType(getActivity(), tv_kuaibao_title);

        tvShopViewTitle.setText("爱生活，更要懂得生活");
        TextUtil.setTextType(getActivity(), tvShopViewTitle);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.dip2px(getActivity(), 105));
        layoutParams.leftMargin = AppUtils.dip2px(getActivity(), 16);
        layoutParams.rightMargin = AppUtils.dip2px(getActivity(), 16);
        iv_aishenghuo_bg.setLayoutParams(layoutParams);
        iv_aishenghuo_bg.setImageResource(R.mipmap.iv_love_life_bg);
    }

    @SuppressLint("NewApi")
    private void initeData() {
        getBanner();
        getNewsList();
        getCategories();
        getNeedBuyedList();

    }

    @OnClick({R.id.iv_gongnue, R.id.iv_yaoqing})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_gongnue:
                //省惠优选攻略
                startActivity(new Intent(getActivity(), StrategyActivity.class));
                break;

            case R.id.iv_yaoqing:
                //分享赚钱
                startActivity(new Intent(getActivity(), ShareNoticeActivity.class));
                break;
        }
    }

    /**
     * 获取广告
     */
    private void getBanner() {
        RetrofitFactory.getInstence().API()
                .getBanner(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BannerEntity>>(getActivity()) {
                    @Override
                    protected void onSuccees(final BaseEntity<List<BannerEntity>> t) throws Exception {
                        for (BannerEntity bannerEntity : t.getData()) {
                            list1.add(bannerEntity.getImageUrl());
                        }

                        fragmentHomeBanner.setImages(list1);
                        fragmentHomeBanner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {

                                if (t.getData().get(position).getUrlType() == 1) {
                                    Intent intent = new Intent(getActivity(), WebActivity.class);
                                    intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().get(position).getTitle());
                                    intent.putExtra(Constants.INTENT_KEY_URL, t.getData().get(position).getTarget());
                                    startActivity(intent);
                                } else {
                                    // todo 去特色分类

                                }


                            }
                        });
                        fragmentHomeBanner.start();


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

    /**
     * 获取分类
     */
    public void getCategories() {
//		RetrofitFactory.getInstence().API()
//				.commodityCategories(1, 100)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new BaseObserver<CategoriesEntity>(getActivity()) {
//					@Override
//					protected void onSuccees(final BaseEntity<CategoriesEntity> t) throws Exception {
//						totalPage = (int) Math.ceil(t.getData().getDatas().size() * 1.0 / 8);
//						ArrayList<View> viewPagerList = new ArrayList<View>();
//						for (int i = 0; i < totalPage; i++) {
//							//每个页面都是inflate出一个新实例
//							final GridView gridView = (GridView) View.inflate(getActivity(), R.layout.view_view_paper, null);
//							final HomeProAdapter homeProAdapter = new HomeProAdapter(t.getData().getDatas(), getActivity(), i);
//							gridView.setAdapter(homeProAdapter);
//							gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//								@Override
//								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//									CategoriesEntity.DatasBean obj = (CategoriesEntity.DatasBean) gridView.getItemAtPosition(position);
//									if (obj != null) {
//										Intent intent = new Intent(getActivity(), ProductSumActivity.class);
//										intent.putExtra(Constants.INTENT_KEY_TITLE, obj.getName());
//										intent.putExtra(Constants.INTENT_KEY_ID, obj.getId() + "");
//										startActivity(intent);
//									}
//
//								}
//							});
//
//							//每一个GridView作为一个View对象添加到ViewPager集合中
//							viewPagerList.add(gridView);
//						}
//						//设置ViewPager适配器
//						viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));
//
//						//添加小圆点
//						ivPoints = new ImageView[totalPage];
//						for (int i = 0; i < totalPage; i++) {
//							//循坏加入点点图片组
//							ivPoints[i] = new ImageView(getActivity());
//							if (i == 0) {
//								ivPoints[i].setImageResource(R.mipmap.page_focuese);
//							} else {
//								ivPoints[i].setImageResource(R.mipmap.page_unfocused);
//							}
//							ivPoints[i].setPadding(8, 8, 8, 8);
//							points.addView(ivPoints[i]);
//						}
//						//设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
//						viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//							@Override
//							public void onPageSelected(int position) {
//								// TODO Auto-generated method stub
//								//currentPage = position;
//								for (int i = 0; i < totalPage; i++) {
//									if (i == position) {
//										ivPoints[i].setImageResource(R.mipmap.page_focuese);
//									} else {
//										ivPoints[i].setImageResource(R.mipmap.page_unfocused);
//									}
//								}
//							}
//						});
//
//
//					}
//
//					@Override
//					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//					}
//				});


        RetrofitFactory.getInstence().API()
                .commoditySpecialCategories(1, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CategoriesEntity>(getActivity()) {
                    @Override
                    protected void onSuccees(final BaseEntity<CategoriesEntity> t) throws Exception {
                        List<ImageView> list2 = new ArrayList<>();
                        list2.add(iv_channel_img_01);
                        list2.add(iv_channel_img_02);
                        list2.add(iv_channel_img_03);
                        list2.add(iv_channel_img_04);
                        list2.add(ivHeaderHomePinpaizhuanqu);
                        list2.add(ivHeaderHomeRenqi);
                        list2.add(ivHeaderHomeXinzhuang);
                        list2.add(ivHeaderHomeChaodijia);
                        for (int i = 0; i < t.getData().getDatas().size(); i++) {
                            final int finalI = i;
                            ImageLoader.getInstance().displayImage(t.getData().getDatas().get(i).getIconUrl(), list2.get(i));
                            list2.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), HomeMenuTwoActivity.class);
                                    intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().getDatas().get(finalI).getName());
                                    intent.putExtra(Constants.INTENT_KEY_ID, t.getData().getDatas().get(finalI).getId() + "");
                                    if (finalI == 7) {
                                        intent.putExtra(Constants.INTENT_KEY_TYPE, 2);
                                    } else if (finalI == 5) {
//										ToastUtils.show(mContext, "视频购物");
                                        intent.putExtra(Constants.INTENT_KEY_TYPE, 3);
                                    } else {
                                        intent.putExtra(Constants.INTENT_KEY_TYPE, 1);
                                    }
                                    startActivity(intent);
                                }
                            });

//							if (i == 7) {
//								list2.get(i).setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View view) {
//										Intent intent = new Intent(getActivity(), HomeMenuTwoActivity.class);
//										intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().getDatas().get(finalI).getName());
//										intent.putExtra(Constants.INTENT_KEY_ID, t.getData().getDatas().get(finalI).getId() + "");
//										intent.putExtra(Constants.INTENT_KEY_TYPE, 2);
//										startActivity(intent);
//									}
//								});
//							}else {
//								list2.get(i).setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View view) {
//										Intent intent = new Intent(getActivity(), HomeMenuTwoActivity.class);
//										intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().getDatas().get(finalI).getName());
//										intent.putExtra(Constants.INTENT_KEY_ID, t.getData().getDatas().get(finalI).getId() + "");
//										intent.putExtra(Constants.INTENT_KEY_TYPE, 1);
//										startActivity(intent);
//									}
//								});
//							}
//							if (i != 0) {
//								final int finalI = i;
//								list2.get(i).setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//
//										Intent intent = new Intent(getActivity(), ProductSumActivity.class);
//										intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().getDatas().get(finalI).getName());
//										intent.putExtra(Constants.INTENT_KEY_ID, t.getData().getDatas().get(finalI).getId() + "");
//										intent.putExtra(Constants.INTENT_KEY_TYPE, 1);
//										startActivity(intent);
//
//									}
//								});
//							} else {
//								list2.get(i).setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										startActivity(new Intent(getActivity(), BrandSpecialActivity.class));
//									}
//								});
//							}


                        }


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    @Override
    public void onClick(View v) {

    }

    class HomeAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
            //     helper.setText(R.id.item_home_pro_quan, item.getTitle());
            //  helper.setImageResource(R.id.icon, item.());
//            // 加载网络图片
            Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
            helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(getActivity(), item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
            helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
            helper.setText(R.id.item_home_pro_yuanjia, "￥" + item.getPrice());
            helper.setText(R.id.tv_commision, "预估佣金：¥" + item.getMineCommision());
            helper.setText(R.id.item_home_pro_quanhoujia, "￥" + item.getDiscountPrice());
            ((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

            helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());
        }
    }

    private void getNewsList() {
        RetrofitFactory.getInstence().API().newsList(1, 6).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NewsEntity>(getActivity()) {

                    @Override
                    protected void onSuccees(BaseEntity<NewsEntity> t) throws Exception {
                        Log.e("TAG", JSON.toJSONString(t.getData().getDatas()));
                        //設置快報信息
                        List<String> tipList = new ArrayList<>();
                        List<NewsEntity.DatasBean> newsList = t.getData().getDatas();
                        for (int i = 0; i < newsList.size(); i++) {
                            tipList.add(newsList.get(i).getTitle());
                        }
                        tipView.setTipList(tipList);
                        tipView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//								新闻
                                startActivity(new Intent(getActivity(), NewsListActivity.class));
//								startActivity(new Intent(getActivity(), PreferenceRankActivity.class));
                            }
                        });


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    public void getNeedBuyedList() {
        RetrofitFactory.getInstence().API().needBuyedList(1, page, 12).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ProductEntity>(getActivity()) {
                    @Override
                    protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
                        refreshlayout.finishRefreshing();
                        refreshlayout.finishLoadmore();

                        if (mList.size() >= t.getData().getTotal()) {
                            //数据全部加载完毕
                            homeAdapter.loadMoreComplete();
                            homeAdapter.loadMoreEnd();
                        } else {
                            if (page == 1) {
                                homeAdapter.setNewData(t.getData().getDatas());
                            } else {
                                homeAdapter.addData(t.getData().getDatas());
                            }
                            //   mCurrentCounter = mQuickAdapter.getData().size();
                            page++;
                            homeAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        refreshlayout.finishRefreshing();
                        refreshlayout.finishLoadmore();
                    }
                });


//		RetrofitFactory.getInstence().API()
//				.needBuyedList(page, 6)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new BaseObserver<ProductEntity>(getActivity()) {
//					@Override
//					protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
//						if (mList.size() >= t.getData().getTotal()) {
//							//数据全部加载完毕
//							homeAdapter.loadMoreEnd();
//						} else {
//							homeAdapter.addData(t.getData().getDatas());
//							//   mCurrentCounter = mQuickAdapter.getData().size();
//							homeAdapter.loadMoreComplete();
//							page++;
//						}
//					}
//
//					@Override
//					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//					}
//				});

    }


}
