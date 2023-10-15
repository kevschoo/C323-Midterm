package com.example.midterm

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.midterm.databinding.FragmentGameQuestionBinding

class GameQuestionFragment : Fragment()
{
    private var _binding: FragmentGameQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentGameQuestionBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return view
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored into the view
     * This method sets up button click listeners and associated logic for guessing the answer
     *
     * @param view The View
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.minusButton.setOnClickListener {
            val currentVal = binding.guessEditText.text.toString().toIntOrNull() ?: 0
            binding.guessEditText.setText((currentVal - 1).toString())
        }

        binding.plusButton.setOnClickListener {
            val currentVal = binding.guessEditText.text.toString().toIntOrNull() ?: 0
            binding.guessEditText.setText((currentVal + 1).toString())
        }

        binding.submitGuessButton.setOnClickListener {
            val guess = binding.guessEditText.text.toString().toIntOrNull()
            if (guess != null) {
                viewModel.incrementAttempts()
                if (viewModel.isGuessCorrect(guess)) {
                    playSound(R.raw.ding)
                    val providedName = binding.playerNameEditText.text.toString()
                    val playerName = if (providedName.isEmpty()) "Unknown Player" else providedName
                    viewModel.endGame(playerName, viewModel.attempts.value ?: 0)
                    viewModel.insert(Score(0, playerName, viewModel.playerScore.value!!))

                    view.findNavController().navigate(R.id.action_gameFragment_to_mainFragment)
                } else {
                    playSound(R.raw.buzz)
                    if (guess < viewModel.getCorrectNumber()!!) {
                        Toast.makeText(context, "Guess higher!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Guess lower!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Enter a valid guess", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Play a sound based on the provided resource ID
     *
     * @param resourceId The resource ID of the sound to play
     */
    private fun playSound(resourceId: Int) {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer.start()

        // Clean up resources when done playing
        mediaPlayer.setOnCompletionListener { player ->
            player.release()
        }
    }

    /**
     * Called when the view is destroyed
     */
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}