package com.shaon.speednaltitude;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


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
		        
				if(CanGetLocation())
		        {
					Intent aStartService = new Intent(getBaseContext(), GpsServices.class);			
					startService(aStartService);
		        } 
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
    
    public boolean CanGetLocation()
	{
		LocationManager aLocationManager = (LocationManager) Dashboard.this.getSystemService(LOCATION_SERVICE);
		
		boolean isGPSEnabled, isNetworkEnabled;
		
		// getting GPS status
        isGPSEnabled = aLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
                
        // getting network status
        isNetworkEnabled = aLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        if (isGPSEnabled && isNetworkEnabled)
        {
        	return true;
        }else
        {
        	return false;
        }
	}
}
