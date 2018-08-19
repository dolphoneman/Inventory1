package com.example.smason.inventory1;

import android.provider.BaseColumns;

public final class InventoryContract {

    private InventoryContract() {}

    /**
     * Inner class that defines constant values for the product database table.
     * Each entry in the table represents a single pet.
     */
    public static final class Product implements BaseColumns {

        //Name of database table for inventory
        public final static String TABLE_NAME = "product";

        //column names
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRODUCT_NAME ="name";

        public final static String COLUMN_PRODUCT_PRICE = "price";

        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

        public final static String COLUMN_PRODUCT_SUPP_NAME = "supplier_name";

        public final static String COLUMN_PRODUCT_SUPP_PHONE = "phone_number";


    }

}
