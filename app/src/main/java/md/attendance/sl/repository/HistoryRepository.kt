package md.attendance.sl.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.data.history.HistoryDao
import md.attendance.sl.data.history.HistoryEntity
import javax.inject.Inject


class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryInterface {
    override suspend fun insertHistory(historyEntity: HistoryEntity): Long {
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

    override suspend fun getHistoryByDate(date: String,id: Int):  HistoryEntity? {
       return  historyDao.getHistoryByDate(date,id)
    }

    override fun getHistory(id: Int): LiveData<List<HistoryEntity>> {
        return historyDao.getHistoryByUserId(id) ?: MutableLiveData(emptyList())
    }

    override fun getAllHistory(): LiveData<List<HistoryEntity>> {
        return historyDao.getAllHistory()
    }
}
