package com.ramsoft.mydog.ui.alldogs.view.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ramsoft.mydog.R
import com.squareup.picasso.Picasso

/**
 * @author Priyesh Bhargava
 */
class DogsByBreedAdapter(private val context: Context, var imgList: List<String>) :
    RecyclerView.Adapter<DogsByBreedAdapter.ViewHolder>() {
    private  var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_all_dogs_by_breed, parent, false)

        return ViewHolder(view)
    }

     fun setClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener;
    }

    fun setDogsImgList(imgList: List<String>) {
        this.imgList = imgList;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(imgList[position]).resize(80, 80).placeholder(R.drawable.progress_animation).into(holder.imageView)
        holder.imageView.setOnClickListener {
            onClickListener?.onClick(holder.imageView, position)
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)


    }
}