package com.example.dormitoryexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private DataBaseHelper dbh;

    private RecyclerViewAdapter adapter;

    private ArrayList<ArrayList<String>> listData;

    private RecyclerView list;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        list = findViewById(R.id.list);
        btnAdd = findViewById(R.id.btnAdd);
        dbh = new DataBaseHelper(this);


        showList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDataActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        makeListData();
        adapter.updateData(listData);
    }

    private void showList() {
        makeListData();
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(ListDataActivity.this, listData);
        adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ListDataActivity.this, ExpenseActivity.class);
                intent.putExtra("id", adapter.getItem(position));
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);
    }

    private void makeListData(){
        listData = new ArrayList<>();
        Cursor data = dbh.getExpenses();
        for (int i = 0; i<6;i++){
            listData.add(new ArrayList<String>());
        }
        while(data.moveToNext()){
            listData.get(0).add(data.getString(0));
            listData.get(1).add(data.getString(1));
            listData.get(2).add(data.getString(2));
            listData.get(3).add(data.getString(3));
            listData.get(4).add(data.getString(4));
            listData.get(5).add(data.getString(5));
        }
    }
}
