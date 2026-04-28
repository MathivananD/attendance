package md.attendance.sl.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.history.HistoryDao
import md.attendance.sl.data.history.HistoryEntity
import javax.inject.Inject


class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryInterface {
    override suspend fun insertHistory(historyEntity: HistoryEntity): Int {
       return  historyDao.insertHistory(historyEntity)
    }

    override suspend fun updateHistory(historyEntity: HistoryEntity) {
       return historyDao.updateHistory(historyEntity)
    }

    override suspend fun deleteHistory(historyEntity: HistoryEntity) {
       return historyDao.deleteHistory(historyEntity)
    }

    override suspend fun getHistoryById(id: Int):  HistoryEntity? {
          return historyDao.getHistoryById(id)
    }

    override suspend fun getHistoryByDate(date: String,id: Int):  Flow<HistoryEntity?> {
        TODO("Not yet implemented")
    }

    override fun getHistory(id: Int): LiveData<List<HistoryEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllHistory(): LiveData<List<HistoryEntity>> {
        TODO("Not yet implemented")
    }
}