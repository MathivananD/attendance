package md.attendance.sl.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import md.attendance.sl.application.history.HistoryInterface
import md.attendance.sl.application.home.HomeInterface
import md.attendance.sl.use_case.LoginUserCase
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.users.UserDao
import md.attendance.sl.data.users.UserDatabase
import md.attendance.sl.repository.LoginRepository
import md.attendance.sl.repository.SignUpRepository
import javax.inject.Singleton
import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.history.HistoryDao
import md.attendance.sl.data.history.HistoryDatabase
import md.attendance.sl.repository.HistoryRepository
import md.attendance.sl.repository.HomeRepository
import md.attendance.sl.repository.validators.UserValidator
import md.attendance.sl.use_case.HomeUserCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun getSessionManager(app: Application): SessionManager {
        return SessionManager(app.applicationContext)
    }

    @Provides
    @Singleton
    fun getUserRepository(
        userDao: UserDao,
        sessionManager: SessionManager,
        userValidator: UserValidator
    ): SignUpRepository {
        return SignUpRepository(userDao, sessionManager, userValidator)
    }

    @Provides
    @Singleton
    fun getUserDao(app: Application): UserDao {
        return UserDatabase.getDatabase(app.applicationContext).userDao()
    }

    @Provides
    @Singleton
    fun getHistoryDao(app: Application): HistoryDao {
        return HistoryDatabase.getDatabase(app.applicationContext).historyDao()
    }


    @Provides
    @Singleton
    fun getLoginRepository(userDao: UserDao, sessionManager: SessionManager): LoginInterfaces {
        return LoginRepository(userDao, sessionManager)
    }

    @Provides
    @Singleton
    fun getHistoryRepository(historyDao: HistoryDao): HistoryInterface {
        return HistoryRepository(historyDao)
    }

    @Provides
    @Singleton
    fun getLoginUseCase(
        loginRepository: LoginInterfaces
    ): LoginUserCase {
        return LoginUserCase(loginRepository)
    }

    @Provides
    @Singleton
    fun getHomeUseCase(
        historyRepository: HistoryInterface,
        homeRepository: HomeInterface,
        sessionManager: SessionManager,
        ): HomeUserCase {
        return HomeUserCase(homeRepository, historyRepository, sessionManager)
    }


    @Provides
    @Singleton
    fun getUserValidator(): UserValidator {
        return UserValidator()
    }
}

