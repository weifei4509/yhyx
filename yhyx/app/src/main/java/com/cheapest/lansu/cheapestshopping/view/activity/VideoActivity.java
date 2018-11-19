/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.ijkplayer.widget.media.AndroidMediaController;
import com.cheapest.lansu.cheapestshopping.ijkplayer.widget.media.IRenderView;
import com.cheapest.lansu.cheapestshopping.ijkplayer.widget.media.IjkVideoView;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity extends AppCompatActivity {
	private static final String TAG = "VideoActivity";

	private String mVideoPath;
	private Uri mVideoUri;
	private String mTitle;

	private AndroidMediaController mMediaController;
	private IjkVideoView mVideoView;
	private TextView mToastTextView;

	private boolean mBackPressed;

	public static Intent newIntent(Context context, String videoPath, String videoTitle) {
		Intent intent = new Intent(context, VideoActivity.class);
		intent.putExtra("videoPath", videoPath);
		intent.putExtra("videoTitle", videoTitle);
		return intent;
	}

	public static void goPage(Context context, String videoPath, String videoTitle) {
		context.startActivity(newIntent(context, videoPath, videoTitle));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		// handle arguments
		mTitle = getIntent().getStringExtra("videoTitle");
		mVideoPath = getIntent().getStringExtra("videoPath");
//        mVideoPath = "http://1251922718.vod2.myqcloud.com/9f966b65vodgzp1251922718/21dfaf369031868223225520115/AnA4aPd9bSEA.mp3";
//        mVideoPath = "http://qimengvideo.haomubook.com/1-1%20Off%20To%20Story-Land.flv";
//        mVideoPath = "http://qimengvideo.haomubook.com/单词表.mp4";
//        try {
//        } catch (UnsupportedEncodingException e) {
//            mVideoPath = "http://englishimg.haomubook.com/upload/videoames/11.mp4";
//            e.printStackTrace();
//        }
//        mVideoPath = "http://qimengvideo.haomubook.com/7-2The Dolls’ Thanksgiving Dinner.flv";


		// init UI
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		mMediaController = new AndroidMediaController(this, false);
		mMediaController.setSupportActionBar(actionBar);

		actionBar.setTitle(mTitle);
		actionBar.setDisplayHomeAsUpEnabled(true);
		mToastTextView = (TextView) findViewById(R.id.toast_text_view);

		// init player
		IjkMediaPlayer.loadLibrariesOnce(null);
		IjkMediaPlayer.native_profileBegin("libijkplayer.so");

		mVideoView = (IjkVideoView) findViewById(R.id.video_view);
		mVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
		mVideoView.setMediaController(mMediaController);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// prefer mVideoPath
		if (mVideoPath != null) {
			mVideoView.setVideoPath(mVideoPath);
		} else if (mVideoUri != null)
			mVideoView.setVideoURI(mVideoUri);
		else {
			Log.e(TAG, "Null Data Source\n");
			finish();
			return;
		}
		mVideoView.start();
		mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
				ToastUtils.show(VideoActivity.this, "视频文件异常，已退出播放");
				onBackPressed();
				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		mBackPressed = true;
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		if (mBackPressed) {
			mVideoView.stopPlayback();
			mVideoView.release(true);
		}
		IjkMediaPlayer.native_profileEnd();
		super.onStop();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
}
