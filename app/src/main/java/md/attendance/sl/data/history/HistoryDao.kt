package md.attendance.sl.data.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(user: HistoryEntity): Long

    @Update
    suspend fun updateHistory(user: HistoryEntity)

    @Delete
    suspend fun deleteHistory(user: HistoryEntity)

    @Query("SELECT * FROM history_table WHERE userId = :id")
    fun getHistoryByUserId(id: Int): Flow<List<HistoryEntity>>

    @Query(
        """
    SELECT * FROM history_table
    WHERE checkInTime LIKE :date || '%' And userId = :id
    LIMIT 1
"""
    )
    suspend fun getHistoryByDate(
        date: String,
        id: Int
    ): HistoryEntity?

    @Query("SELECT * FROM history_table WHERE id = :id Limit 1")
    suspend fun getHistoryById(id: Int): HistoryEntity?

    @Query("SELECT * FROM history_table")
    fun getAllHistory(): Flow<List<HistoryEntity>>

}
