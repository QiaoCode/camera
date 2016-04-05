package com.example.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import TopCodes.Scanner;
import TopCodes.TopCode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	/*
点击run按钮，则scanner扫描过的数据，每隔一秒输出一个listcode。
 * */
	ImageView imgFavorite;
	public List<TopCode> TopCodesList=new ArrayList();
	private TopCode Instruction=null;
    private Timer rTimer=null;
    private static int rcount=0;
    private TimerTask rTimerTask=null;
	//*******timer
	private TextView runtext=null;
	private TextView timertext=null;
	private Button bt_run=null;
    private static String TAG="Timer";
    
    private Timer mTimer=null;
    private TimerTask mTimerTask=null;
    
    private Handler mHandler=null;
    private Handler rHandler=null;
    private static int count=0;
    private boolean isPause=false;
    private boolean isStop=true;
    
    private static int delay=1000;//1s
    private static int period=1000;//1s
    private static int delay2=1000;//1s
    private static int period2=1000;//1s
    protected static final int UPDATE_TEXTVIEW = 0;
	protected static final int UPDATE_RUNTEXT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_run=(Button)findViewById(R.id.bt_run);
        runtext=(TextView)findViewById(R.id.runtext);
        timertext=(TextView)findViewById(R.id.timertext);
        imgFavorite=(ImageView)findViewById(R.id.imageView1);
        imgFavorite.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		open();
        	}
        });
        startTimer();
        //设置runAll监听
        bt_run.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				rcount = 0; 
				startList();
				Log.e(TAG,"startbutton");
				/*
				// TODO Auto-generated method stub
				isStop=!isStop;
				
				if(!isStop){//如果list是运行阶段
					startList();
					Log.e(TAG,"startlist");
				}else{
					stopInstructions();//释放list
					Log.e(TAG,"stoplist");
				}
				
				if(isStop){
					bt_run.setText(R.string.start);
				}else{
					bt_run.setText(R.string.stop);
				}
			*/}
        });
        
        mHandler=new Handler(){
        	public void handleMessage(Message msg){
        		switch (msg.what){
        		case UPDATE_TEXTVIEW:
        		    updateTimerText();
        		    break;
        		default:
        			break;
        		}
        	}
        };
        rHandler=new Handler(){
        	public void handleMessage(Message msg){
        		Log.e(TAG,"handleMessage");
        		switch (msg.what){
        		case UPDATE_RUNTEXT:
        			updateRunText();
        			rcount++;
        		    break;
        		default:
        			break;
            	}
        	}
        };
    } 
	private void stopInstructions() {
		// TODO Auto-generated method stub
		TopCodesList=null;
	}
	private void stopList(){
		Log.e(TAG, "stopList");
		if (rTimer != null) {  
            rTimer.cancel();  
            rTimer = null;  
        }  
  
        if (rTimerTask != null) {  
            rTimerTask.cancel();  
            rTimerTask = null;  
        }     
		rcount = 0;
	}
	//************************************
    private TopCode getInstructions(){
    	if(rcount<TopCodesList.size()){
    		Instruction=TopCodesList.get(rcount);
    		Log.e(TAG,"getInstructions"+String.valueOf(Instruction));
       	    Log.e(TAG,"rcount"+rcount);
    	}else{ 
    		Instruction=TopCodesList.get(TopCodesList.size()-1);
    		stopList();
    	}
    	//runtext.setText(String.valueOf(Instruction)); 
    		//rHandler.sendEmptyMessageDelayed(UPDATE_RUNTEXT, 5 * 1000);
    	//return String.valueOf(Instruction);
    	return Instruction;
    }
    protected void updateRunText() {
	     runtext.setText(String.valueOf(getInstructions()));
    	 Log.e(TAG,"updateRunText-->"+getInstructions());
	     //sendRunMessage(UPDATE_RUNTEXT);      
	}
   public void sendRunMessage(int id){ //调用的是Handler中的sendMessage(Message msg) 
       Log.e(TAG,"sendRunMessage-->");
	   if (rHandler != null) {  
           Message message = Message.obtain(rHandler, id); 
           rHandler.sendMessage(message);   
       }   
   }  
   public void startList() {
	   Log.e(TAG,"startlist");
       if (rTimer == null) {  
           rTimer = new Timer();  
       }  
 
       if (rTimerTask == null) {  
           rTimerTask = new TimerTask() {  
               @Override  
               public void run() {  
                   //Log.i(TAG, "count: "+String.valueOf(count));  
            	   Log.e(TAG,"startsendrun()");
                   sendRunMessage(UPDATE_RUNTEXT);
               }  
           };  
       }  
 
       if(rTimer != null && rTimerTask != null )  
           rTimer.schedule(rTimerTask, delay2, period2);  
 
   
   } 
   //*******************************
    private void startTimer(){  
        if (mTimer == null) {  
            mTimer = new Timer();  
        }  
  
        if (mTimerTask == null) {  
            mTimerTask = new TimerTask() {  
                @Override  
                public void run() {  
                    Log.i(TAG, "count: "+String.valueOf(count));  
                    sendMessage(UPDATE_TEXTVIEW);  
                      
                    count ++;    
                }  
            };  
        }  
  
        if(mTimer != null && mTimerTask != null )  
            mTimer.schedule(mTimerTask, delay, period);  
  
    }  
      
    public void sendMessage(int id){  
        if (mHandler != null) {  
            Message message = Message.obtain(mHandler, id);     
            mHandler.sendMessage(message);   
        }  
    }  
    private void updateTimerText(){  
    	timertext.setText(String.valueOf(count));  
    } 
        public void open() {
		// TODO Auto-generated method stub
        	Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
	}
        @Override
	 protected void onActivityResult(int requestCode,int resultCode,Intent data){
        	super.onActivityResult(requestCode, resultCode, data);
		//命名要注意，get方法里面的引号不要忘了,activity不要再写错了！
        	if (data == null||"".equals(data)) {
				return;
			} else {
				//Bundle extras = data.getExtras();
				Bitmap bp=(Bitmap)data.getExtras().get("data");
				//request 0  result -1
				Scanner scanner=new Scanner();
			   	TopCodesList=scanner.scan(bp);//返回spots列表
			   	Log.e(TAG, "spots-->"+TopCodesList);
	    		Log.e(TAG,"getListSize"+String.valueOf(TopCodesList.size()));
				imgFavorite.setImageBitmap(bp);
				}
		 
	 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
