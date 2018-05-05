package gl2.kasri.younes.paintapplication.persistence;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceInfo {

    private String macAddress;
    private double longitude;
    private double latitude;
    private Activity activity;

    public DeviceInfo(Activity activity) {
        setActivity(activity);
        getMacAddr();
        getLocationData();
    }

    /* To Retrieve Data From DB and Tests */
    public DeviceInfo(String mac_device, String longitude, String latitude) {
    }



    private String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    private void getLocationData(){
        Geocoder geocoder;
        String bestProvider;
        List<Address> user = null;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null){

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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}