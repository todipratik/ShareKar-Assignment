package com.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataEntry {
	public static final String KEY_ITEM = "item";
	public static final String KEY_DESCRIPTION = "description";

	public static final String DATABASE_NAME = "ShareKar";
	public static final String DATABASE_TABLE = "Items";
	public static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	public SQLiteDatabase ourDatabase;

	private class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ITEM
					+ " TEXT NOT NULL, " + KEY_DESCRIPTION + " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + " );");
			onCreate(db);
		}

	}

	public DataEntry(Context c) {
		ourContext = c;
	}

	public DataEntry open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String item, String description) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ITEM, item);
		cv.put(KEY_DESCRIPTION, description);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public Cursor readAll() {
		return ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
	}
}
