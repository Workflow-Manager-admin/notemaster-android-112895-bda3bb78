package com.example.notesfrontend;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Main activity: displays list of notes, allows searching, creating, editing, and deleting notes.
 */
public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private NotesAdapter notesAdapter;
    private NotesDatabase db;
    private RecyclerView recyclerView;
    private List<Note> notesList;
    private static final int REQUEST_ADD_NOTE = 1;
    private static final int REQUEST_EDIT_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		// Initialize database
        db = NotesDatabase.getInstance(this);

        // Initialize RecyclerView + Adapter
        recyclerView = findViewById(R.id.rv_notes);
        notesList = db.noteDao().getAll();
        notesAdapter = new NotesAdapter(notesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notesAdapter);

        // Floating Action Button - add
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
            startActivityForResult(intent, REQUEST_ADD_NOTE);
        });

        // Search functionality
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search notes...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterNotes(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterNotes(newText);
                return true;
            }
        });
    }

    private void filterNotes(String query) {
        List<Note> filtered = db.noteDao().searchNotes("%" + query + "%");
        notesAdapter.setNotes(filtered);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshNotes();
    }

    private void refreshNotes() {
        notesList = db.noteDao().getAll();
        notesAdapter.setNotes(notesList);
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
        intent.putExtra("note_id", note.id);
        startActivityForResult(intent, REQUEST_EDIT_NOTE);
    }

    @Override
    public void onNoteDelete(Note note) {
        db.noteDao().delete(note);
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        refreshNotes();
    }
}
