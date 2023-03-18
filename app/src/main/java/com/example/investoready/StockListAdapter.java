package com.example.investoready;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investoready.Database.StockInfo;

import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.StockViewHolder>{
    private String[] symbol;
    private double[] change, price;

    private OnStockListener onStockListener;

    public StockListAdapter(List<StockInfo> stockList, long n, OnStockListener onStockListener)
    {
        this.onStockListener = onStockListener;
        int i;
        String[] temps = new String[15];
        double[] tempc = new double[15];
        double[] tempp = new double[15];

        Log.d("Value", "StockListAdapter: "+stockList.get(0).getSymbol());
        for(i=0;i<n;i++)
        {
            temps[i] = stockList.get(i).getSymbol();
            tempc[i] = stockList.get(i).getChange();
            tempp[i] = stockList.get(i).getPrice();
        }
        this.symbol = temps;
        this.change = tempc;
        this.price = tempp;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new StockViewHolder(view, onStockListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        String name1 = symbol[position];
        String symbol1 = symbol[position];
        String temp = String.valueOf(price[position]);
        String price1 = "₹ " + temp;
        String change1 = String.valueOf(change[position]);
        double value = change[position];
        if(value<0)
            holder.c_change.setTextColor(Color.parseColor("#ff0000"));
        else
            holder.c_change.setTextColor(Color.parseColor("#00ff7f"));
        holder.c_name.setText(name1);
        holder.c_symbol.setText(symbol1);
        holder.c_price.setText(price1);
        holder.c_change.setText(change1);
    }

    @Override
    public int getItemCount() {
        return symbol.length;
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView c_name;
        TextView c_symbol;
        TextView c_price;
        TextView c_change;
        OnStockListener onStockListener;
        public StockViewHolder(@NonNull View itemView, OnStockListener onStockListener) {
            super(itemView);
            c_name = itemView.findViewById(R.id.name);
            c_symbol = itemView.findViewById(R.id.symbol);
            c_price = itemView.findViewById(R.id.price);
            c_change = itemView.findViewById(R.id.change);
            this.onStockListener = onStockListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStockListener.onStockClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnStockListener{
        void onStockClick(int position);
    }
}
