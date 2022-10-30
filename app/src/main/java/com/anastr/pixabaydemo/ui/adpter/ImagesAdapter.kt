package com.anastr.pixabaydemo.ui.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anastr.pixabaydemo.data.model.ImageInfo
import com.anastr.pixabaydemo.databinding.ItemImageBinding
import com.bumptech.glide.Glide

typealias OnImageClicked = ((ImageInfo) -> Unit)

class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private val imagesDiff = ImagesDiffCallback(this)

    private var onItemClicked: OnImageClicked? = null

    fun updateImages(newList: List<ImageInfo>) {
        imagesDiff.updateList(newList)
    }

    fun setOnItemClickListener(onItemClicked: OnImageClicked) {
        this.onItemClicked = onItemClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageInfo = imagesDiff.currentList[position]

        holder.binding.tvUserName.text = imageInfo.userName

        holder.itemView.setOnClickListener { onItemClicked?.invoke(imageInfo) }

        Glide.with(holder.itemView.context)
            .load(imageInfo.previewUrl)
            .into(holder.binding.ivImage)

        Glide.with(holder.itemView.context)
            .load(imageInfo.userImageUrl)
            .circleCrop()
            .into(holder.binding.ivUser)
    }

    override fun getItemCount() = imagesDiff.currentList.size


    class ImageViewHolder(val binding: ItemImageBinding): ViewHolder(binding.root)
}
