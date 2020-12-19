package com.example.hotelmanager.DB;

import android.provider.BaseColumns;

public class HotelHelperClass {

    private HotelHelperClass() {}

    public static class HotelEntry implements BaseColumns {
        public static final String TABLE_NAME = "guests";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ROOM = "room";
        public static final String COLUMN_TOTAL_PRICE = "total_price";
        public static final String COLUMN_SEX = "sex";
    }
}
