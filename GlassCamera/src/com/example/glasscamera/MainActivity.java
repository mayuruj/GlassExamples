package com.example.glasscamera;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//Intent intent=new Intent();
		Intent i = new Intent("android.intent.action.CAMERA_BUTTON");
		getApplicationContext().sendBroadcast(i);
		//takePicture();
		
		
        
	}

	@SuppressWarnings("deprecation")
	private void takePicture() {
		// TODO Auto-generated method stub
		Camera camera=Camera.open();
		Camera.Parameters parameters = camera.getParameters();
	    parameters.setPictureFormat(PixelFormat.JPEG);
	    camera.setParameters(parameters);
	    SurfaceView view = new SurfaceView(this);
	    try {
	        camera.setPreviewDisplay(view.getHolder());
	        
	        
	        camera.startPreview();
	        
	        
	        camera.takePicture(null,null,photoCallback);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	}
	 Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
		    public void onPictureTaken(byte[] data, Camera camera) {

		        Uri uriTarget = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
		        OutputStream imageFileOS;

		        try {

		            imageFileOS = getContentResolver().openOutputStream(uriTarget);
		            imageFileOS.write(data);
		            imageFileOS.flush();
		            imageFileOS.close();

		            Toast.makeText(MainActivity.this, "Image saved: " + uriTarget.toString(), Toast.LENGTH_LONG).show();
		            System.out.println("Image saved: " + uriTarget.toString());

		        } catch (FileNotFoundException e) {
		            e.printStackTrace();

		        } catch (IOException e) {
		            e.printStackTrace();

		        }
		        finish();

		    }
		};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

}
