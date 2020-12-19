package com.example.hotelmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hotelmanager.Model.Guest;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final Integer V = 1;
    public static final String DB_NAME = "GuestsOfHotel.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HotelHelperClass.HotelEntry.TABLE_NAME + " (" +
                    HotelHelperClass.HotelEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    HotelHelperClass.HotelEntry.COLUMN_NAME + " TEXT, " +
                    HotelHelperClass.HotelEntry.COLUMN_ROOM + " INTEGER, " +
                    HotelHelperClass.HotelEntry.COLUMN_TOTAL_PRICE + " DOUBLE, " +
                    HotelHelperClass.HotelEntry.COLUMN_SEX + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HotelHelperClass.HotelEntry.TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( SQL_DELETE_ENTRIES );
        onCreate(db);
    }

    public int editGuest(Guest guest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HotelHelperClass.HotelEntry.COLUMN_NAME, guest.getName());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_ROOM, guest.getRoom());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_TOTAL_PRICE, guest.getTotalPrice());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_SEX, guest.getSex());

        return db.update(HotelHelperClass.HotelEntry.TABLE_NAME,
                cv,
                HotelHelperClass.HotelEntry.COLUMN_ID + " = ?",
                new String[]{guest.getId() + ""});
    }

    public int deleteGuest(Guest guest) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(HotelHelperClass.HotelEntry.TABLE_NAME,
                HotelHelperClass.HotelEntry.COLUMN_ID + " = " + guest.getId(),
                null);
    }

    public long addGuest(Guest guest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HotelHelperClass.HotelEntry.COLUMN_NAME, guest.getName());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_ROOM, guest.getRoom());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_TOTAL_PRICE, guest.getTotalPrice());
        cv.put(HotelHelperClass.HotelEntry.COLUMN_SEX, guest.getSex());

        return db.insert(HotelHelperClass.HotelEntry.TABLE_NAME,
                null,
                cv);
    }

    public List<Guest> getGuests() {
        List<Guest> guests = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(HotelHelperClass.HotelEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(HotelHelperClass.HotelEntry.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(HotelHelperClass.HotelEntry.COLUMN_NAME);
            int roomIndex = cursor.getColumnIndex(HotelHelperClass.HotelEntry.COLUMN_ROOM);
            int totalPriceIndex = cursor.getColumnIndex(HotelHelperClass.HotelEntry.COLUMN_TOTAL_PRICE);
            int sexIndex = cursor.getColumnIndex(HotelHelperClass.HotelEntry.COLUMN_SEX);

            do {
                Guest guest = new Guest(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getInt(roomIndex),
                        cursor.getDouble(totalPriceIndex),
                        cursor.getString(sexIndex));
                guests.add(guest);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return guests;
    }
}
