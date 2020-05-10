package com.example.dormitoryexpenses;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_USERS = "users_table";
    private static final String UCOL2 = "USERNAME";
    private static final String UCOL3 = "NAME";
    private static final String UCOL4 = "SURNAME";
    private static final String UCOL5 = "SALT";
    private static final String UCOL6 = "HASH";

    private static final String TABLE_EXPENSES = "expenses_table";
    private static final String ECOL0 = "ID";
    private static final String ECOL1 = "NAME";
    private static final String ECOL2 = "DESCRIPTION";
    private static final String ECOL3 = "DATE";
    private static final String ECOL4 = "USERNAME";
    private static final String ECOL5 = "VALUE";


    public DataBaseHelper(Context context) {
        super(context, TABLE_USERS, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" + UCOL2 + " TEXT NOT NULL PRIMARY KEY, " + UCOL3 + " TEXT, " + UCOL4 + " TEXT, " + UCOL5 + " TEXT, " + UCOL6 + " TEXT)";
        String createTable2 = "CREATE TABLE " + TABLE_EXPENSES + " (" + ECOL0 + " INTEGER PRIMARY KEY, " + ECOL1 + " TEXT, " + ECOL2 + " TEXT, " + ECOL3 + " DATETIME, " + ECOL4 + " TEXT, " + ECOL5 + " INTEGER)" ;
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public boolean addUser(String uname, String name, String sname, String salt, String hash){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UCOL2, uname);
        cv.put(UCOL3, name);
        cv.put(UCOL4, sname);
        cv.put(UCOL5, salt);
        cv.put(UCOL6, hash);

        long res = db.insert(TABLE_USERS, null, cv);

        return res != -1;
    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + UCOL2 +  " = '" + username + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean addExpense(String expanse,String description,String date,String user, int zl, int gr){
        int money = 100*zl + gr;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ECOL1, expanse);
        cv.put(ECOL2, description);
        cv.put(ECOL3, date);
        cv.put(ECOL4, user);
        cv.put(ECOL5, money);
        long res = db.insert(TABLE_EXPENSES, null, cv);
        return res != -1;
    }


    public Cursor getExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXPENSES;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + ECOL0 +  " = " + id;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean deleteExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ECOL0 + " = " + id;
        long res = db.delete(TABLE_EXPENSES, whereClause, null);
        return res!=-1;
    }

    public void dropDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSES);
        onCreate(db);
    }
}
