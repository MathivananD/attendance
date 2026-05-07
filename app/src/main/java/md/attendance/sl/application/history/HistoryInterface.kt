package md.attendance.sl.application.history

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.data.history.HistoryEntity

interface HistoryInterface {
    suspend fun insertHistory(historyEntity: HistoryEntity): Long
    suspend fun updateHistory(historyEntity: HistoryEntity)
    suspend fun deleteHistory(historyEntity: HistoryEntity)
    suspend fun getHistoryById(id: Int): HistoryEntity?

    suspend fun getHistoryByDate(date: String,id: Int):  HistoryEntity?
    fun getHistory(id: Int): Flow<List<HistoryEntity>>
    fun getAllHistory(): Flow<List<HistoryEntity>>
}
