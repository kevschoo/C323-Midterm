package com.example.midterm

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midterm.databinding.FragmentHighScoreBinding

class HighScoreFragment : Fragment()
{
    private var _binding: FragmentHighScoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return view
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored into the view
     * This method sets up the dialog and highscore fragment
     *
     * @param view The View
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ScoreListAdapter(
            onScoreDelete = { score ->
                // Building the alert dialog here
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete Score")
                builder.setMessage("Are you sure you want to delete ${score.playerName}'s score?")
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.delete(score)
                    Toast.makeText(context, "Score deleted", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No", null)
                builder.create().show()
            },
            onScoreEdit = { score ->}
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllScores().observe(viewLifecycleOwner, { scores ->
            if (scores.isNullOrEmpty())
            {
                binding.emptyHighScoresTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
            else
            {
                binding.emptyHighScoresTextView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.scores = scores
                adapter.notifyDataSetChanged() // notify the adapter that the data set has changed
            }
        })
        binding.returnMainButton.setOnClickListener { view.findNavController().navigate(R.id.action_highScoreFragment_to_mainFragment)}

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