package com.example.glassapp;

import java.util.ArrayList;
import java.util.Locale;

import android.speech.tts.TextToSpeech;

public class SpeechInteraction {
	
	public String getActivityResponse(ArrayList<String> textFromSpeechList) {
		// TODO Auto-generated method stub
		String action=null;
		for(String i :textFromSpeechList)					//
		{	System.out.println(i);
				if(i.equalsIgnoreCase("walking"))
				{
					action="walking";
				}
				else if(i.equalsIgnoreCase("talking"))
				{
					action="talking";
				}
				else if(i.equalsIgnoreCase("running"))
				{
					action="running";
				}
				else if(i.equalsIgnoreCase("working"))
				{
					action="working";
				}
		}
		
		return action; 
	}
	
	public String getConfirmation(ArrayList<String> textFromSpeechList)
	{
		String responseYesNo=null;
		for(String i:textFromSpeechList)
		{
			System.out.println(i);
				if(i.equalsIgnoreCase("YES") || i.equalsIgnoreCase("YEP") || i.equalsIgnoreCase("YUP") || i.equalsIgnoreCase("YEAH")){
					responseYesNo="Yes";
				}
				else
				{
					responseYesNo="No";
				}
		}
		return responseYesNo;
		
	}

}


