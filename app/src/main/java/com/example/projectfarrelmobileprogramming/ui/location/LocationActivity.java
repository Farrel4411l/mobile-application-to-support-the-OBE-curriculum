package com.example.projectfarrelmobileprogramming.ui.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvCoordinate;
    private MaterialButton btnGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MaterialToolbar toolbar = findViewById(R.id.toolbarLocation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvCoordinate = findViewById(R.id.tvCoordinate);
        btnGetLocation = findViewById(R.id.btnGetLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(v -> checkPermissionAndGetLocation());
    }

    private void checkPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Meminta izin lokasi jika belum diberikan
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Izin sudah diberikan, ambil lokasi
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        btnGetLocation.setEnabled(false);
        btnGetLocation.setText("Mencari Lokasi...");

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                btnGetLocation.setEnabled(true);
                btnGetLocation.setText("Dapatkan Lokasi Terkini");

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    tvCoordinate.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                    Toast.makeText(LocationActivity.this, "Lokasi berhasil didapatkan!", Toast.LENGTH_SHORT).show();
                } else {
                    tvCoordinate.setText("Lokasi tidak ditemukan. Pastikan GPS HP menyala.");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan oleh pengguna
                Toast.makeText(this, "Izin Lokasi Diberikan!", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                // Izin ditolak
                Toast.makeText(this, "Izin Lokasi Ditolak. Tidak dapat mengambil lokasi.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
