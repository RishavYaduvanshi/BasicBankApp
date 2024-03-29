package com.rishavyaduvanshi.banking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "user_list";
    List<Model> modelList_showlist = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    UserListAdapter adapter;
    String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable =new ColorDrawable(Color.parseColor("#0F9D58"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Users");

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#2E8B57"));



        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        showData();
    }

    private void showData() {
        Log.d(TAG, "showData: called");
        modelList_showlist.clear();
        Log.d(TAG, "showData: modellist cleared");
        Cursor cursor = new DatabaseHelper(this).readalldata();
        while(cursor.moveToNext()){

            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            Model model = new Model(cursor.getString(0), cursor.getString(1), price);
            modelList_showlist.add(model);
        }
        Log.d(TAG, "showData: outside cursor");

        adapter = new UserListAdapter(UserListActivity.this, modelList_showlist);
        mRecyclerView.setAdapter(adapter);

    }

    public void nextActivity(int position) {
        Log.d(TAG, "nextActivity: called");
        phonenumber = modelList_showlist.get(position).getPhoneno();
        Intent intent = new Intent(UserListActivity.this, UserDataActivity.class);
        intent.putExtra("phonenumber",phonenumber);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_history){
            startActivity(new Intent(UserListActivity.this, HistoryListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}