package md.attendance.sl.infrastructure

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.UserDao
import md.attendance.sl.data.UserEntity
import javax.inject.Inject


class LoginRepository @Inject constructor(
    val userDao: UserDao,
    val sessionManager: SessionManager,
) : LoginInterfaces {
    override fun login(
        userName: String,
        password: String,
    ): Flow<Boolean> = flow {
        val user =
            userDao.getUserByName(userName) ?: throw LoginInterfaces.Exceptions.UserNameNotFound()
        if (user.password != password) {
            throw LoginInterfaces.Exceptions.PasswordIncorrect()
        }
        sessionManager.saveLogin(user.id)
        emit(true)

    }.flowOn(Dispatchers.IO)

    override fun logOut(): Flow<Unit> = flow {
        sessionManager.logout()
        emit(Unit)
    }

    override fun isSessionValid(token: String): Flow<Boolean> = flow {
        emit(sessionManager.isLoggedIn())
    }


}
