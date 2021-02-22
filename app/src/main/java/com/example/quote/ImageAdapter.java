package com.example.quote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> implements OnQuoteItemClickListener3{

    ArrayList<Image> items = new ArrayList<>();
    OnQuoteItemClickListener3 listener;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.image_select, viewGroup, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Image item = items.get(position);
        viewHolder.setItem(item);
    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(Image item) {
        items.add(item);
    }

    public void setItems(ArrayList<Image> items) {
        this.items = items;
    }

    public Image getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Image item) {
        items.set(position, item);
    }

    public ArrayList<Image> getItems() {
        return items;
    }

    public void setOnQuoteItemClickListener(OnQuoteItemClickListener3 listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ImageAdapter.ViewHolder viewHolder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(viewHolder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        RelativeLayout layout;
        final int ran[] = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};


        public ViewHolder(View itemView, final OnQuoteItemClickListener3 listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        if (listener != null) {
                            listener.onItemClick(ViewHolder.this, v, position);

                        }

                    }

                }
            });

        }

        public void setItem(Image item) {

            imageView.setImageResource(Integer.parseInt(item.getImage()));

        }
    }
}