package com.example.midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.midterm.databinding.FragmentGameAttemptsBinding

class GameAttemptsFragment : Fragment()
{
    private var _binding: FragmentGameAttemptsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return Return the View for the fragment's UI, or null
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentGameAttemptsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return view
    }

    /**
     * Called immediately after has onCreateView returned, but before any saved state has been restored in to the view
     *
     * @param view The View
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attempts.observe(viewLifecycleOwner, Observer { attempts ->
            binding.attemptTextView.text = "Attempts: $attempts"
        })
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