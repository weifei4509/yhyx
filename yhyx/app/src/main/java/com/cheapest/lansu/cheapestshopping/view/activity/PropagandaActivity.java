package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BalanceRecordEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.PropagandaEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.BitMaker;
import com.cheapest.lansu.cheapestshopping.utils.BitmapMaker;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ImageShow.ImageAdapter;
import com.cheapest.lansu.cheapestshopping.utils.ImageShow.MyGridView;
import com.cheapest.lansu.cheapestshopping.utils.NormalUtil;
import com.cheapest.lansu.cheapestshopping.utils.StringUtil;
import com.cheapest.lansu.cheapestshopping.utils.SystemConfig;
import com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration;
import com.cheapest.lansu.cheapestshopping.view.custom.MultiImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PropagandaActivity extends BaseActivity {
    private int page=1;
    private int size=10;
    @Bind(R.id.rlv)
    RecyclerView rlv;
   RewardsAdapter rewardsAdapter;
    private List<PropagandaEntity.DatasBean> mList = new ArrayList<>();
    private int imageSize=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("宣传物料");
        SystemConfig.init(getApplicationContext());
        initImageLoader(getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(llm);
        rewardsAdapter = new RewardsAdapter(R.layout.item_propaganda, mList);
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
        rewardsAdapter.setHeaderAndEmpty(true);  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rlv.addItemDecoration(dividerItemDecoration);
        rlv.setItemAnimator(new DefaultItemAnimator());
        imageSize = (SystemConfig.getWidth() - NormalUtil.dip2px(this, 90)
                - 2*NormalUtil.dip2px(mContext, 3))/3;
        getList();




    }

    @Override
    public int bindLayout() {
        return R.layout.activity_propaganda;
    }
    private void getList() {
        RetrofitFactory.getInstence().API()
                .adteams(CacheInfo.getUserID(this),page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PropagandaEntity>(this) {
                    @Override
                    protected void onSuccees(final BaseEntity<PropagandaEntity> t) throws Exception {

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





    class RewardsAdapter extends BaseQuickAdapter<PropagandaEntity.DatasBean, BaseViewHolder> {
        public RewardsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PropagandaEntity.DatasBean item) {

                   helper.setText(R.id.tv_time,item.getCreateTime());
                   helper.setText(R.id.tv_content,item.getTitle());
            ImageAdapter adapter = new ImageAdapter(PropagandaActivity.this,item.getImages(),imageSize);
            MyGridView myGridView=       helper.getView(R.id.gridview);
            if (item.getImages().size() == 1) {

               myGridView.setNumColumns(1);
                myGridView.getLayoutParams().width = StringUtil
                        .getThumbSize(item.getImages().get(0) + "").x;


            } else {
                myGridView.setNumColumns(3);
                myGridView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }


           myGridView.setAdapter(adapter);
              helper.getView(R.id.tv_copy_content).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                      cm.setText(item.getTitle()+ "");
                      Toast.makeText(PropagandaActivity.this, "复制成功", Toast.LENGTH_LONG).show();
                  }
              });  helper.getView(R.id.tv_copy_image).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Toast.makeText(PropagandaActivity.this,"正在保存，稍后可在相册内查看",Toast.LENGTH_LONG).show();

                      if(item.getImages()==null||item.getImages().size()==0)
                          return;

                      new BitMaker(mContext, item.getImages().get(0), new BitmapMaker.loadImage() {
                          @Override
                          public void loadFinish(Bitmap bitmap) {

                              saveBmp2Gallery(bitmap, System.currentTimeMillis()+"",PropagandaActivity.this);

                          }
                      });

                  for(int i=1;i<item.getImages().size();i++){

                      new Task().execute(item.getImages().get(i));


                  }


                  }
              });
        }
    }

    /**
     * 异步线程下载图片
     *
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            Bitmap  bitmap=GetImageInputStream((String)params[0]);
            saveBmp2Gallery(bitmap, System.currentTimeMillis()+"",PropagandaActivity.this);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message=new Message();
            message.what=0x123;


        }

    }

   /*
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static void saveBmp2Gallery(Bitmap bmp, String picName,Context mContext) {

        String fileName = null;
        //系统相册目录
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"Camera"+File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName+ ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
}

         MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);


    }
    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.color.bg_gray) //
                .showImageOnFail(R.color.bg_gray) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new FIFOLimitedMemoryCache(2 * 1024 * 1024));
        config.defaultDisplayImageOptions(defaultOptions);//

		/*
		 * config.memoryCacheExtraOptions(720, 1280);
		 * config.diskCacheExtraOptions(720, 1280, null);
		 */
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());

        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        // config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
    /**
     * 异步线程下载图片
     *
     */

}
