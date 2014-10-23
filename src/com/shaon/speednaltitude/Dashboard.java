package com.shaon.speednaltitude;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Dashboard extends ActionBarActivity {
	
	Button btnStart, btnStop;
	//TextView txtSpeed, txtAltitude;	
	
	void InitializeUI()
	{
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		
		/*txtSpeed = (TextView) findViewById(R.id.txtSpeed);
		txtAltitude = (TextView) findViewById(R.id.txtAltitude);*/
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        InitializeUI();
        
        btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        
				if(!IsNetworkEnabled())
		        {
					Toast.makeText(Dashboard.this, "Enable internet and Try again!", 
							Toast.LENGTH_LONG).show();
		        }else if(!IsGPSEnabled()){
		        	Toast.makeText(Dashboard.this, "Enable GPS and Try again!", 
							Toast.LENGTH_LONG).show();
		        }else{
		        	Intent aStartService = new Intent(getBaseContext(), GpsServices.class);			
					startService(aStartService);
		        }
				
				/*NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				
				Notification n = new NotificationCompat.Builder(Dashboard.this)
				.setContentTitle("It's a Notification")				
				.setSmallIcon(R.drawable.ic_launcher)				
				.setAutoCancel(true).build();
				
				notificationManager.notify(0,n);*/
				
			}
		});
        
        btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopService(new Intent(getBaseContext(), GpsServices.class));
			}
		});
        
        
    }
    
    boolean IsGPSEnabled()
    {
		LocationManager aLocationManager = (LocationManager) Dashboard.this
				.getSystemService(LOCATION_SERVICE);
		boolean isGPSEnabled = aLocationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		return isGPSEnabled;
    }
    
    boolean IsNetworkEnabled()
    {
    	ConnectivityManager connectivity = (ConnectivityManager) this
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivity != null) {
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();
	        if (info != null)
	            for (int i = 0; i < info.length; i++)
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	                    return true;
	                }

	    }
	    return false;
    }
    
}
