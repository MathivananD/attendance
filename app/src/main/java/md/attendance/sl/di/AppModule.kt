package md.attendance.sl.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import md.attendance.sl.application.login.LoginUserCase
import md.attendance.sl.data.SessionManager
import md.attendance.sl.data.UserDao
import md.attendance.sl.data.UserDatabase
import md.attendance.sl.infrastructure.LoginRepository
import md.attendance.sl.infrastructure.UserRepository
import javax.inject.Singleton
import md.attendance.sl.application.login.interfaces.LoginInterfaces

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun getSessionManager(app: Application): SessionManager {
        return SessionManager(app.applicationContext);
    }

    @Provides
    @Singleton
    fun getUserRepository(app: Application): UserRepository {
        return UserRepository(app);
    }

    @Provides
    @Singleton
    fun getUserDao(app: Application): UserDao {
        return UserDatabase.getDatabase(app.applicationContext).userDao();
    }

    @Provides
    @Singleton
    fun getLoginRepository(userDao: UserDao, sessionManager: SessionManager): LoginInterfaces {
        return LoginRepository(userDao, sessionManager)
    }

    @Provides
    @Singleton
    fun getLoginUseCase(loginRepository: LoginInterfaces, sessionManager: SessionManager): LoginUserCase {
        return LoginUserCase(loginRepository, sessionManager);
    }
}

