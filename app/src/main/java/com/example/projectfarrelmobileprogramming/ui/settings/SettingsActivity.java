package com.example.projectfarrelmobileprogramming.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText etName, etNim;
    private MaterialButton btnSaveSettings;
    private TextView tvCurrentProfile;
    
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_NIM = "user_nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialToolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etName = findViewById(R.id.etName);
        etNim = findViewById(R.id.etNim);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        tvCurrentProfile = findViewById(R.id.tvCurrentProfile);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        loadProfile();

        btnSaveSettings.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String nim = etNim.getText() != null ? etNim.getText().toString().trim() : "";

        if (name.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Nama dan NIM tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_NIM, nim);
        editor.apply();

        Toast.makeText(this, "Profil Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        loadProfile();
    }

    private void loadProfile() {
        String name = sharedPreferences.getString(KEY_NAME, "");
        String nim = sharedPreferences.getString(KEY_NIM, "");

        if (!name.isEmpty() && !nim.isEmpty()) {
            tvCurrentProfile.setText("Nama: " + name + "\nNIM: " + nim);
            etName.setText(name);
            etNim.setText(nim);
        } else {
            tvCurrentProfile.setText("Belum ada data profil.");
        }
    }
}
