package md.attendance.sl.use_case

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.application.home.HomeInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.users.UserDao
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.repository.HistoryRepository
import md.attendance.sl.repository.HomeRepository
import javax.inject.Inject

class HomeUserCase @Inject constructor(
    val homeRepository: HomeInterface,
    val historyRepository: HistoryInterface,
    val sessionManager: SessionManager,
) {
    val currentUserId = sessionManager.getCurrentId()

    suspend fun getUser(): UserEntity? {

        return homeRepository.getUser(currentUserId);
    }

    fun getCurrentUserId(): Int {
        return sessionManager.getCurrentId()
    }

    suspend fun getTodayCheckIn(date: String): Flow<HistoryEntity?> {

        return historyRepository.getHistoryByDate(date, currentUserId)
    }


    suspend fun callCheckIn(historyEntity: HistoryEntity): Int  {
        return historyRepository.insertHistory(historyEntity)
    }

    suspend fun callCheckOut(historyEntity: HistoryEntity) {
        return historyRepository.updateHistory(historyEntity)
    }

}