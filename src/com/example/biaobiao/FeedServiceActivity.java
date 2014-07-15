package com.example.biaobiao;

import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.param.PutFeedParam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class FeedServiceActivity extends Activity implements OnClickListener{
    
    private Button putFeedBtn;
    private TextView textView;
    private RennClient rennClient;
    private ProgressDialog mProgressDialog;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_feedservice);
        initView();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FeedServiceActivity.this);
		score= sp.getInt("grade", 1);
    }

    private void initView(){
        rennClient = RennClient.getInstance(this);
        putFeedBtn = (Button) findViewById(R.id.putFeed);
        putFeedBtn.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.putFeed:
                PutFeedParam param = new PutFeedParam();
                param.setTitle("镖镖必达");
                param.setMessage("镖镖必达游戏战报");
                param.setDescription("我玩了镖镖必达，并狂砍"+score+"分，快来挑战我！");
                param.setActionName("下载应用");
                param.setActionTargetUrl("http://www.renren.com");
                param.setSubtitle("蓟门边IT创意工作室出品");
                param.setImageUrl("http://img.my.csdn.net/uploads/201311/26/1385429731_6806.png");
                param.setTargetUrl("http://write.blog.csdn.net/postlist");
                
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(FeedServiceActivity.this);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.setTitle("text");
                    mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                    mProgressDialog.setMessage("text");
                    mProgressDialog.show();
                }
                try {
                    rennClient.getRennService().sendAsynRequest(param, new CallBack() {    
                        
                        @Override
                        public void onSuccess(RennResponse response) {
                            textView.setText(response.toString());
                            Toast.makeText(FeedServiceActivity.this, "发送成功", Toast.LENGTH_SHORT).show();  
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                                mProgressDialog = null;
                            }                           
                        }
                        
                        @Override
                        public void onFailed(String errorCode, String errorMessage) {
                            textView.setText(errorCode+":"+errorMessage);
                            Toast.makeText(FeedServiceActivity.this, "失败请再试", Toast.LENGTH_SHORT).show();
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                                mProgressDialog = null;
                            }                            
                        }
                    });
                } catch (RennException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;

            default:
                break;
        }
        
    }

}
