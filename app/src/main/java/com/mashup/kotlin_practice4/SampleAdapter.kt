package com.mashup.kotlin_practice4

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.kotlin_practice4.extensions.inflate

class SampleAdapter : ListAdapter<SampleAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        // TODO get ItemViewType
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // TODO Create ViewHolder
        return TextViewHolder(parent.inflate(viewType))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ImageViewHolder -> holder.bind(item)
            is TextViewHolder -> holder.bind(item)
            else -> Log.e(TAG, "bind unknown type view holder")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(position !in 0 until itemCount) return
        val item = getItem(position) ?: return

        if(payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                when (it) {
                    PAYLOAD_TEXT_CHANGED -> (holder as? TextViewHolder)?.bind(item)
                    PAYLOAD_IMAGE_CHANGED -> (holder as? ImageViewHolder)?.bind(item)
                }
            }
        }
    }

    private fun ImageViewHolder.bind(item: SampleAdapterItem) {
        // TODO bind image
        Glide.with(itemView)
//            .load(item.imageUrl)
//            .into(imageView)
    }

    private fun TextViewHolder.bind(item: SampleAdapterItem) {
//        textView.text = item.text
    }

    private class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView as ImageView
    }

    private class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView as TextView
    }

    companion object {
        private const val TAG = "SamplAdapter"

        private const val VIEW_TYPE_TEXT = R.layout.item_sample_text
        private const val VIEW_TYPE_IMAGE = R.layout.item_sample_image

        private const val PAYLOAD_TEXT_CHANGED = "payload-text-changed"
        private const val PAYLOAD_IMAGE_CHANGED = "payload-image-changed"

        private val DIFF_CALLBACK = AsyncDifferConfig.Builder(
            object : DiffUtil.ItemCallback<SampleAdapterItem>() {
                override fun areItemsTheSame(
                    oldItem: SampleAdapterItem,
                    newItem: SampleAdapterItem
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun areContentsTheSame(
                    oldItem: SampleAdapterItem,
                    newItem: SampleAdapterItem
                ): Boolean {
                    TODO("Not yet implemented")
                }
            }
        ).build()
    }
}