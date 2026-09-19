package com.example.projectfarrelmobileprogramming;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.cardview.widget.CardView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Find Cards
        CardView cardProdi = findViewById(R.id.cardProdi);
        CardView cardMk = findViewById(R.id.cardMk);
        CardView cardMapping = findViewById(R.id.cardMapping);
        CardView cardRps = findViewById(R.id.cardRps);
        CardView cardRtm = findViewById(R.id.cardRtm);
        CardView cardAsesmen = findViewById(R.id.cardAsesmen);
        CardView cardSettings = findViewById(R.id.cardSettings);
        CardView cardLocation = findViewById(R.id.cardLocation);
        CardView cardNote = findViewById(R.id.cardNote);

        // Set Click Listeners
        cardProdi.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.prodi.ProdiActivity.class);
            startActivity(intent);
        });
        cardMk.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.mk.MkActivity.class);
            startActivity(intent);
        });
        cardMapping.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.mapping.MappingActivity.class);
            startActivity(intent);
        });
        cardRps.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.rps.RpsActivity.class);
            startActivity(intent);
        });
        cardRtm.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.rtm.RtmActivity.class);
            startActivity(intent);
        });
        cardAsesmen.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.asesmen.AsesmenActivity.class);
            startActivity(intent);
        });
        cardSettings.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.settings.SettingsActivity.class);
            startActivity(intent);
        });
        cardLocation.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.location.LocationActivity.class);
            startActivity(intent);
        });
        cardNote.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.note.NoteActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // Hanya bottom padding agar header kena atas
            return insets;
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}