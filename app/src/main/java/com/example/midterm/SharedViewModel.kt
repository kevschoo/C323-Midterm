package com.example.midterm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random
/**
 *  The view model used to share data across the application
 */
class SharedViewModel(application: Application) : AndroidViewModel(application)
{
    private val scoreRepository: ScoreRepository
    val playerName: MutableLiveData<String?> = MutableLiveData()
    val playerScore: MutableLiveData<Int?> = MutableLiveData()
    private val correctNumber = MutableLiveData<Int>(Random.nextInt(1, 101))
    val attempts = MutableLiveData<Int>(0)

    init {
        val noteDao = ScoreDatabase.getDatabase(application).ScoreDao()
        scoreRepository = ScoreRepository(noteDao)
    }

    /**
     * Updates a score in the repository
     *
     * @param score The score to update
     */
    fun update(score: Score) = viewModelScope.launch { scoreRepository.update(score) }

    /**
     * Inserts a new score into the repository
     *
     * @param score The score to insert
     */
    fun insert(score: Score) = viewModelScope.launch { scoreRepository.insert(score) }

    /**
     * Fetches all the scores from the repository
     *
     * @return LiveData list of all scores
     */
    fun getAllScores(): LiveData<List<Score>> { return scoreRepository.getAllScores() }

    /**
     * Deletes a score from the repository
     *
     * @param score The score to delete
     */
    fun delete(score: Score) = viewModelScope.launch {scoreRepository.delete(score) }

    /**
     * Ends the game and sets the player's name and score
     *
     * @param name Player's name
     * @param score Player's score
     */
    fun startNewGame() {
        playerName.value = null
        playerScore.value = null
        attempts.value = 0
        correctNumber.value = Random.nextInt(1, 101)
    }

    /**
     * Ends the game and sets the player's name and score
     *
     * @param name Player's name
     * @param score Player's score
     */
    fun endGame(name: String?, score: Int) {
        playerName.value = name ?: "Unknown Player"
        playerScore.value = score
    }

    /**
     * Checks if the provided guess is correct
     *
     * @param guess The guessed number
     * @return `true` if the guess is correct, `false` otherwise
     */
    fun isGuessCorrect(guess: Int): Boolean {
        return guess == correctNumber.value
    }

    /**
     * Increments the attempts count
     */
    fun incrementAttempts() {
        attempts.value = (attempts.value ?: 0) + 1
    }

    /**
     * Fetches the correct number for the game
     *
     * @return Correct number
     */
    fun getCorrectNumber(): Int? {
        return correctNumber.value
    }
}