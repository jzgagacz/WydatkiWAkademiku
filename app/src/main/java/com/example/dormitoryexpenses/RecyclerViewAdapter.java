package com.example.dormitoryexpenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ArrayList<String>> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerViewAdapter(Context context, ArrayList<ArrayList<String>> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String expanse = mData.get(1).get(position);
        String date = mData.get(3).get(position);
        String uname = mData.get(4).get(position);
        String money = mData.get(5).get(position);
        money =money.substring(0,money.length()-2)+","+money.substring(money.length()-2, money.length()) + "zł";
        holder.expanseTextView.setText(expanse);
        holder.dateTextView.setText(date);
        holder.unameTextView.setText(uname);
        holder.moneyTextView.setText(money);
        }

    @Override
    public int getItemCount() {
        return mData.get(0).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView expanseTextView, dateTextView, unameTextView, moneyTextView;

        ViewHolder(View itemView) {
            super(itemView);
            expanseTextView = itemView.findViewById(R.id.tfExpanse);
            dateTextView = itemView.findViewById(R.id.tfShowDate);
            unameTextView = itemView.findViewById(R.id.tfShowUsername);
            moneyTextView = itemView.findViewById(R.id.tfShowMoney);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return mData.get(0).get(id);
    }

    public void updateData (ArrayList<ArrayList<String>> newData){
        mData = newData;
        notifyDataSetChanged();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
