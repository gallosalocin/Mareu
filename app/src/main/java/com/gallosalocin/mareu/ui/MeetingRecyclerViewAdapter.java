package com.gallosalocin.mareu.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> meetingsList;
    private OnItemClickListener onItemClickListener;

    MeetingRecyclerViewAdapter(List<Meeting> meetings, OnItemClickListener onItemClickListener) {
        this.meetingsList = meetings;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Meeting meeting = meetingsList.get(position);
        holder.roomColor.setImageResource(meeting.getRoomColor());
        holder.topic.setText(meeting.getTopic());
        holder.time.setText(meeting.getTime());
        holder.room.setText(meeting.getRoom());
        holder.email.setText(meeting.getEmail());

    }

    @Override
    public int getItemCount() {
        return meetingsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_cardview_image)
        ImageView roomColor;
        @BindView(R.id.tv_cardview_topic)
        TextView topic;
        @BindView(R.id.tv_cardview_time)
        TextView time;
        @BindView(R.id.tv_cardview_room)
        TextView room;
        @BindView(R.id.tv_cardview_email)
        TextView email;
        @BindView(R.id.iv_cardview_delete_btn)
        ImageView deleteImage;
        @BindView(R.id.cardview_meeting)
        CardView cardView;

        OnItemClickListener onItemClickListener;

        ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;

            deleteImage.setOnClickListener(this);
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