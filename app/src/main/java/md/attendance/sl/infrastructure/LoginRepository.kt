package md.attendance.sl.infrastructure

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.login.interfaces.LoginInterfaces

class LoginRepository : LoginInterfaces {
    override fun login(
        userName: String,
        password: String
    ): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun logOut(): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun isSessionValid(token: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}