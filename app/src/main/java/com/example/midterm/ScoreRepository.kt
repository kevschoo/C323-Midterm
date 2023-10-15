package com.example.midterm
import androidx.lifecycle.LiveData


/**
 * Repository for managing operations related to the Score entity
 *
 * @param noteDao Data Access Object (DAO) for the Score entity
 */
class ScoreRepository(private val noteDao: ScoreDao)
{
    /**
     * Fetches all the scores from the database
     *
     * @return LiveData list of all scores
     */
    fun getAllScores(): LiveData<List<Score>> {return noteDao.getAllScores()}

    /**
     * Inserts a score into the database
     *
     * @param score The score to be inserted
     */
    suspend fun insert(score: Score) {noteDao.insert(score)}

    /**
     * Updates an existing score in the database
     *
     * @param score The score with updated data
     */
    suspend fun update(score: Score) {noteDao.update(score)}

    /**
     * Deletes a score from the database
     *
     * @param score The score to be deleted
     */
    suspend fun delete(score: Score) {noteDao.delete(score)}
}