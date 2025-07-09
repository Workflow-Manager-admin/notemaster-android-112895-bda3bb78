package com.example.notesfrontend;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * PUBLIC_INTERFACE
 * Room database for storing notes locally.
 */
@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase INSTANCE;

    public abstract NoteDao noteDao();

    public static NotesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, "notes_db")
                    .allowMainThreadQueries() // For simplicity, allow on main thread (not recommended for production)
                    .build();
        }
        return INSTANCE;
    }
}
