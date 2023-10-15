package com.example.midterm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying a list of scores in a RecyclerView
 *
 * @param onScoreDelete Function to call when a score is deleted
 * @param onScoreEdit Function to call when a score is edited
 */
class ScoreListAdapter(
    private val onScoreDelete: (Score) -> Unit,
    private val onScoreEdit: (Score) -> Unit
) : RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder>() {

    var scores = emptyList<Score>()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(ScoreDiffCallback(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    /**
     * ViewHolder to display a single score
     */
    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvScoreTitle: TextView = itemView.findViewById(R.id.tvScoreTitle)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    /**
     * Create new views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(itemView)
    }

    /**
     * Replace the contents of a view
     */
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val current = scores[position]
        holder.tvScoreTitle.text = "${current.playerName}'s Score: ${current.playerScore}"
        holder.btnDelete.setOnClickListener { onScoreDelete(current) }
        holder.itemView.setOnClickListener { onScoreEdit(current) }
    }

    /**
     * Return the size of the dataset
     */
    override fun getItemCount() = scores.size

    /**
     * Callback for calculating the diff between two non-null items in a list
     *
     * @param oldList The old list
     * @param newList The new list
     */
    private class ScoreDiffCallback(
        private val oldList: List<Score>,
        private val newList: List<Score>
    ) : DiffUtil.Callback()
    {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}