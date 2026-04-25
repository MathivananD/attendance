package md.attendance.sl.application.home

import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.UserDao
import md.attendance.sl.data.UserEntity
import javax.inject.Inject

class HomeUserCase @Inject constructor(val userDaos: UserDao, val sessionManager: SessionManager) :
    HomeInterface {
    override suspend fun getUser(id: Int): UserEntity? {
        return userDaos.getUserById(id);
    }

    override fun getCurrentUserId(): Int {
        return sessionManager.getCurrentId()
    }

}