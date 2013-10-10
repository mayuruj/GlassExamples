package com.example.glassapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

//import com.example.texttospeech.MainActivity;






import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.PendingIntent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

/**
 * @author joe
 * 
 */
public class GlassActivity extends Activity implements TextToSpeech.OnInitListener{

	protected static final int 							SPEECH		=		1;
	protected static final int 							YESNO        =      2;
	protected String 									action		=		"";
	protected TextToSpeech								tts;
	protected PowerManager 								pm;
	protected int 										PROX_POS	=		1;				
	protected SensorManager								sensorManager;
	protected Sensor									sensor;
	//private 							TextToSpeech 				tts;
	private	  static final 				String 			RECORD		=	"ACTIVITY";
	private   static final 				String 			RESPONSE	=	"YesNo";
	private 							String 			speechText;
	protected 							String	 		message;
	private   static final 				String			ERROR		=	"Did not get you";
	private	  static final				String			YES			=	"Yes";
	public 	  static final 				String 			ON_HEAD = 	"onhead";
	private   							String			intention;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_glass);
		sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(GlassActivity.ON_HEAD, true))
		{


			Intent i = new Intent("android.intent.action.CAMERA_BUTTON");
			getApplicationContext().sendBroadcast(i);
			if(isNetworkAvailable())
			{
				try																									// Code to convert speech to text
				{
					//PackageManager pkgManager=this.getPackageManager();
					/*if(!(pkgManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)))
					{
					System.out.println("It does not have a proximity sensor");

					}*/





					if(!((PowerManager) getSystemService(Context.POWER_SERVICE)).isScreenOn())
					{	
						System.out.println("Screen is off");
						pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
						WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
						wakeLock.acquire();
						KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE); 
						KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
						keyguardLock.disableKeyguard();
					}
					tts = new TextToSpeech(GlassActivity.this,GlassActivity.this);
					// Code to convert speech to text


					System.out.println("Inside oncreate of the application");

						




				}
				catch(ActivityNotFoundException exception)
				{
					System.out.println(exception.getMessage());
					Toast.makeText(getApplicationContext(), "Your device does not support speech to text conversion", 1000).show();

				}
			}
		}
		System.out.println("Inside oncreate");

		AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		//Intent intent=new Intent(MainActivity.this, MainActivity.class);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		callIntent.setClass(GlassActivity.this,GlassActivity.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent=PendingIntent.getActivity(GlassActivity.this, 0, callIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE,10);
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glass, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("Inside onActivity result of the application");
		switch (requestCode) {
		case SPEECH: 																									//did receive some speech
			if (resultCode == RESULT_OK) {
				Log.i("Message", "Damn !!! Inaccurate amount of time !!");
				//Get the resultant text in an arraylist
				ArrayList<String> textFromSpeechList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if(!textFromSpeechList.isEmpty())
				{
					SpeechInteraction interaction=new SpeechInteraction();
					String action=interaction.getActivityResponse(textFromSpeechList);
					message="I see you are "+action;
					textToSpeech(RESPONSE,message);
				}
			}
			else
			{
				message="Sorry, could not hear you. Please repeat your message";
				textToSpeech(ERROR, message);
			}

			break;
			/*case YESNO:
			if(resultCode==RESULT_OK){
				ArrayList<String> textFromSpeechList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if(!textFromSpeechList.isEmpty())
				{
					SpeechInteraction interaction=new SpeechInteraction();
					String action=interaction.getConfirmation(textFromSpeechList);
					if(action.equalsIgnoreCase("YES"))
					{
						message="Thank you";
					}
					else
					{
						message="I will be back again";
					}
				}
			}
			break;*/
		}



		finish();
	}
	@Override
	public void onPause() {
		super.onPause();  
		//sensorManager.unregisterListener(this);
		//Intent startTimerServiceIntent=new Intent(GlassActivity.this,BackgroundService.class);
		//startService(startTimerServiceIntent);
		//tts.shutdown();

		finish();

	}

	@Override
	public void onResume() {
		super.onResume(); 
		System.out.println("Inside onResume");
		//sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);


		if(isNetworkAvailable())
		{
			try																									// Code to convert speech to text
			{
				//PackageManager pkgManager=this.getPackageManager();
				/*if(!(pkgManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)))
			{
				System.out.println("It does not have a proximity sensor");

			}*/





				if(!((PowerManager) getSystemService(Context.POWER_SERVICE)).isScreenOn())
				{	
					System.out.println("Screen is off");
					pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
					WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
					wakeLock.acquire();
					KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE); 
					KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
					keyguardLock.disableKeyguard();
				}
				tts = new TextToSpeech(GlassActivity.this,GlassActivity.this);
				// Code to convert speech to text


				System.out.println("Inside oncreate of the application");






			}
			catch(ActivityNotFoundException exception)
			{
				System.out.println(exception.getMessage());
				Toast.makeText(getApplicationContext(), "Your device does not support speech to text conversion", 1000).show();

			}
		}
		System.out.println("Inside oncreate");

		AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		//Intent intent=new Intent(MainActivity.this, MainActivity.class);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		callIntent.setClass(GlassActivity.this,GlassActivity.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent=PendingIntent.getActivity(GlassActivity.this, 0, callIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE,10);
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
		finish();
	}





	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
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
				message="Hi Steephen, what are you doing?";
				textToSpeech(RECORD,message);

			}
		}

	}

	

	@SuppressLint("NewApi")
	private void textToSpeech(String utteranceId,String message)
	{
		System.out.println("inside text to speech");
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

				if(utteranceId.equalsIgnoreCase(RECORD) || utteranceId.equalsIgnoreCase(ERROR))
				{
					System.out.println("Done saying");
					//Toast.makeText(GlassActivity.this, "Record your current activity", Toast.LENGTH_LONG).show();
					//textToSpeech(RECORD);
					//tts.stop();
					//tts.shutdown();
					speechToText();
				}
				else if(utteranceId.equalsIgnoreCase(RESPONSE))
				{
					System.out.println("Get the user response");
					speechToText();
				}

			}
		});

		HashMap<String, String> hashMap=new HashMap<String,String>();
		hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,utteranceId);

		tts.speak(message, TextToSpeech.QUEUE_FLUSH,hashMap);
		//tts.speak("really ?",TextToSpeech.QUEUE_FLUSH , hashMap);

	}
	private void speechToText()
	{
		try{
			System.out.println("Inside speech to text");
			Intent speechRecognizerIntent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);			// Recognize the speech to convert it to text 
			speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
			speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			startActivityForResult(speechRecognizerIntent, SPEECH);
		}
		catch(ActivityNotFoundException activityNotFoundEx)
		{
			System.out.println("Problem loading activity");

		}
	}
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
