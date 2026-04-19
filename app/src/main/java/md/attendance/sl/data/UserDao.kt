package md.attendance.sl.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user:  UserEntity)

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM user_table WHERE email = :email And password = :password")
    suspend fun getUserByUserNameAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): LiveData<List<UserEntity>>

}