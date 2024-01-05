package com.example.grilleproduitsactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE = "ProduitDB.db";
    private static final String TABLE = "Produits";
    public static final String COL_ID = "_id";
    public static final String COL_LABEL = "label";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PHOTO = "photo";

    public dbHandler(Context c, SQLiteDatabase.CursorFactory f) {
        super(c, DATABASE, f, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + " (" +
                COL_ID + " TEXT PRIMARY KEY," +
                COL_LABEL + " TEXT," +
                COL_DESCRIPTION + " TEXT," +
                COL_PHOTO + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addProduit(Produit produit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, produit.getId());
        values.put(COL_LABEL, produit.getLabel());
        values.put(COL_DESCRIPTION, produit.getDescription());
        values.put(COL_PHOTO, produit.getPhoto());
        db.insert(TABLE, null, values);
        db.close();
    }

    public Boolean deleteProduit(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE, COL_ID + "=?", new String[]{id});
        db.close();
        return deletedRows > 0;
    }

    public ArrayList<Produit> getProduits() {
        ArrayList<Produit> produits = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String label = cursor.getString(1);
                String description = cursor.getString(2);
                String photo = cursor.getString(3);
                Produit produit = new Produit(id, label, description, photo);
                produits.add(produit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return produits;
    }
}

