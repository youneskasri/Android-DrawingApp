package gl2.kasri.younes.paintapplication.persistence;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static gl2.kasri.younes.paintapplication.Dev.TAG;

@SuppressWarnings("WeakerAccess")
public class DeviceInfo {

    private String macAddress;
    private double longitude;
    private double latitude;
    private Activity activity;

    public DeviceInfo(Activity activity) {
        setActivity(activity);
        macAddress=getMacAddr();
        getLocationData();
    }

    /* To Retrive Data from DB and Tests */
    public DeviceInfo(String mac_device, String longitude, String latitude) {
        macAddress = mac_device;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    private String getMacAddr() {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress()
    }

    private void getLocationData(){
        Geocoder geocoder;
        String bestProvider;
        List<Address> user;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null){
            Log.i(TAG, "getLocationData: Couldn't get Location data");
            return;
        }
        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null){
            Log.i(TAG, "getLocationData: Couldn't get Location data");
        }else{
            geocoder = new Geocoder(activity);
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                latitude =  user.get(0).getLatitude();
                longitude =  user.get(0).getLongitude();
                System.out.println(" DDD lat: " + latitude +",  longitude: "+ longitude);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getMacAddress() {
        return macAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
