package com.example.a4lab;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        loadNotes();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = notesList.get(position);

            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("noteName", selectedNote.getName());
            intent.putExtra("noteContent", selectedNote.getContent());
            startActivity(intent);
        });
    }

    private void loadNotes() {
        notesList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllNotes();

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("_id");
            int nameColumnIndex = cursor.getColumnIndex("name");
            int contentColumnIndex = cursor.getColumnIndex("content");

            if (idColumnIndex != -1 && nameColumnIndex != -1 && contentColumnIndex != -1) {
                do {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String content = cursor.getString(contentColumnIndex);
                    Note note = new Note(id, name, content);
                    notesList.add(note);
                } while (cursor.moveToNext());
            } else {
                Log.e("MainActivity", "Columns not found in cursor");
                Toast.makeText(this, "Error loading notes: columns not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("MainActivity", "No notes found in database");
            Toast.makeText(this, "Error loading notes: no data", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.delete_note) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}
