package md.attendance.sl.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.history.HistoryEntity
import javax.inject.Inject



class HistoryUseCase @Inject constructor(
    val historyRepository: HistoryInterface,
    val sessionManager: SessionManager
) {

    suspend fun insertHistory(historyEntity: HistoryEntity): Long {
        return historyRepository.insertHistory(historyEntity)
    }

    suspend fun updateHistory(historyEntity: HistoryEntity) {
        historyRepository.updateHistory(historyEntity)
    }

    suspend fun deleteHistory(historyEntity: HistoryEntity) {
        historyRepository.deleteHistory(historyEntity)
    }

    suspend fun getUserById(id: Int): HistoryEntity? {
        return historyRepository.getHistoryById(id)
    }

    fun getHistory(): Flow<List<HistoryEntity>> {
        val id=sessionManager.getCurrentId()
        Log.d("vvvvvvvvvvvvvvv 5555","${id}")
        return historyRepository.getHistory(id)
    }

    fun getCurrentUserId(): Int {
        return sessionManager.getCurrentId()
    }
}
