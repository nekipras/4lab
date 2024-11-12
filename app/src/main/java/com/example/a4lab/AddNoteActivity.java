package com.example.a4lab;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteNameEditText;
    private EditText noteContentEditText;
    private Button saveNoteButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteNameEditText = findViewById(R.id.noteNameEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        databaseHelper = new DatabaseHelper(this);

        saveNoteButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteName = noteNameEditText.getText().toString().trim();
        String noteContent = noteContentEditText.getText().toString().trim();

        if (TextUtils.isEmpty(noteName)) {
            Toast.makeText(this, "Note name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(noteContent)) {
            Toast.makeText(this, "Note content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = databaseHelper.insertNote(noteName, noteContent);
        if (isInserted) {
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
        }
    }
}
