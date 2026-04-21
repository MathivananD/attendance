package md.attendance.sl.application.login

import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.SessionManager
import javax.inject.Inject

class LoginUserCase @Inject constructor(
    private val loginRepository: LoginInterfaces,
    private val sessionManager: SessionManager,

    ) {
    init {
        println("hhhhhhhhhhhhhhhhhhhhhhhhhhh")
    }

}
