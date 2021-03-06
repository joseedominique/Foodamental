package com.foodamental.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.foodamental.model.ProductObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOUSSEF on 14/07/2016.
 */
public class ProductDB{
    public static final String PRODUCTDB_TABLE_NAME = "PRODUCT";
    //Product table Columns names
    public static final String PRODUCTDB_COLUMN_ID = "ID_PRODUCT";
    public static final String PRODUCTDB_COLUMN_NAME = "NAME";
    public static final String PRODUCTDB_COLUMN_BRAND = "BRAND";
    public static final String PRODUCTDB_COLUMN_IMAGE_URL= "IMAGE_URL";
    public static final String PRODUCTDB_COLUMN_CATEGORY = "CATEGORY";



    public ProductDB() {
        super();
    }





    // Adding new Product

    public void addProduct(ProductObject product) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCTDB_COLUMN_ID, product.getIdProduct()); // product id
        values.put(PRODUCTDB_COLUMN_NAME, product.getName()); // product name
        values.put(PRODUCTDB_COLUMN_BRAND, product.getBrand()); // product brand
        values.put(PRODUCTDB_COLUMN_IMAGE_URL, product.getImage()); // product image
        values.put(PRODUCTDB_COLUMN_CATEGORY, product.getCategory()); // product category

        // Insert Row
        db.insert(PRODUCTDB_TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

    }

    // Get one product with id
    public ProductObject getProduct(Integer id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.query(PRODUCTDB_TABLE_NAME, new String[]{PRODUCTDB_COLUMN_ID, PRODUCTDB_COLUMN_NAME,
                        PRODUCTDB_COLUMN_BRAND, PRODUCTDB_COLUMN_IMAGE_URL, PRODUCTDB_COLUMN_CATEGORY}, PRODUCTDB_COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProductObject product = new ProductObject(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        // Return user
        DatabaseManager.getInstance().closeDatabase();

        return product;
    }

    // Getting All Products

    public List<ProductObject> getALLProduct() {
        List<ProductObject> productList = new ArrayList<ProductObject>();
        // Select all Query
        String selectQuery = "SELECT * FROM " + PRODUCTDB_TABLE_NAME;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductObject product = new ProductObject(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        // return product list
        DatabaseManager.getInstance().closeDatabase();
        return productList;
    }

    //getting products Count
    public int getProductCount() {
        String countQuery = "SELECT * FROM" + PRODUCTDB_TABLE_NAME;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        DatabaseManager.getInstance().closeDatabase();
        return cursor.getCount();
    }

    //Updating a product
    public int updateProduct(ProductObject product) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCTDB_COLUMN_ID, product.getIdProduct()); // product id
        values.put(PRODUCTDB_COLUMN_NAME, product.getName()); // product name
        values.put(PRODUCTDB_COLUMN_BRAND, product.getBrand()); // product brand
        values.put(PRODUCTDB_COLUMN_IMAGE_URL, product.getImage()); // product image
        values.put(PRODUCTDB_COLUMN_CATEGORY, product.getCategory()); // product category
        int result = db.update(PRODUCTDB_TABLE_NAME, values, PRODUCTDB_COLUMN_ID + " = ?",
        new String[]{String.valueOf(product.getIdProduct())});
        //updating row
        DatabaseManager.getInstance().closeDatabase();

        return result;
    }

    // Deleting a product
    public void deleteProduct(ProductObject product) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        db.delete(PRODUCTDB_TABLE_NAME, PRODUCTDB_COLUMN_ID + " = ? ",
                new String[]{String.valueOf(product.getIdProduct())});
        DatabaseManager.getInstance().closeDatabase();
    }






}
