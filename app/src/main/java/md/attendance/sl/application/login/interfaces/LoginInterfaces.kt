package md.attendance.sl.application.login.interfaces

import kotlinx.coroutines.flow.Flow

interface LoginInterfaces {

    sealed class Exceptions(msg: String): Exception(msg){

        class NoNetWorkException: Exceptions("Internet not available")
        class UserNameNotFound: Exceptions("UserName not found")
        class PasswordIncorrect: Exceptions("password is incorrect")
        class CustomError(msg: String?): Exceptions(msg?:"Something went wrong")

    }

  fun login(userName: String, password: String): Flow<Boolean>

  fun logOut(): Flow<Unit>

  fun isSessionValid(token: String): Flow<Boolean>

}