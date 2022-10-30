package com.anastr.pixabaydemo.ui.adpter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anastr.pixabaydemo.data.model.ImageInfo

class ImagesDiffCallback(private val adapter: RecyclerView.Adapter<*>): DiffUtil.Callback() {

    private var oldList = listOf<ImageInfo>()
    var currentList = listOf<ImageInfo>()
        private set

    fun updateList(newList: List<ImageInfo>) {
        oldList = currentList
        currentList = newList
        DiffUtil.calculateDiff(this).dispatchUpdatesTo(adapter)
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = currentList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == currentList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == currentList[newItemPosition]
}