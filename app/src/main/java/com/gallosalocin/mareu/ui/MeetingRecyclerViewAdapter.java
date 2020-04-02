package com.gallosalocin.mareu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.CardviewMainBinding;
import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> meetingsList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    MeetingRecyclerViewAdapter(List<Meeting> meetings, OnItemClickListener onItemClickListener, Context context) {
        this.meetingsList = meetings;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewMainBinding binding = CardviewMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false);
        return new ViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = meetingsList.get(position);
        String descriptionItems = context.getString(R.string.description_items, meeting.getTopic(), meeting.getTime()
                , meeting.getRoom());

        holder.roomColor.setImageResource(meeting.getRoomColor());
        holder.description.setText(descriptionItems);
        holder.email.setText(meeting.getEmail());

    }

    @Override
    public int getItemCount() {
        return meetingsList.size();
    }

    void setMeetings(List<Meeting> meetings) {
        meetingsList = meetings;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView roomColor;
        private TextView description;
        private TextView email;
        private OnItemClickListener onItemClickListener;

        ViewHolder(@NonNull CardviewMainBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.onItemClickListener = onItemClickListener;

            roomColor = binding.ivCardviewColor;
            description = binding.tvCardviewDescription;
            email = binding.tvCardviewEmail;

            binding.ivCardviewDeleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_cardview_delete_btn) {
                onItemClickListener.onDeleteClick(getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
}