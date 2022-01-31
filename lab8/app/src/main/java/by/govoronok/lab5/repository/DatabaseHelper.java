package by.govoronok.lab5.repository;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app.db";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "item"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_FOUNDPLACE = "foundPlace";
    public static final String COLUMN_FOUNDDATE = "foundDate";
    public static final String COLUMN_RETURNINGPLACE = "returningPlace";
    public static final String COLUMN_PICTURE = "picture";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE item (" + COLUMN_ID + " INTEGER, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_FOUNDPLACE + " TEXT, "
                + COLUMN_FOUNDDATE + " TEXT, "
                + COLUMN_RETURNINGPLACE + " TEXT, "
                + COLUMN_PICTURE + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_ID + ", " + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION + ", " + COLUMN_FOUNDPLACE + ", " + COLUMN_FOUNDPLACE + ", "
                + COLUMN_FOUNDDATE + ", " + COLUMN_RETURNINGPLACE +  ", " + COLUMN_PICTURE
                + ") VALUES (10, 'Car', 'NewcAr', 'bstu', 'november', 'sbtu', 'sdpnsdpv');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}