package com.example.midterm
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a Score entity in the database
 *
 * @property id Unique identifier for the score
 * @property playerName Name of the player
 * @property playerScore Score of the player
 */
@Entity(tableName = "scores")
data class Score
    (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val playerName: String,
    val playerScore: Int
)