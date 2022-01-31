package com.varets.lab101.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.varets.lab101.MainActivity;
import com.varets.lab101.db.Dao.DaoCases;
import com.varets.lab101.db.Entities.ModelCases;

@Database(entities = {ModelCases.class}, version = 1, exportSchema = false)
public abstract class CasesRoomDatabase extends RoomDatabase {

    public abstract DaoCases DaoCases();
    private static CasesRoomDatabase INSTANCE;
    public static CasesRoomDatabase getCaseDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (CasesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CasesRoomDatabase.class, "casesDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
