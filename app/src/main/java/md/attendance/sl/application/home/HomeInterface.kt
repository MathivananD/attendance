package md.attendance.sl.application.home

import md.attendance.sl.data.users.UserEntity

interface HomeInterface {

    suspend fun getUser(id: Int): UserEntity?




}