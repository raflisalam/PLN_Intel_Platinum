package com.example.navmenu.ui.karebosi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navmenu.InputSurveyActivity;
import com.example.navmenu.MainActivity;
import com.example.navmenu.R;
import com.example.navmenu.datamodel.RegistrationModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.navmenu.InputSurveyActivity.DETAIL_DATA2;

public class DetailKarebosiActivity extends AppCompatActivity {

    public static final String DETAIL_DATA = "detail_data";
    private static final int REQUEST_CALL = 1;

    private ArrayList<RegistrationModel> registrationModels = new ArrayList<RegistrationModel>();
    private Boolean clicked = false;

    private TextView tvNamaPelanggan, idPelanggan, tvAlamat, tvDaya, tvNo_hp, tvTarif, tvIndikasi, tvStandRek, tagLokasi, fotoMeter, fotoRumah;
    private ImageButton btnBack;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private FloatingActionButton btnFloat, btnInput, btnCalling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karebosi);

        getSupportActionBar().hide();

        tvNamaPelanggan = findViewById(R.id.tvNamaPelanggan);
        idPelanggan = findViewById(R.id.idPelanggan);
        tvAlamat = findViewById(R.id.tvALamatPelanggan);
        tvDaya = findViewById(R.id.jmlhDaya);
        tvTarif = findViewById(R.id.jmlhTarif);
        tvIndikasi = findViewById(R.id.tvIndikasi);
        tvStandRek = findViewById(R.id.angkaStand);
        tagLokasi = findViewById(R.id.tagLokasi);
        tvNo_hp = findViewById(R.id.tvNoHp);
        fotoMeter = findViewById(R.id.fotoMeter);
        fotoRumah = findViewById(R.id.fotoRumah);
        btnBack = findViewById(R.id.btnBack);

        //button float
        btnFloat = findViewById(R.id.btnFloat);
        btnInput = findViewById(R.id.btnInput);
        btnCalling = findViewById(R.id.btnCall);

        RegistrationModel registrationModel = getIntent().getParcelableExtra(DETAIL_DATA);
        if (registrationModel != null) {
            idPelanggan.setText(registrationModel.getIdpel());
            tvNamaPelanggan.setText(registrationModel.getNama());
            tvAlamat.setText(registrationModel.getAlamat());
            tvDaya.setText(registrationModel.getDaya());
            tvTarif.setText(registrationModel.getTarif());
            tvIndikasi.setText(registrationModel.getIndikasi());
            tvStandRek.setText(registrationModel.getStandRek());
            tagLokasi.setText(registrationModel.getTagLokasi());
            tvNo_hp.setText(registrationModel.getNo_hp());
            fotoMeter.setText(registrationModel.getFotoMeter());
            fotoRumah.setText(registrationModel.getFotoRumah());

            btnInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailKarebosiActivity.this, InputSurveyActivity.class);
                    intent.putExtra(DETAIL_DATA2, registrationModel);
                    startActivity(intent);
                }
            });

            btnCalling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calling();
                }
            });
        }

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibilty(clicked);
                setAnimation(clicked);
//                setClickable(clicked);
                clicked = !clicked;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKarebosiActivity.this, KarebosiFragment.class);
                startActivity(intent);
            }
        });

        //animation
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

    }

    private void calling() {
        String noHp = "62"+tvNo_hp.getText().toString();
        if (noHp.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(DetailKarebosiActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailKarebosiActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + noHp;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                calling();
            } else {
                Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setVisibilty(Boolean clicked) {
        if (!clicked) {
            btnInput.setVisibility(View.VISIBLE);
            btnCalling.setVisibility(View.VISIBLE);
        } else {
            btnInput.setVisibility(View.INVISIBLE);
            btnCalling.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked) {
            btnInput.startAnimation(fromBottom);
            btnCalling.startAnimation(fromBottom);
            btnFloat.startAnimation(rotateOpen);
        } else {
            btnInput.startAnimation(toBottom);
            btnCalling.startAnimation(toBottom);
            btnFloat.startAnimation(rotateClose);
        }
    }
}