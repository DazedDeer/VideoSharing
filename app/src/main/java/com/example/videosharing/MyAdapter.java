package com.example.videosharing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videosharing.model.Items;
import com.squareup.picasso.Picasso;

// The adapter for listing channel video information
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    // constructor
    public MyAdapter(Context context, Items[] items) {
        this.context = context;
        this.items = items;
    }

    Context context;
    Items[] items;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item views
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item , parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set the video's information (title, thumbnail)
        holder.textView.setText(items[position].getSnippet().getTitle());
        Picasso.get().load(items[position].getSnippet().getThumbnails().getHigh().getUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When a video is clicked on, save it's information
                Intent intent = new Intent(view.getContext(), PlaySelected.class);
                intent.putExtra("id", items[position].getId().getVideoId());
                intent.putExtra("title", items[position].getSnippet().getTitle());

                // Video detail activity for the clicked on video
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
