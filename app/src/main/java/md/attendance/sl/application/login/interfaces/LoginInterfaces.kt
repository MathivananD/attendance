package md.attendance.sl.application.login.interfaces

import kotlinx.coroutines.flow.Flow

interface LoginInterfaces {

    sealed class Exceptions(msg: String): Exception(msg){

        class NoNetWorkException: Exceptions("Internet not available")
        class CredentialIncorrect: Exceptions("User name or password id incorrect")
        class CustomError(msg: String?): Exceptions(msg?:"Something went wrong")
    }

  fun login(userName: String, password: String): Flow<String>

  fun logOut(): Flow<Unit>

  fun isSessionValid(token: String): Flow<Boolean>

}