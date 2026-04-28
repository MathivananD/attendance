package md.attendance.sl.use_case

import android.util.*
import android.util.Log.*
import androidx.lifecycle.LiveData
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.application.home.HomeInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.users.UserEntity
import javax.inject.Inject

class HomeUserCase @Inject constructor(
    val homeRepository: HomeInterface,
    val historyRepository: HistoryInterface,
    val sessionManager: SessionManager,
) {
    fun  currentUserId() = getCurrentUserId()

    suspend fun getUser(): UserEntity? {

        return homeRepository.getUser(currentUserId());
    }

    fun getCurrentUserId(): Int {
        return sessionManager.getCurrentId()
    }

    suspend fun getTodayCheckIn(date: String): HistoryEntity? {

        return historyRepository.getHistoryByDate(date, currentUserId())
    }


    suspend fun callCheckIn(historyEntity: HistoryEntity): Long  {
        d("Historyddddddddddddd", historyEntity.toString())
        return historyRepository.insertHistory(historyEntity)
    }

    suspend fun callCheckOut(historyEntity: HistoryEntity) {
        return historyRepository.updateHistory(historyEntity)
    }
     fun getAll():LiveData<List<HistoryEntity>> {
        return historyRepository.getAllHistory()
    }

}