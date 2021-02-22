package com.example.quote;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
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

public class QuoteAdapter2 extends RecyclerView.Adapter<QuoteAdapter2.ViewHolder> implements OnQuoteItemClickListener2 {

    ArrayList<Quote_Db> items = new ArrayList<>();
    OnQuoteItemClickListener2 listener;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.quote_favorite, viewGroup, false);
        return new QuoteAdapter2.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Quote_Db item = items.get(position);
        viewHolder.setItem(item);

    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(Quote_Db item) {
        items.add(item);
    }

    public void setItems(ArrayList<Quote_Db> items) {
        this.items = items;
    }

    public Quote_Db getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Quote_Db item) {
        items.set(position, item);
    }

    public ArrayList<Quote_Db> getItems(){return items;}


    public void setOnQuoteItemClickListener(OnQuoteItemClickListener2 listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder viewHolder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(viewHolder, view, position);
        }
    }


     static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        ImageButton button;
        ImageButton button1;
       // ImageButton button2;
        RelativeLayout layout;
         final int ran[] = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};


        public ViewHolder(View itemView, final OnQuoteItemClickListener2 listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            button = itemView.findViewById(R.id.button1);
            button1 = itemView.findViewById(R.id.button);
          //  button2 = itemView.findViewById(R.id.button2);
            layout = itemView.findViewById(R.id.layout);

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
/*
            button2.setOnClickListener(new View.OnClickListener() {
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
*/
            button1.setOnClickListener(new View.OnClickListener() {
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

            // 즐겨찾기 버튼
            button.setOnClickListener(new View.OnClickListener() {
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

        public void setItem(Quote_Db item) {

            imageView.setImageResource(Integer.parseInt(item.getImage()));
            fontChange(item.getFont());
           textView1.setText(item.getQuote() + "\n" + "\n" + "- " + item.getWriter());
           button.setImageResource(R.drawable.favorite2);
        }

         public void fontChange(int font) {
             Typeface typeface;
             switch (font) {
                 case R.id.font1:
                     typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/font1.ttf");
                     textView1.setTypeface(typeface);
                     textView1.setTextSize(18);
                     break;
                 case R.id.font2:
                     typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/font2.ttf");
                     textView1.setTypeface(typeface);
                     break;
                 case R.id.font3:
                     typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/font3.ttf");
                     textView1.setTypeface(typeface);
                     break;
             }

         }


    }

}
