package kr.dkshin.android.pagingwithnetworkandroom.data.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;

/**
 * Created by SHIN on 2018. 7. 13..
 */
@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> userList);

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    User findById(long id);

}
