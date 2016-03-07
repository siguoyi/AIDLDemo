package com.example.aidldemo;

import java.util.Timer;
import java.util.TimerTask;

import com.example.aidldemo.ICat.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AidlService extends Service {
	private static final String TAG = "AidlService";
	
	private CatBinder catBinder;
	Timer timer = new Timer();
	String[] colors = new String[]{"red","yellow","black"};
	double[] weights = new double[]{2.3,3.1,1.58};
	
	private String color;
	private double weight;

	@Override
	public IBinder onBind(Intent intent) {
		return catBinder;
	}

	public class CatBinder extends Stub{

		@Override
		public String getColor() throws RemoteException {
			return color;
		}

		@Override
		public double getWeight() throws RemoteException {
			return weight;
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		catBinder = new CatBinder();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				int rand = (int)(Math.random() * 3);
				color = colors[rand];
				weight = weights[rand];
				Log.d(TAG, "--------------" + rand);
			}
		}, 0,800);
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
	}
}
