package in.championswimmer.delhiautometer;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class TrackActivity extends Activity {

    public TextView meterKilometer, meterFare;
    public LocationManager locationManager;
    public Location lastLocation = null;
    public Float distance = (float) 0.0, finalKilometers = (float) 0, finalFare = (float) 0;
    public Criteria criteria;
    public Context context;


    public TrackActivity() {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //context = getApplicationContext();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);

        meterKilometer = (TextView) findViewById(R.id.meterKilometer);
        meterFare = (TextView) findViewById(R.id.meterFare);

        meterKilometer.setText("Distance \t : " + (String.format("%.2f", finalKilometers)));
        meterFare.setText("Fare \t \t \t : " + String.format("%.2f", finalFare));
        boolean locationEnabled = locationManager.isProviderEnabled(locationManager.getBestProvider(criteria, false));
        if (!locationEnabled) {
            Toast.makeText(getApplicationContext(),
                    "Please enable GPS to use Real Time Tracking", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        trackLocation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.track, menu);
        return true;
    }

    public void trackLocation () {
        Log.d("ARNAV", "tracklocation started");

        LocationListener locLis = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.d("ARNAV", "override onlocationchanged");
                if (lastLocation == null) {
                    lastLocation = location;
                }
                distance += lastLocation.distanceTo(location);
                lastLocation = location;
                updateMeter(distance);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("ARNAV", "provider status changed");

            }

            @Override
            public void onProviderEnabled(String s) {
                //lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
                Log.d("ARNAV", "provider enabled");
                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 600, 50, this);


            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getApplicationContext(),
                        "DO NOT DISABLE GPS!!\nEnable GPS immediately to resume tracking", Toast.LENGTH_LONG).show();

            }
        };
        locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 600, 50, locLis);


    }

    public void updateMeter (float mDist) {
        finalKilometers = mDist / 1000;
        if (finalKilometers > 2 ) {
            finalFare = (((finalKilometers - 2) * 8) + 25);
        }
        else {
            finalFare = (float) 0.00;
        }
        Log.d("ARNAV", finalKilometers + "  " + finalFare);
        meterKilometer.setText("Distance \t : " + (String.format("%.2f", finalKilometers)));
        meterFare.setText("Fare \t \t \t : " + String.format("%.2f", finalFare));

    }
    
}
