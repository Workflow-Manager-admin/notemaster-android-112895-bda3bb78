package com.example.notesfrontend;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Data access object for Note entity.
 */
@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY updatedAt DESC")
    List<Note> getAll();

    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Note WHERE id = :id LIMIT 1")
    Note getById(int id);

    @Query("SELECT * FROM Note WHERE title LIKE :query OR content LIKE :query ORDER BY updatedAt DESC")
    List<Note> searchNotes(String query);
}
