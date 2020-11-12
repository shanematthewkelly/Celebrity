package com.example.spendcelebsmoney.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@androidx.room.Dao
public interface UserDAO {

    @Query("DELETE FROM users")
    void bulkDelete();

    @Query("SELECT * FROM users ORDER BY myName DESC")
    LiveData<List<User>> allUserWorth();

    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

}
