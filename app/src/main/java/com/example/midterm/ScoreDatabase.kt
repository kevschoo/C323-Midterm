package com.example.midterm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
/**
 * The Room database that contains the Score table
 */
@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class ScoreDatabase : RoomDatabase()
{
    /**
     * Retrieves the Data Access Object (DAO) for the Score table
     *
     * @return The DAO for the Score table
     */
    abstract fun ScoreDao(): ScoreDao

    /**
     * Singleton instance of the ScoreDatabase
     */
    companion object
    {
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        /**
         * Returns an instance of ScoreDatabas
         * If an instance already exists, it returns that
         * Otherwise, it creates a new database instance
         *
         * @param context Context used to get the application context
         * @return An instance of ScoreDatabase
         */
        fun getDatabase(context: Context): ScoreDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}