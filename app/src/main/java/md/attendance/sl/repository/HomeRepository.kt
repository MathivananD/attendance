package md.attendance.sl.repository

import md.attendance.sl.application.home.HomeInterface
import md.attendance.sl.data.users.UserDao
import md.attendance.sl.data.users.UserEntity

class HomeRepository(val userDaos: UserDao) : HomeInterface {
    override suspend fun getUser(id: Int): UserEntity? {
        return userDaos.getUserById(id);
    }

    override suspend fun updateUser(entity: UserEntity) {
        return userDaos.updateUser(entity)
    }
}