package com.ramsoft.mydog.ui.alldogs.view.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ramsoft.mydog.R

/**
 * @author Priyesh Bhargava
 */
class AllBreedsAdapter(private val context: Context, var allBreeds: Map<String, List<String>>) :
    RecyclerView.Adapter<AllBreedsAdapter.ViewHolder>() {
    private lateinit var onClickListener: OnClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_all_dogs, parent, false)

        return ViewHolder(view)
    }

    fun setClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener;
    }

    fun setBreedsList(allBreeds: Map<String, List<String>>) {
        this.allBreeds = allBreeds;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBreeds.text = allBreeds.keys.toList()[position]
        holder.tvSubBreeds.text =
            if (allBreeds[allBreeds.keys.toList()[position]]?.isEmpty() == true) "No sub breeds" else allBreeds[allBreeds.keys.toList()[position]]?.toList()
                ?.joinToString { it ->
                    return@joinToString it
                }
        holder.cvRoot.setOnClickListener {
            onClickListener.onClick(holder.cvRoot, position)
        }
    }

    override fun getItemCount(): Int {
        return allBreeds.keys.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBreeds: TextView = itemView.findViewById(R.id.tvBreeds)
        val tvSubBreeds: TextView = itemView.findViewById(R.id.tvSubBreeds)
        val cvRoot: CardView = itemView.findViewById(R.id.cvRoot)
    }
}