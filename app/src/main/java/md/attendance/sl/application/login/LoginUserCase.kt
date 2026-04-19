package md.attendance.sl.application.login

import md.attendance.sl.application.login.interfaces.LoginInterfaces
import javax.inject.Inject

class LoginUserCase @Inject constructor(
    private val loginRepository: LoginInterfaces,

    ) {

}