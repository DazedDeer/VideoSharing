package com.example.videosharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videosharing.model.Items;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public MyAdapter(Context context, Items[] items) {
        this.context = context;
        this.items = items;
    }

    Context context;
    Items[] items;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item , parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView.setText(items[position].getSnippet().getTitle());
        Picasso.get().load(items[position].getSnippet().getThumbnails().getHigh().getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
