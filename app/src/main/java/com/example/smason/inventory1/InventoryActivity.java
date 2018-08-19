package com.example.smason.inventory1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.smason.inventory1.InventoryContract.Product;

public class InventoryActivity extends AppCompatActivity {

    //DB helper to use the database
    private InventoryDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_activity);

        mDbHelper = new InventoryDBHelper (this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
        //inserts duplicate hardcoded data only after second start
        insertProduct();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase inventorydb = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Product._ID,
                Product.COLUMN_PRODUCT_NAME,
                Product.COLUMN_PRODUCT_PRICE,
                Product.COLUMN_PRODUCT_QUANTITY,
                Product.COLUMN_PRODUCT_SUPP_NAME,
                Product.COLUMN_PRODUCT_SUPP_PHONE };

        // Perform a query on the product table
        Cursor cursor = inventorydb.query(
                Product.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = findViewById(R.id.text_view_product);

        try {

            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The product table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(Product._ID + " - " +
                    Product.COLUMN_PRODUCT_NAME + " - " +
                    Product.COLUMN_PRODUCT_PRICE + " - " +
                    Product.COLUMN_PRODUCT_QUANTITY + " - " +
                    Product.COLUMN_PRODUCT_SUPP_NAME + " - " +
                    Product.COLUMN_PRODUCT_SUPP_PHONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Product._ID);
            int nameColumnIndex = cursor.getColumnIndex(Product.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(Product.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(Product.COLUMN_PRODUCT_QUANTITY);
            int suppNameColumnIndex = cursor.getColumnIndex(Product.COLUMN_PRODUCT_SUPP_NAME);
            int suppPhoneColumnIndex = cursor.getColumnIndex(Product.COLUMN_PRODUCT_SUPP_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSuppName = cursor.getString(suppNameColumnIndex);
                String currentSuppPhone = cursor.getString(suppPhoneColumnIndex);

                // Display column data of the current row in the cursor to the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSuppName + " - " +
                        currentSuppPhone));
            }
        } finally {
            // Always close the cursor
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded data into the database. For debugging purposes only and
     * does run on application startup adding duplicate information each time. The only difference
     * in data is the _ID number.
     */
    private void insertProduct() {
        // Gets the database in write mode
        SQLiteDatabase inventorydb = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and the products attributes are the values.
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_PRODUCT_NAME, "Ethernet Cable");
        values.put(Product.COLUMN_PRODUCT_PRICE, 5);
        values.put(Product.COLUMN_PRODUCT_QUANTITY, 10);
        values.put(Product.COLUMN_PRODUCT_SUPP_NAME, "MediaBridge");
        values.put(Product.COLUMN_PRODUCT_SUPP_PHONE, "555-555-1234");

        // Insert a new row in the database, returning the ID of that new row.
        // The first argument for inventorydb.insert() is the products table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for the product.
        long newRowId = inventorydb.insert(Product.TABLE_NAME, null, values);
    }
}
