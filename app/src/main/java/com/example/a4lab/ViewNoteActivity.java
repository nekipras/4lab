package com.example.a4lab;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        String noteName = getIntent().getStringExtra("noteName");
        String noteContent = getIntent().getStringExtra("noteContent");

        TextView nameTextView = findViewById(R.id.noteNameTextView);
        TextView contentTextView = findViewById(R.id.noteContentTextView);

        nameTextView.setText(noteName);
        contentTextView.setText(noteContent);
    }
}