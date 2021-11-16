package com.example.navmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navmenu.datamodel.RegistrationModel;
import com.example.navmenu.fan.FAN;
import com.example.navmenu.fan.FileJson;
import com.example.navmenu.fan.InterfaceFAN;
import com.example.navmenu.fan.InterfaceFileJson;
import com.example.navmenu.ui.karebosi.DetailKarebosiActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class InputSurveyActivity extends AppCompatActivity implements View.OnClickListener, InterfaceFAN, InterfaceFileJson.save {

    private Button btnSubmit;
    private ImageButton btnBack, btnGetLocation;
    private TextView idPel, tagging;
    private EditText etStanSurvey;
    private FusedLocationProviderClient fusedLocationProviderClient;

    //spinner
    private Spinner spinner;

    private String inIdpel, inNama, inAlamat, inUnit, inDaya, inTarif, inStanRek, inTagLokasi, inFotoMeter, inFotoRumah, inNo_hp, inIndikasi, strTagging;
    public static final String DETAIL_DATA2 = "detail_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_survey);

        getSupportActionBar().hide();

        spinner = findViewById(R.id.spinner);

        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        idPel = findViewById(R.id.dataIdpel);
        etStanSurvey = findViewById(R.id.inputStanSurvey);
        tagging = findViewById(R.id.tvCurrentLocation);


        //get Cordinates Tagging Survey//
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                InputSurveyActivity.this
        );
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(InputSurveyActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(InputSurveyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(InputSurveyActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                    ,Manifest.permission.ACCESS_COARSE_LOCATION}
                                    ,100);
                }
            }
        });
        //end get tagging survey//

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputSurveyActivity.this, DetailKarebosiActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(this);

        //ambil idpel
        RegistrationModel registrationModel = getIntent().getParcelableExtra(DETAIL_DATA2);
        if (registrationModel !=null) {
            idPel.setText(registrationModel.getIdpel());
        }

        //Spinner
        ArrayAdapter adapterSpinner = ArrayAdapter.createFromResource(this, R.array.pilihan_tindaklanjut, R.layout.list_tindaklanjut);
        adapterSpinner.setDropDownViewResource(R.layout.list_tindaklanjut);
        spinner.setAdapter(adapterSpinner);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull  Task<Location> task) {
                    Location location = task.getResult();
                    if (location !=null) {
                        tagging.setText(String.valueOf(location.getLatitude()+","+location.getLongitude()));
                    } else {
                        @SuppressLint("RestrictedApi") LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                tagging.setText(String.valueOf(location1.getLatitude()+","+location1.getLongitude()));
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onClick(View v) {
        if (btnSubmit == v) {

            RegistrationModel registrationModel = getIntent().getParcelableExtra(DETAIL_DATA2);
            if (registrationModel != null) {
                inIdpel = registrationModel.getIdpel();
                inAlamat = registrationModel.getAlamat();
                inDaya = registrationModel.getDaya();
                inFotoMeter = registrationModel.getFotoMeter();
                inNama = registrationModel.getNama();
                inStanRek = registrationModel.getStandRek();
                inTagLokasi = registrationModel.getTagLokasi();
                inIndikasi = registrationModel.getIndikasi();
                inTarif = registrationModel.getTarif();
                inNo_hp = registrationModel.getNo_hp();
                inFotoRumah = registrationModel.getFotoRumah();
                inUnit = registrationModel.getUnit();
            }

            String stanSurvey = etStanSurvey.getText().toString();
            String tindakLanjut = String.valueOf(spinner.getSelectedItem()).toUpperCase();
            String taggingSurvey = tagging.getText().toString();

            if (TextUtils.isEmpty(stanSurvey)) {
                etStanSurvey.setError("Kolom tidak boleh kosong");
                return;
            }

            //input data ke excel
            Map<String, String> maps = new HashMap<>();
            maps.put("idpel", inIdpel);
            maps.put("nama", inNama);
            maps.put("alamat", inAlamat);
            maps.put("unit", inUnit);
            maps.put("no_hp", inNo_hp);
            maps.put("tarif", inTarif);
            maps.put("daya", inDaya);
            maps.put("indikasi", inIndikasi);
            maps.put("stan_rek", inStanRek);
            maps.put("tagging_lokasi", inTagLokasi);
            maps.put("foto_rumah", inFotoRumah);
            maps.put("foto_meter", inFotoMeter);
            maps.put("stan_survey", stanSurvey);
            maps.put("tindak_lanjut", tindakLanjut);
            maps.put("tagging_survey", taggingSurvey);
            String url = "https://script.google.com/macros/s/AKfycbyHnBT7nnJg-mewlF6OhlIeiq-V19qEGZH4z_owUQQxEt6h4tu2f_Jq_9bnLtXAeAGIeA/exec";
            FAN fan = new FAN();
            fan.postFAN(url, "simpan", maps,this);
        }
    }

    @Override
    public void successFAN(String json) throws JSONException {
        FileJson fileJson = new FileJson();
        fileJson.simpan(json, this);
    }

    @Override
    public void errorFAN(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successSimpan(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}