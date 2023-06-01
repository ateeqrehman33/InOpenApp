package com.testinopenapp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.testinopenapp.data.model.dashboard.TopLink
import com.testinopenapp.databinding.ItemLinkBinding
import com.testinopenapp.utils.loadUrl


class LinkListAdapter() :

    ListAdapter<TopLink, RecyclerView.ViewHolder>(
        AsyncDifferConfig.Builder(
            DiffCallback,
        ).build(),
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val itemlinkBinding = ItemLinkBinding.inflate(inflater, parent, false)
        return MaleViewHolder(itemlinkBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val link = currentList[position]
        when (holder) {
            is MaleViewHolder -> holder.bind(link)
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<TopLink>() {
        override fun areItemsTheSame(
            oldItem: TopLink,
            newItem: TopLink,
        ): Boolean {
            return oldItem.domain_id == newItem.domain_id
        }

        override fun areContentsTheSame(
            oldItem: TopLink,
            newItem: TopLink,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class MaleViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(link: TopLink) {
            binding.apply {

                tvLinkName.text = link.title
                tvLink.text = link.smart_link
                tvLinkDatetime.text = link.created_at
                clickstv.text = link.total_clicks.toString()
                ivLinkimage.loadUrl(link.original_image)
            }

        }
    }


}