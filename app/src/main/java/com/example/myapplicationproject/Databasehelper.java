package com.example.myapplicationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.security.identity.EphemeralPublicKeyNotFoundException;

public class Databasehelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Test_DB";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_REGISTER = "register";
    public static final String TABLE_ACADEMY = "academy";

    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_PHONE = "phone";

    public static final String COL_ACADEMY_NAME = "academy_name";
    public static final String COL_ACADEMY_PHONE = "academy_phone";
    public static final String COL_ACADEMY_LOCATION = "academy_location";
    public static final String COL_PRODUCT_IMAGE_URI = "productImageUri";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACADEMY); // Drop academy table as well

        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_PHONE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ACADEMY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ACADEMY_NAME + " TEXT, " +
                COL_ACADEMY_PHONE + " TEXT, " +
                COL_ACADEMY_LOCATION + " TEXT, " +
                COL_PRODUCT_IMAGE_URI + " BLOB)"); // Add the image URI column
    }

    public boolean insertUser(String username, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_PHONE, phone);

        long result = db.insert(TABLE_REGISTER, null, contentValues);
        return result != -1;
    }

    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void insertAcademy(String name, String phone, String location, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ACADEMY_NAME, name);
        values.put(COL_ACADEMY_PHONE, phone);
        values.put(COL_ACADEMY_LOCATION, location);
        values.put(COL_PRODUCT_IMAGE_URI, imageByteArray);
        db.insert(TABLE_ACADEMY, null, values);
        db.close();
    }

    public Cursor getAllAcademies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACADEMY, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACADEMY + " WHERE " + COL_ACADEMY_NAME + " = ?", new String[]{productName});
    }



    public void updateAcademy(int productId, String productName, String phone, String locatin, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ACADEMY_NAME, productName);
        values.put(COL_ACADEMY_PHONE, phone);
        values.put(COL_ACADEMY_LOCATION, locatin);
        values.put(COL_PRODUCT_IMAGE_URI, productImageByteArray);

        db.update(TABLE_ACADEMY, values, COL_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACADEMY, COL_ACADEMY_NAME + " = ?", new String[]{productName});
        db.close();
    }
}
