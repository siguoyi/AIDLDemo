package com.example.aidldemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private ICat catService;
	private Button bt_get;
	private EditText et_color;
	private EditText et_weight;
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			catService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			catService = ICat.Stub.asInterface(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt_get = (Button) findViewById(R.id.bt_get);
		et_color = (EditText) findViewById(R.id.et_color);
		et_weight = (EditText) findViewById(R.id.et_weight);
		
		Intent intent = new Intent();
		intent.setAction("com.example.aidldemo.action.AIDL_SERVICE");
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		
		bt_get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					et_color.setText(catService.getColor());
					et_weight.setText(catService.getWeight() + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unbindService(conn);
	}
}
