package com.example.quote;

import android.view.View;

public interface OnQuoteItemClickListener {
    public void onItemClick(QuoteAdapter.ViewHolder viewHolder, View view, int position);
}
