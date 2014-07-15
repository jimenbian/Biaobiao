package com.example.biaobiao;

import org.json.JSONObject;

import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.RennClient.LoginListener;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.PutFeedParam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {

	ImageResource mr = ImageResource.getImageResource();
	MusicPlayer mp;

	Handler handler;
	SharedPreferences sp;
	ImageView iv[];
	ImageView logo;
    private TextView textView;
    private ProgressDialog mProgressDialog;
	int image[];
	int alpha;
	int screen_width;
	int screen_height;
	int bgm; // bgm，是否被打开声音，默认属性为0，表示打开，1表示关闭，8表示第一次加载此变量
	private static final String APP_ID = "244377";

	private static final String API_KEY = "aae2088442924540bc171691eddd8118";

	private static final String SECRET_KEY = "4e921aa441c34c72b40106309b9912da";
	
	RennClient rennClient;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.main_menu);
			
		mp = MusicPlayer.getMusicPlayer(getApplicationContext());
		
		getWindow().setBackgroundDrawable(
				mr.getDrawable(getResources(),
						R.drawable.backgroundpic));

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screen_width = dm.widthPixels;
		screen_height = dm.heightPixels;	
		 rennClient = RennClient.getInstance(this);//获取实例
		 rennClient.init(APP_ID, API_KEY, SECRET_KEY);//设置应用程序信息
			rennClient
			.setScope("publish_feed");
		 rennClient.setTokenType("bearer");
//			rennClient.login(this);
		handler = new Handler();
		iv = new ImageView[6];
//		logo = (ImageView) findViewById(R.id.menu_logo);
//		logo.setImageDrawable(mr.getDrawable(getResources(),
//				R.drawable.game_logo));
//		logo.setMaxWidth((screen_width - 120) * 2 / 3);
//		logo.setMaxHeight((screen_height - 40) * 5 / 9);
		iv[0] = (ImageView) findViewById(R.id.menu_begin);
		iv[1] = (ImageView) findViewById(R.id.menu_music);
		iv[2] = (ImageView) findViewById(R.id.menu_grade);
		iv[3] = (ImageView) findViewById(R.id.menu_help);
		iv[4] = (ImageView) findViewById(R.id.menu_about);
		iv[5] = (ImageView) findViewById(R.id.menu_exit);

		image = new int[] { R.drawable.button_begin1,
				R.drawable.button_close_bgm1, R.drawable.button_grade1,
				R.drawable.button_help1, R.drawable.button_about1,
				R.drawable.button_exit1, R.drawable.button_open_bgm1};

		alpha = 0;
		handler.postDelayed(showMenu, 1000);

		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		bgm = sp.getInt("bgm", 8);

		if (bgm == 8) {
			bgm = 0;
			mp.startBGM();
			Editor editor = sp.edit();
			editor.putInt("bgm", 0);
			editor.commit();
		} else if (bgm == 0) {
			mp.startBGM();
		}
	}

	Runnable showMenu = new Runnable() {
		@Override
		public void run() {
			if (alpha == 0) {
				alpha = 5;
				for (int i = 0; i < 6; i++) {
					iv[i].setMaxWidth((screen_width - 120) / 3 - 20);
					iv[i].setMaxHeight((screen_height - 40) * 2 / 9);
					iv[i].setImageDrawable(mr.getDrawable(
							getResources(), image[i]));
					iv[i].setAlpha(alpha);
					iv[i].setOnClickListener(listener);
				}
				if (bgm == 1) {
					iv[1].setImageDrawable(mr
							.getDrawable(getResources(),
									R.drawable.button_open_bgm1));
				}
			//	logo.setAlpha(alpha);
				handler.postDelayed(showMenu, 200);
			} else if (alpha < 250) {
				alpha += 25;
				for (int i = 0; i < 6; i++) {
					iv[i].setAlpha(alpha);
				}
				//logo.setAlpha(alpha);
				handler.postDelayed(showMenu, 200);
			} else {
				handler.removeCallbacks(showMenu);
			}
		}
	};

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int k = 0;
			for (; k < 6; k++) {
				if (v.equals(iv[k]))
					break;
			}
			if (k == 0) {
				Intent intent = new Intent();	
				intent.setClass(MenuActivity.this, MainActivity.class);				
				startActivity(intent);}
			    if (k == 1) {
				if (bgm==0) {
					bgm = 1;
					mp.stopBGM();
					mp.setUseAble(false);
					((ImageView) v).setImageDrawable(mr
							.getDrawable(getResources(),
									R.drawable.button_open_bgm1));
					Editor editor = sp.edit();
					editor.putInt("bgm", bgm);
					editor.commit();
				} else {
					bgm = 0;
					mp.startBGM();
					mp.setUseAble(true);
					((ImageView) v).setImageDrawable(mr.getDrawable(
							getResources(),
							R.drawable.button_close_bgm1));
					Editor editor = sp.edit();
					editor.putInt("bgm", bgm);
					editor.commit();
				}
			} else 
				if (k == 2) {
					
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("application","grade");
				intent.putExtras(bundle);
				intent.setClass(MenuActivity.this, OtherActivity.class);
				MenuActivity.this.startActivity(intent);
				//rennClient.login(this);
				}
			 if (k == 3) {
				 rennClient.setLoginListener(new LoginListener() {
						@Override
						public void onLoginSuccess() {
							// TODO Auto-generated method stub
							 Intent intent4 = new Intent(MenuActivity.this,
										FeedServiceActivity.class);
								MenuActivity.this.startActivity(intent4);

						}

						@Override
						public void onLoginCanceled() {
							 Toast.makeText(MenuActivity.this, "发送失败，请重试", Toast.LENGTH_SHORT).show();			
						}
					});
					rennClient.login(MenuActivity.this);
								   	                
	 
				 //				Intent intent = new Intent();
//				Bundle bundle = new Bundle();
//				bundle.putString("application","help");
//				intent.putExtras(bundle);
//				intent.setClass(MenuActivity.this, OtherActivity.class);
//				MenuActivity.this.startActivity(intent);
			} else if (k == 4) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("application","about");
				intent.putExtras(bundle);
				intent.setClass(MenuActivity.this, OtherActivity.class);
				MenuActivity.this.startActivity(intent);
			} else if (k == 5) {
				finish();
			}
		
		}};
	
	@Override
	protected void onDestroy() {
		mp.release();
		super.onDestroy();
	}
	
}