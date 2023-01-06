package com.example.composedemo.features_note.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.composedemo.features_note.domain.model.Packages
import com.example.composedemo.features_note.domain.model.User

@Database(
    entities = [(User::class), (Packages::class)],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = UserDatabase.Migration3to4::class),
        AutoMigration(from = 3, to = 4, spec = UserDatabase.Migration4to5::class),
    ]
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserAuthDao
    abstract val packagesCrud: PackagesCrud

    companion object {
        const val DATABASE_NAME = "users_db"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Packages ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }
    }

    @RenameColumn(tableName = "Packages", fromColumnName = "lastUpdate", toColumnName = "updated")
    class Migration3to4 : AutoMigrationSpec

    @RenameColumn(tableName = "Packages", fromColumnName = "lastUpdate", toColumnName = "update")
    class Migration4to5 : AutoMigrationSpec
}