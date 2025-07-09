package com.example.notesfrontend;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * PUBLIC_INTERFACE
 * Activity for creating or editing a note.
 */
public class NoteDetailActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private FloatingActionButton fabSave;
    private NotesDatabase db;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        etTitle = findViewById(R.id.et_note_title);
        etContent = findViewById(R.id.et_note_content);
        fabSave = findViewById(R.id.fab_save);

        db = NotesDatabase.getInstance(this);

        int noteId = getIntent().getIntExtra("note_id", -1);
        if (noteId != -1) {
            note = db.noteDao().getById(noteId);
            if (note != null) {
                etTitle.setText(note.title);
                etContent.setText(note.content);
            }
        } else {
            note = new Note();
        }

        fabSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String titleInput = etTitle.getText().toString().trim();
        String contentInput = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(titleInput)) {
            etTitle.setError("Title required");
            etTitle.requestFocus();
            return;
        }

        note.title = titleInput;
        note.content = contentInput;
        note.updatedAt = System.currentTimeMillis();
        if (note.id == 0) {
            note.createdAt = note.updatedAt;
            db.noteDao().insert(note);
            Toast.makeText(this, "Note created", Toast.LENGTH_SHORT).show();
        } else {
            db.noteDao().update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);
        finish();
    }
}
