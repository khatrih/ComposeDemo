package com.example.composedemo

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.composedemo.features_note.data.data_source.UserDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test"

@RunWith(AndroidJUnit4::class)
class UserMigrationTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        UserDatabase::class.java,
        listOf(UserDatabase.Migration4to5()),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate1To2() {

        var db = helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO Packages values('netflix', '150', 'test', 'cash')")
            close()
        }

        db = helper.runMigrationsAndValidate(DB_NAME, 2, true)

        db.query("SELECT * FROM Packages").apply {
            assertThat(moveToFirst()).isTrue()
            assertThat(getInt(getColumnIndex("lastUpdate"))).isEqualTo(0)
        }
    }

    @Test
    fun testAllMigration() {
        helper.createDatabase(DB_NAME, 1).apply { close() }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            UserDatabase::class.java,
            DB_NAME
        ).addMigrations(UserDatabase.MIGRATION_1_2).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}