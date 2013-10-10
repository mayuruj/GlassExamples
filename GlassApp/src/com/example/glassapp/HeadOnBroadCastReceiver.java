package com.example.glassapp;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class HeadOnBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
			if (intent.getAction().equals("com.google.glass.action.DON_STATE")) {
	            handleDonStateChanged(context, intent);
	        }
	}
	
	private void handleDonStateChanged(Context context, Intent intent) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = preferences.edit();
				if (!intent.getExtras().getBoolean("is_donned")) {
		            ///If we take it off, then lock the device
						//PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(MainActivity.PREF_LOCKED, true).commit();
					System.out.println("D is not onned");
					//Toast.makeText(GlassActivity.this,, duration)
					
					  editor.putBoolean(GlassActivity.ON_HEAD,false);
					  
				}
				else
				{
					System.out.println("D is onned");
					editor.putBoolean(GlassActivity.ON_HEAD, true);
				}
				editor.commit();
				
		    }

		    private void bringMainActivityToFront(Context context) {
		       // MainActivity.startLockScreen(context);
		    }	 
}
