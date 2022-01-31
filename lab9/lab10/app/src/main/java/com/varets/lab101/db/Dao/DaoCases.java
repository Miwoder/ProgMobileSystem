package com.varets.lab101.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.varets.lab101.db.Entities.ModelCases;

import java.util.List;

@Dao
public interface DaoCases {
    @Query("SELECT * FROM ModelCases WHERE id = :id")
    ModelCases getCaseId(long id);

    @Query("SELECT * FROM ModelCases ORDER BY nameCase")
    LiveData<List<ModelCases>> getAllCases();

    @Insert
    void addCase(ModelCases modelCases);

    @Update
    void updateCase(ModelCases modelCases);

    @Delete
    void deleteCase(ModelCases modelCases);
}
