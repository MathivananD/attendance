package md.attendance.sl.infrastructure

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import md.attendance.sl.application.login.interfaces.SignUpInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.UserDao
import md.attendance.sl.data.UserEntity
import md.attendance.sl.infrastructure.validators.UserValidator
import md.attendance.sl.infrastructure.validators.ValidationResult
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    val userDao: UserDao,
    val sessionManager: SessionManager,
    val validator: UserValidator
) : SignUpInterface {
    override fun signUp(userEntity: UserEntity): Flow<Boolean> = flow {
        val user = userDao.getUserByName(userEntity.email)
        if (user != null) {
            throw SignUpInterface.Exceptions.AlreadyExist()
        }
        val currentUser =  userDao.insertUser(userEntity);

        sessionManager.saveLogin(currentUser.toInt())
        emit(true)
    }

    override fun signUpValidate(userEntity: UserEntity): ValidationResult {
        return validator.validate(userEntity)
    }
}
