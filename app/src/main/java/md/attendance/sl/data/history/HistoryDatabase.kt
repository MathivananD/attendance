package md.attendance.sl.data.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HistoryEntity::class], version = 3, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        val MIGRATION_2_3 =
            object : Migration(2, 3) {

                override fun migrate(
                    db: SupportSQLiteDatabase,
                ) {

                    db.execSQL(
                        """
                ALTER TABLE history_table
                ADD COLUMN checkOutLatitude REAL
                """
                    )

                    db.execSQL(
                        """
                ALTER TABLE history_table
                ADD COLUMN checkOutLongitude REAL
                """
                    )
                }
            }

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                ).addMigrations(MIGRATION_2_3).build()
                INSTANCE = instance
                instance
            }
        }
    }
}