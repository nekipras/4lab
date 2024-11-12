package com.example.a4lab;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteNoteActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Spinner notesSpinner;
    private Button deleteButton;
    private HashMap<String, Integer> noteMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        databaseHelper = new DatabaseHelper(this);
        notesSpinner = findViewById(R.id.notesSpinner);
        deleteButton = findViewById(R.id.deleteButton);

        loadNotesIntoSpinner();

        deleteButton.setOnClickListener(v -> deleteSelectedNote());
    }

    private void loadNotesIntoSpinner() {
        ArrayList<String> noteNames = new ArrayList<>();
        noteMap = new HashMap<>();

        Cursor cursor = databaseHelper.getAllNotes();
        if (cursor.moveToFirst()) {
            do {
                int noteId = cursor.getInt(cursor.getColumnIndex("_id"));
                String noteName = cursor.getString(cursor.getColumnIndex("name"));

                noteMap.put(noteName, noteId);
                noteNames.add(noteName);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noteNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notesSpinner.setAdapter(adapter);
    }

    private void deleteSelectedNote() {
        String selectedNoteName = (String) notesSpinner.getSelectedItem();

        if (selectedNoteName != null && noteMap.containsKey(selectedNoteName)) {
            int noteId = noteMap.get(selectedNoteName);

            boolean isDeleted = databaseHelper.deleteNoteById(noteId);

            if (isDeleted) {
                Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                loadNotesIntoSpinner();
            } else {
                Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No note selected", Toast.LENGTH_SHORT).show();
        }
    }
}
