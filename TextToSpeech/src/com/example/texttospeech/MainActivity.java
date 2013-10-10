package com.example.texttospeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener{
	
	/**********Variable declaration*****/
	private 							TextToSpeech 				tts;
	private 	static final 			String 						RECORD		=	"ACTIVITY";	
	private 							String 						speechText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tts=new TextToSpeech(this,this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		System.out.println("inside init");
		if(status==TextToSpeech.SUCCESS)
		{
			int result=tts.setLanguage(Locale.US);
			if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
			{
				System.out.println("Language not supported ");
			}
			else
			{
				
				System.out.println("Result");
				
				textToSpeech(RECORD);
				
			}
		}
	}
	
	@SuppressLint("NewApi")
	private void textToSpeech(String utteranceId)
	{
			
			tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
			
			@Override
			public void onStart(String utteranceId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String utteranceId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDone(String utteranceId) {
				// TODO Auto-generated method stub
				if(utteranceId.equalsIgnoreCase(RECORD))
				{
					System.out.println("Done saying");
					Toast.makeText(MainActivity.this, "Record your current activity", Toast.LENGTH_LONG).show();
					//textToSpeech(RECORD);
				}
			}
			});
		
		HashMap<String, String> hashMap=new HashMap<String,String>();
		hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,utteranceId);
		
		tts.speak("Hi Mayur, please record your current activity", TextToSpeech.QUEUE_FLUSH,hashMap);
		//tts.speak("really ?",TextToSpeech.QUEUE_FLUSH , hashMap);
	
	}

	

}
