package com.rishavyaduvanshi.banking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private String TABLE_NAME = "user_table";
    private String TABLE_NAME1 = "transfers_table";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (PHONENUMBER INTEGER PRIMARY KEY ,NAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + TABLE_NAME1 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");
        db.execSQL("insert into user_table values(6104567890,'Arun',4864.00,'arun@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(2346226641,'Bablu',4582.00,'bablu@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(3456789012,'David',3452.05,'david@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(4574190123,'Dev',7426.09,'dev@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(5678901234,'Rohan',723650.02,'rohan@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(6764462345,'Ritu',12364.15,'ritu@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(7890123456,'Gaurav',5936.00,'gaurav@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(6922347567,'Shubham',6325.22,'shubham@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(9012345678,'Sonu',4398.46,'sonu@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
        db.execSQL("insert into user_table values(3113867809,'Ranjan',8273.90,'ranjan@gmail.com','XXXXXXXXXXXX1234','ABC01234567')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        return cursor;
    }

    public Cursor readparticulardata(String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table except select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user_table set balance = " + amount + " where phonenumber = " +phonenumber);
    }

    public Cursor readtransferdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table", null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(TABLE_NAME1, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
