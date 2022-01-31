package pav.fit.bstu.lab9;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards")
    LiveData<List<ContactCard>> getAll();

    @Query("SELECT * FROM cards WHERE id = :id")
    ContactCard getById(String id);

    @Insert
    void insert(ContactCard card);

    @Update
    void update(ContactCard card);

    @Delete
    void delete(ContactCard card);

}
