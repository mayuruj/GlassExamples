/*package com.example.glassapp;


import java.util.Timer;
import java.util.TimerTask;


import android.app.Service;
//import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.IBinder;
//import android.preference.PreferenceManager.OnActivityResultListener;
//import android.speech.RecognizerIntent;
//import android.widget.Toast;

public class BackgroundService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate()
	{
		
	}
	@Override
	public void onDestroy()
	{
		
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId)
	{
				System.out.println("Get me out of here !!");
				

			   PendingIntent delayPendingIntent
			     = PendingIntent.getBroadcast(getBaseContext(),
			       0, myIntent, 0);
			  
			    AlarmManager alarmManager
			      = (AlarmManager)getSystemService(ALARM_SERVICE);
			    Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(System.currentTimeMillis());
			    calendar.add(Calendar.SECOND, 10);
			    long interval = 1000; 								//Interval not good ?
			    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
			      calendar.getTimeInMillis(), interval, delayPendingIntent);
				Timer timerToReturnBack=new Timer();
				TimerTask timerTask=new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intentToActivity = new Intent(getApplicationContext(),
								GlassActivity.class);
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
						callIntent.setClass(getApplicationContext(),GlassActivity.class);
						startActivity(callIntent);
						stopSelf();
						//startActivity(intentToActivity);
					}
				};
			    timerToReturnBack.schedule(timerTask, 10*1000);
			    
			    Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				callIntent.setClass(getApplicationContext(),GlassActivity.class);
				startActivity(callIntent);
				stopSelf();
			    return START_NOT_STICKY;
	}
	
	
	
	
	
}
*/