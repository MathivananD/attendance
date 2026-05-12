package md.attendance.sl.data.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {



    abstract fun userDao(): UserDao

    companion object {
        val MIGRATION_1_2 =
            object : Migration(1, 2) {

                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {

                    database.execSQL(
                        """
                ALTER TABLE user_table
                ADD COLUMN profileImage  TEXT
                """
                    )
                }
            }
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )   .addMigrations(
                    MIGRATION_1_2
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}
