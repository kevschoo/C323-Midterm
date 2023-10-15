package com.example.midterm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
/**
 * Data Access Object for the Score
 * Provides methods to interact with the database
 */
@Dao
interface ScoreDao
{
    /**
     * Inserts a new score into the database
     *
     * @param score The score to be inserted
     */
    @Insert
    suspend fun insert(score: Score)

    /**
     * Updates an existing score in the database
     *
     * @param score The score to be updated
     */
    @Update
    suspend fun update(score: Score)

    /**
     * Deletes a score from the database
     *
     * @param score The score to be deleted
     */
    @Delete
    suspend fun delete(score: Score)

    /**
     * Retrieves all scores from the database, ordered by playerScore in ascending order
     *
     * @return LiveData list of scores
     */
    @Query("SELECT * FROM scores ORDER BY playerScore ASC")
    fun getAllScores(): LiveData<List<Score>>
}