package md.attendance.sl.use_case

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.repository.SignUpRepository
import md.attendance.sl.repository.validators.UserValidator
import md.attendance.sl.repository.validators.ValidationResult
import javax.inject.Inject

class SignupUserCase @Inject constructor(
    val signUpRepository: SignUpRepository,
     val validator: UserValidator
) {
    fun signUp(userEntity: UserEntity): Flow<Boolean> {
        return signUpRepository.signUp(userEntity)

    }

    fun signUpValidate(userEntity: UserEntity): ValidationResult {
        return validator.validate(userEntity);
    }
}