package md.attendance.sl.infrastructure

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.UserDao
import md.attendance.sl.data.UserDatabase
import md.attendance.sl.data.UserEntity

class UserRepository(application: Application) : LoginInterfaces {
    override fun login(
        userName: String,
        password: String,
    ): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun logOut(): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun isSessionValid(token: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    private val userDao: UserDao = UserDatabase.getDatabase(application).userDao()
    var allUsers: LiveData<List<UserEntity>> = liveData { }

    suspend fun getAllUser() {
        allUsers = userDao.getAllUsers()
    }

    suspend fun insert(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun update(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }

    suspend fun delete(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.deleteUser(user)
        }
    }
}