package com.example.notesfrontend;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

/**
 * PUBLIC_INTERFACE
 * Note entity model for Room database.
 */
@Entity
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String content;
    public long createdAt;
    public long updatedAt;
}
