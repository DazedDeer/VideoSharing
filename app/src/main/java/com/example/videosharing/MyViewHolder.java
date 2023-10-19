package com.example.videosharing;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Holds channel video information
public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Views for the channel video title and thumbnail
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
    }
}
