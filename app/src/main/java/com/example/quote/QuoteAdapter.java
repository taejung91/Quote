package com.example.quote;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> implements OnQuoteItemClickListener {

    ArrayList<Quote> items = new ArrayList<>();
    OnQuoteItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.quote_list, viewGroup, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Quote item = items.get(position);
        viewHolder.setItem(item);



    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(Quote item) {
        items.add(item);
    }

    public void setItems(ArrayList<Quote> items) {
        this.items = items;
    }

    public Quote getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Quote item) {
        items.set(position, item);
    }


    public void setOnQuoteItemClickListener(OnQuoteItemClickListener listener) {
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

        public ViewHolder(View itemView, final OnQuoteItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            button = itemView.findViewById(R.id.button1);

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



            // 즐겨찾기 버튼
            //
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


        public void setItem(Quote item) {
            String number = query_C_Quote3(item);
          //  String id = query_C_Quote4(item);

            // id
            if (number.equals(item.getNumber())) {
                Log.d("즐찾1", number);
                button.setImageResource(R.drawable.favorite2);
            }else {
                Log.d("즐찾2", number);
                button.setImageResource(R.drawable.favorite);
            }

            imageView.setImageResource(item.getImage());
            fontChange(item.getFont());
            textView1.setText(item.getQuote() + "\n" + "\n" + "- " + item.getWriter());

        }
/*
        public String query_C_Quote3(Quote item) {
            String number2 = "";
            try {
                String uriString = "content://com.example.quote/c_quote";
                Uri uri = new Uri.Builder().build().parse(uriString);
                String[] columns = new String[]{"number"};
                String selection = "number = ? AND id = ?";
                String[] selectionArgs = new String[]{item.getNumber(), item.getId()};
                Context context;
                context = itemView.getContext();

                Cursor cursor = context.getContentResolver().query(uri, columns, selection, selectionArgs, null);

                while (cursor.moveToNext()) {

                    number2 = cursor.getString(cursor.getColumnIndex(columns[0]));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return number2;
        }*/
        public String query_C_Quote3(Quote item) {
            String result = "";
            try {
                result = new Mysql_Db5().execute(item.getId(), item.getNumber(), "select").get();
                Log.d("즐찾DB값", result);
            } catch (Exception e) {

            }
            return result;
        }


/*

        public String query_C_Quote4(Quote item) {
            String id2 = "";
            try {
                String uriString = "content://com.example.quote/c_quote";
                Uri uri = new Uri.Builder().build().parse(uriString);
                String[] columns = new String[]{"id"};
                String selection = "id = ?";
                String[] selectionArgs = new String[]{item.getId()};
                Context context;
                context = itemView.getContext();

                Cursor cursor = context.getContentResolver().query(uri, columns, selection, selectionArgs, null);

                while (cursor.moveToNext()) {

                    id2 = cursor.getString(cursor.getColumnIndex(columns[0]));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return id2;
        }

            // 아이디 추가
    /*    public Quote query_C_Quote3(Quote item) {
            String number2 = "";
            String id2 = "";
            Quote quote2 = new Quote();
            try {
                String uriString = "content://com.example.quote/c_quote";
                Uri uri = new Uri.Builder().build().parse(uriString);
                String[] columns = new String[]{"number, id"};
                String selection = "number = ? AND id = ?";
                String[] selectionArgs = new String[]{item.getNumber(), String.valueOf(item.getId())};
                Context context;
                context = itemView.getContext();

                Cursor cursor = context.getContentResolver().query(uri, columns, selection, selectionArgs, null);

                while (cursor.moveToNext()) {

                    number2 = cursor.getString(cursor.getColumnIndex(columns[0]));
                    id2 = cursor.getString(cursor.getColumnIndex(columns[1]));

                    quote2.setNumber(number2);
                    quote2.setId(id2);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return quote2;
        }*/

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
