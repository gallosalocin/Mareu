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
import com.gallosalocin.mareu.model.Reunion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ReunionRecycleViewAdapter extends RecyclerView.Adapter<ReunionRecycleViewAdapter.ViewHolder> {

    private List<Reunion> reunionsList;
    private OnItemClickListener onItemClickListener;

    public ReunionRecycleViewAdapter(List<Reunion> reunions, OnItemClickListener onItemClickListener) {
        this.reunionsList = reunions;
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

        Reunion reunion = reunionsList.get(position);
        holder.topic.setText(reunion.getTopic());
        holder.time.setText(reunion.getTime());
        holder.room.setText(reunion.getRoom());
        holder.email.setText(reunion.getEmail());

    }

    @Override
    public int getItemCount() {
        return reunionsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_cardview_image)
        public ImageView roomColor;
        @BindView(R.id.tv_cardview_topic)
        public TextView topic;
        @BindView(R.id.tv_cardview_time)
        public TextView time;
        @BindView(R.id.tv_cardview_room)
        public TextView room;
        @BindView(R.id.tv_cardview_email)
        public TextView email;
        @BindView(R.id.iv_cardview_delete_btn)
        public ImageView deleteImage;
        @BindView(R.id.cardview_cardview)
        public CardView cardView;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;

            deleteImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_cardview_delete_btn:
                    onItemClickListener.onDeleteClick(getAdapterPosition());
                    Log.d(TAG, "onDeleteClick: clicked -> " + getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
}