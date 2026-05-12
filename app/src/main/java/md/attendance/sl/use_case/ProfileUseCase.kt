package md.attendance.sl.use_case

import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.application.home.HomeInterface
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.repository.validators.UserValidator
import md.attendance.sl.repository.validators.ValidationResult
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val homeRepository: HomeInterface,
    private val validator: UserValidator,
    private val sessionManager: SessionManager,
) {


    fun currentUserId() = getCurrentUserId()

    suspend fun getUser(): UserEntity? {

        return homeRepository.getUser(currentUserId());
    }

    fun getCurrentUserId(): Int {
        return sessionManager.getCurrentId()
    }

    suspend fun updateUser(user: UserEntity) {
        homeRepository.updateUser(user)
    }
    fun signUpValidate(userEntity: UserEntity): ValidationResult {
        return validator.validate(userEntity);
    }

}