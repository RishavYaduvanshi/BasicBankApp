package com.rishavyaduvanshi.banking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryListActivity extends AppCompatActivity {

    List<Model> modelList_historylist = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    HistoryAdapter adapter;

    TextView history_empty;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable =new ColorDrawable(Color.parseColor("#000"));
//        actionBar.setBackgroundDrawable(colorDrawable);
//        actionBar.setTitle("Transaction Status");
        getSupportActionBar().hide();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#B341F1"));


        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        history_empty = findViewById(R.id.empty_text);
        button = findViewById(R.id.home);

        showData();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showData() {
        modelList_historylist.clear();
        Cursor cursor = new DatabaseHelper(this).readtransferdata();

        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(4);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            Model model = new Model(cursor.getString(2), cursor.getString(3), price, cursor.getString(1), cursor.getString(5));
            modelList_historylist.add(model);
        }

        adapter = new HistoryAdapter(HistoryListActivity.this, modelList_historylist);
        mRecyclerView.setAdapter(adapter);

        if(modelList_historylist.size() == 0){
            history_empty.setVisibility(View.VISIBLE);
        }

    }

}