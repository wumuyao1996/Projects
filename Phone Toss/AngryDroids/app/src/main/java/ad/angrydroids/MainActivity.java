package ad.angrydroids;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.location.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import android.hardware.*;


public class MainActivity extends ActionBarActivity {

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static Button theButton = null;
    private static TextView text = null;
    private static TableLayout stats;
    private static int TIMER = 5;
    private static CountDownTimer startCounting = null;
    private static Timer cd;
    private boolean isPaused;
    private double longI;
    private double latI;
    private double longT;
    private double latT;
    private int score;
    private double heightI;
    private double heightS;
    private ProgressDialog  dialog;



    private SensorManager sm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        sm = new SensorManager() {
        }*/

        setContentView(R.layout.activity_main);
        Fragment f = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, f)
                    .commit();
        }

        //getSupportFragmentManager().beginTransaction().hide(f);

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Log.println(Log.ASSERT, "Location manager", locationManager.toString());
        // Define a listener that responds to location updates


        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


    }

    private int updateScore(Location loc){

        double R = 6378.137;
        double long2=loc.getLongitude();
        double lat2=loc.getLatitude();

        double dLat = (lat2-latT)*Math.PI/180;
        double dLon = (long2-longT)*Math.PI/180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(latT * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c *1000;//meters

        double newH = (loc.getAltitude()-heightI);
        if(newH>heightS)
            heightS=newH;

        longT= long2;
        latT=lat2;
        return score+(int)(newH*d);
    }

    private double getDistance(Location loc)
    {
        double R = 6371; //radius of earth
        double dLat = (loc.getLatitude()-latI)*Math.PI/180;
        double dLon = (loc.getLongitude()-longI)*Math.PI/180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(latI * Math.PI / 180) * Math.cos(loc.getLatitude() * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d*1000;//meters
    }
    private boolean checkStopped()
    {
        return true;

    }

    public void showStatus(MenuItem mi)
    {
        text.setText("Hello");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPause(){
        super.onPause();
        if(startCounting!=null)
            startCounting.cancel();

        locationManager.removeUpdates(locationListener);
        text.setText("Welcome Back!");
        theButton.setClickable(true);
        theButton.setText("Throw Me");

        return;
    }

    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        TextView tv = (TextView) findViewById(R.id.firstTextView);
        tv.clearAnimation();
        tv.startAnimation(a);
    }

    public void onResume() {
        super.onResume();

        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 0, locationListener);
                theButton.setClickable(false);
                theButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,80);
                theButton.setText("");



                startCounting = new CountDownTimer(6000, 30) {
                    public void onTick(long millisUntilFinished) {


                        int k = (int)(((6000 - millisUntilFinished)/2) % 500 / 3);
                        theButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, k);
                        //text.setText("Throw in: ");

                        theButton.setText(((millisUntilFinished / 1000) +1) + "");

                    }

                    public void onFinish() {

                        theButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);

                        text.setText("NOW");
                        stats.setVisibility(View.VISIBLE);
                        Location temp = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if(temp == null)
                        {
                            locationManager.removeUpdates(locationListener);
                            text.setText("Please find a better GPS connection");
                            theButton.setClickable(true);
                            theButton.setText("Throw Me");
                            return;
                        }
                        heightS = 0;
                        heightI = temp.getAltitude();
                        longI = temp.getLongitude();
                        latI = temp.getLatitude();
                        score = 0;

                        new CountDownTimer(10000, 100) {
                            public void onTick(long millisUntilFinished) {

                                text.setText("I'm FLYING!");
                                theButton.setText(millisUntilFinished / 1000+"");
                                score = updateScore(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

                            }
                            public void onFinish() {
                                theButton.setText(""+score);
                                text.setText("Distance thrown" + getDistance(
                                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER))+"Meters!");
                                theButton.setClickable(true);
                                //theButton.setText("Again!");
                                locationManager.removeUpdates(locationListener);
                            }
                        }.start();
                    }
                }.start();

        }
    });

}



/**
 * A placeholder fragment containing a simple view.
 */
public static class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if (rootView != null) {
            theButton = (Button) rootView.findViewById(R.id.button);
            text = (TextView) rootView.findViewById(R.id.textView);
            //heightView = (TextView) rootView.findViewById(R.id.height);

            stats = (TableLayout) rootView.findViewById(R.id.score);
            stats.setVisibility(View.GONE);
        }
        return rootView;
    }
}


}
