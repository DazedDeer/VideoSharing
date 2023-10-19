package com.example.videosharing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videosharing.channelModel.Ittems;
import com.example.videosharing.model.Items;
import com.squareup.picasso.Picasso;

public class MyChannelAdapter extends RecyclerView.Adapter<MyChannelViewHolder> {

    public MyChannelAdapter(Context context, Ittems[] items) {
        this.context = context;
        this.items = items;
    }

    Context context;
    Ittems[] items;

    @NonNull
    @Override
    public MyChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item, parent, false);
        return new MyChannelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChannelViewHolder holder, int position) {

        holder.chanTitle.setText(items[position].getSnippet().getTitle());
        holder.chanDesc.setText(items[position].getSnippet().getDescription());
        holder.chanSubs.setText(items[position].getStatistics().getSubscriberCount());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}

