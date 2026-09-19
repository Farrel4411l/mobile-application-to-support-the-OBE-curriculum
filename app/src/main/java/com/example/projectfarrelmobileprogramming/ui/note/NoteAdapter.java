package com.example.projectfarrelmobileprogramming.ui.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private OnNoteDeleteListener deleteListener;

    public interface OnNoteDeleteListener {
        void onDeleteClick(Note note);
    }

    public NoteAdapter(List<Note> notes, OnNoteDeleteListener deleteListener) {
        this.notes = notes;
        this.deleteListener = deleteListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvNoteText.setText(note.getText());
        holder.tvNoteTimestamp.setText(note.getTimestamp());
        holder.btnDeleteNote.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteText, tvNoteTimestamp;
        ImageButton btnDeleteNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteText = itemView.findViewById(R.id.tvNoteText);
            tvNoteTimestamp = itemView.findViewById(R.id.tvNoteTimestamp);
            btnDeleteNote = itemView.findViewById(R.id.btnDeleteNote);
        }
    }
}
