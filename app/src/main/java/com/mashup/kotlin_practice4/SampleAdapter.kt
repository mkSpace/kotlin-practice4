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

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SampleAdapterItem.Text -> VIEW_TYPE_TEXT
        is SampleAdapterItem.Image -> VIEW_TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_IMAGE -> ImageViewHolder(parent.inflate(viewType))
            else -> TextViewHolder(parent.inflate(viewType))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ImageViewHolder -> (item as? SampleAdapterItem.Image)?.let { holder.bind(it) }
            is TextViewHolder -> (item as? SampleAdapterItem.Text)?.let { holder.bind(it) }
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
                when {
                    it == PAYLOAD_TEXT_CHANGED && item is SampleAdapterItem.Text ->
                        (holder as? TextViewHolder)?.bind(item)
                    it == PAYLOAD_IMAGE_CHANGED && item is SampleAdapterItem.Image ->
                        (holder as? ImageViewHolder)?.bind(item)
                }
            }
        }
    }

    private fun ImageViewHolder.bind(item: SampleAdapterItem.Image) {
        Glide.with(itemView)
            .load(item.imageUrl)
            .into(imageView)
    }

    private fun TextViewHolder.bind(item: SampleAdapterItem.Text) {
        textView.text = item.text
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
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: SampleAdapterItem,
                    newItem: SampleAdapterItem
                ): Boolean = oldItem == newItem

                override fun getChangePayload(
                    oldItem: SampleAdapterItem,
                    newItem: SampleAdapterItem
                ): Any? {
                    if (oldItem is SampleAdapterItem.Text
                        && newItem is SampleAdapterItem.Text
                        && oldItem.copy(text = newItem.text) == newItem
                    ) {
                        return PAYLOAD_TEXT_CHANGED
                    } else if (oldItem is SampleAdapterItem.Image
                        && newItem is SampleAdapterItem.Image
                        && oldItem.copy(imageUrl = newItem.imageUrl) == newItem
                    ) {
                        return PAYLOAD_IMAGE_CHANGED
                    }
                    return super.getChangePayload(oldItem, newItem)
                }
            }
        ).build()
    }
}