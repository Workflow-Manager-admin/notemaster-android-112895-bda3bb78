package com.example.notesfrontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * RecyclerView Adapter for the notes list.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public interface OnNoteListener {
        void onNoteClick(Note note);
        void onNoteDelete(Note note);
    }
    private List<Note> notes;
    private final OnNoteListener listener;

    public NotesAdapter(List<Note> notes, OnNoteListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.title);
        holder.snippet.setText(trimSnippet(note.content));
        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
        holder.deleteButton.setOnClickListener(v -> listener.onNoteDelete(note));
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    private String trimSnippet(String content) {
        if (content == null) return "";
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, snippet;
        ImageButton deleteButton;
        NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            snippet = itemView.findViewById(R.id.note_snippet);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
