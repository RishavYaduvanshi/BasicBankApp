package com.rishavyaduvanshi.banking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserDataActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String phonenumber;
    Double newbalance;
    TextView name, phoneNumber, email, account_no, ifsc_code, balance;
    Button transfer_button;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);


        getSupportActionBar().setTitle("User Details");

        name = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.userphonenumber);
        email = findViewById(R.id.email);
        account_no = findViewById(R.id.account_no);
        ifsc_code = findViewById(R.id.ifsc_code);
        balance = findViewById(R.id.balance);
        transfer_button = findViewById(R.id.transfer_button);

        progressDialog = new ProgressDialog(UserDataActivity.this);
        progressDialog.setTitle("Loading data...");
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            phonenumber = bundle.getString("phonenumber");
            showData(phonenumber);
        }

        transfer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });
    }

    private void showData(String phonenumber) {
        Cursor cursor = new DatabaseHelper(this).readparticulardata(phonenumber);
        while(cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            newbalance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(newbalance);

            progressDialog.dismiss();

            phoneNumber.setText("Mobile Number : "+cursor.getString(0));
            name.setText("Name : "+cursor.getString(1));
            email.setText("Email Id : "+cursor.getString(3));
            balance.setText("Current Balance : "+price);
            account_no.setText("Account No : "+cursor.getString(4));
            ifsc_code.setText("IFSC Code : "+cursor.getString(5));
        }

    }

    private void enterAmount() {
        final MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(UserDataActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.transfermoney, null);

        mBuilder.setView(mView).setCancelable(false);


        final EditText mAmount = (EditText) mView.findViewById(R.id.enter_money);

        mBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                transactionCancel();
            }
        });

        dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmount.getText().toString().isEmpty()){
                    mAmount.setError("Amount can't be empty");
                }else if(Double.parseDouble(mAmount.getText().toString()) > newbalance){
                    mAmount.setError("Your account don't have enough balance");
                }else{
                    Intent intent = new Intent(UserDataActivity.this, SendTouserActivity.class);
                    intent.putExtra("phonenumber", phoneNumber.getText().toString().substring(16));
                    intent.putExtra("name", name.getText().toString().substring(7));
                    intent.putExtra("currentamount", newbalance.toString());
                    intent.putExtra("transferamount", mAmount.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void transactionCancel() {
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(UserDataActivity.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
                        String date = simpleDateFormat.format(calendar.getTime());

                        new DatabaseHelper(UserDataActivity.this).insertTransferData(date, name.getText().toString(), "Not selected", "0", "Failed");
                        Toast.makeText(UserDataActivity.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterAmount();
            }
        });
        AlertDialog alertexit = builder_exitbutton.create();
        alertexit.show();
    }

}