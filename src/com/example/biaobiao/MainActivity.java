package com.example.biaobiao;

import org.json.JSONObject;

import com.renn.rennsdk.RennClient;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	GameView gameview;
	LinearLayout anims;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		 // this.setContentView(R.layout.activity_main);
		 		gameview=new GameView(this);
     	
     		setContentView(gameview);		
     	
	//	Init();
		}
		
	

	public void Init() {
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Button b = new Button(this);
		b.setText("hello");
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		b.setLayoutParams(lp);
		
		this.setContentView(gameview);
		this.addContentView(b, lp);
  
		
	}
	public void onStart(){
		super.onStart();	
	//	this.setContentView(gameview);
}
	public void onPause(){
		super.onPause();
	}
	public void Resume(){
		super.onResume();
		
	}
	public void onStop(){
		super.onStop();
	}
	public void onRestart(){
		super.onRestart();
	}
    public void Destroy(){
    	super.onDestroy();
    }
	
    

	
}
