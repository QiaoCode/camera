package com.example.camera;

import java.util.ArrayList;
import java.util.List;


import TopCodes.Scanner;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
/*
 * 　java.lang.Object/android.view.View/android.widget.ImageView　　
已知直接子类：ImageButton, QuickContactBadge 
显示任意图像，例如图标。ImageView类可以加载各种来源的图片（如资源或图片库），
需要计算图像的尺寸，比便它可以在其他布局中使用，并提供例如缩放和着色（渲染）各种显示选项。
 * */
	ImageView imgFavorite;
	private static String TAG="LIST";
	//private Scanner scanner=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imgFavorite=(ImageView)findViewById(R.id.imageView1);
        imgFavorite.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		open();
        	}
        });
		
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
				scanner.scan(bp);
				imgFavorite.setImageBitmap(bp);

				}
		 
		 //List<TopCode> TopCodeList =new ArrayList<TopCode>();
	 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
