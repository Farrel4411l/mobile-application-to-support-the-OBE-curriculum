package com.example.projectfarrelmobileprogramming.ui.note;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private NoteAdapter noteAdapter;
    private RecyclerView rvNotes;
    private TextInputEditText etNote;
    private MaterialButton btnSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        MaterialToolbar toolbar = findViewById(R.id.toolbarNote);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etNote = findViewById(R.id.etNote);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        rvNotes = findViewById(R.id.rvNotes);

        databaseHelper = new DatabaseHelper(this);
        
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(null, note -> {
            databaseHelper.deleteNote(note.getId());
            Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show();
            loadNotes();
        });
        rvNotes.setAdapter(noteAdapter);

        btnSaveNote.setOnClickListener(v -> saveNote());
        
        loadNotes();
    }

    private void saveNote() {
        String text = etNote.getText() != null ? etNote.getText().toString().trim() : "";
        if (text.isEmpty()) {
            Toast.makeText(this, "Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseHelper.insertNote(text);
        etNote.setText(""); // clear input
        Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    private void loadNotes() {
        List<Note> notes = databaseHelper.getAllNotes();
        noteAdapter.setNotes(notes);
    }
}
