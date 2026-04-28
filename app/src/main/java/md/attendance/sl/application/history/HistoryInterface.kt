package md.attendance.sl.application.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import md.attendance.sl.data.history.HistoryEntity

interface HistoryInterface {
    suspend fun insertHistory(historyEntity: HistoryEntity): Int
    suspend fun updateHistory(historyEntity: HistoryEntity)
    suspend fun deleteHistory(historyEntity: HistoryEntity)
    suspend fun getHistoryById(id: Int): HistoryEntity?

    suspend fun getHistoryByDate(date: String,id: Int):  Flow<HistoryEntity?>
    fun getHistory(id: Int): LiveData<List<HistoryEntity>>
    fun getAllHistory(): LiveData<List<HistoryEntity>>
}