package md.attendance.sl.application.home

import md.attendance.sl.data.UserEntity

interface HomeInterface {

    suspend fun getUser(id: Int): UserEntity?

     fun getCurrentUserId(): Int



}