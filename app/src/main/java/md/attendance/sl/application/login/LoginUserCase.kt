package md.attendance.sl.application.login

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.SessionManager
import javax.inject.Inject

class LoginUserCase @Inject constructor(
    private val loginRepository: LoginInterfaces

) {

    operator fun invoke(
        userName: String,
        password: String
    ): Flow<Boolean> {
        return loginRepository.login(userName, password)
    }

     fun logOut(): Flow<Unit> {

        return loginRepository.logOut();
    }


}
