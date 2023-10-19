package com.example.videosharing;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyChannelViewHolder extends RecyclerView.ViewHolder {
    public TextView chanTitle;
    public TextView chanDesc;
    public TextView chanSubs;

    public MyChannelViewHolder(@NonNull View itemView) {
        super(itemView);

        chanTitle = itemView.findViewById(R.id.chan_title);
        chanDesc = itemView.findViewById(R.id.chan_desc);
        chanSubs = itemView.findViewById(R.id.chan_subs);
    }
}
