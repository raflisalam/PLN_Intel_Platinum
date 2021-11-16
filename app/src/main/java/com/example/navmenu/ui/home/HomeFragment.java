package com.example.navmenu.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.navmenu.R;
import com.example.navmenu.datamodel.WeatherData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    private TextView tvUcapan, tvNamaUser, weatherLocation, weatherTemp, weatherState;
    private ImageView weatherIcon;

    final String APP_ID = "4f1e1339663625e816464fda44812fc4";
    final String WEATHER_URL = "https://home.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    private String location_provider = LocationManager.GPS_PROVIDER;
    private LocationManager locationManager;
    private LocationListener locationListener;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);

       tvUcapan = view.findViewById(R.id.tvUcapan);
       tvNamaUser = view.findViewById(R.id.tvNamaUser);

       weatherState = view.findViewById(R.id.weatherState);
       weatherIcon = view.findViewById(R.id.weatherIcon);
       weatherLocation = view.findViewById(R.id.weatherLocation);
       weatherTemp = view.findViewById(R.id.weatherTemp);



        Bundle extras = getActivity().getIntent().getExtras();
        String username;
        if (extras != null) {
            username = extras.getString("username");
            tvNamaUser.setText(username);
        }

       ucapanHari();
       return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeatherCurrentLocation();
    }

    private void getWeatherCurrentLocation() {
        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID );
                getNetwork(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(location_provider, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    private void getNetwork(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getContext(), "Data berhasil didapatkan", Toast.LENGTH_SHORT).show();

                WeatherData weatherD = WeatherData.fromJson(response);
                updateUI(weatherD);
//                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void updateUI(WeatherData weatherD) {
            weatherTemp.setText(weatherD.getTemp());
            weatherLocation.setText(weatherD.getCity());
            weatherState.setText(weatherD.getState());
            int resourceID = getResources().getIdentifier(weatherD.getIcon(), "drawable", getContext().getPackageName());
            weatherIcon.setImageResource(resourceID);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationManager !=null ){
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Lokasi didapatkan", Toast.LENGTH_SHORT).show();
                getWeatherCurrentLocation();
            } else {

            }
        }
    }

    private void ucapanHari() {
        Calendar kalender = Calendar.getInstance();
        int jam = kalender.get(Calendar.HOUR_OF_DAY);

        if (jam >= 0 && jam < 12) {
            tvUcapan.setText("Good Morning,");
        } else if (jam >= 12 && jam < 18) {
            tvUcapan.setText("Good Afternoon");
        } else if (jam >= 18 && jam < 24) {
            tvUcapan.setText("Good Evening");
        } else {
        }
    }

}