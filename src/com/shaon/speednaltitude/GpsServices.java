package com.shaon.speednaltitude;


import java.text.DecimalFormat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.Toast;

public class GpsServices extends Service {
	
	WakeLock wakeLock;
	LocationManager locationManager;	
	
	NotificationManager notificationManager;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
		
		Log.e("Google", "Service Created");
		
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		
		
		Toast.makeText(this, "The Service has been Started!", Toast.LENGTH_SHORT).show();
		
	    locationManager = (LocationManager) getApplicationContext()
	            .getSystemService(Context.LOCATION_SERVICE);

	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Toast.makeText(this, "Service Stopped!", Toast.LENGTH_SHORT).show();
		locationManager.removeUpdates(listener);
		locationManager = null;
	}
	
	private void showSpeedAndAltitude(double speed, double altitude) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, speed + " meters/s | " + altitude + " m", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, Dashboard.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		
		Notification n = new Notification.Builder(this)
				.setContentTitle(speed + " meters/s | " + altitude + " m")				
				.setContentIntent(pIntent).setAutoCancel(true)
				.addAction(R.drawable.ic_launcher, "Call", pIntent)
				.addAction(R.drawable.ic_launcher, "More", pIntent)
				.addAction(R.drawable.ic_launcher, "And more", pIntent).build();
		
		notificationManager.notify(0,n);
	}
	
	
	private LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
			Log.e("Google", "Location Changed");

	        if (location == null)
	            return;
	        
	        location.getLatitude();
	        
	       
	        
	        double speed = location.getSpeed();
	        double altitude = location.getAltitude();
	        
	        DecimalFormat df = new DecimalFormat("#.##");
	        
	        speed = Double.parseDouble(df.format(speed));
	        altitude = Double.parseDouble(df.format(altitude));
	        
	        showSpeedAndAltitude(speed, altitude);
			
		}



		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	};

}