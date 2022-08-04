package com.absut.newsapiclient.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.databinding.NewsListItemBigBinding
import com.absut.newsapiclient.databinding.NewsListItemBinding
import com.absut.newsapiclient.utils.Utils
import com.bumptech.glide.Glide

class NewsListAdapter(private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_REGULAR = 1
        const val VIEW_TYPE_BIG = 2
    }

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_REGULAR) {
            val binding =
                NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsListViewHolder(binding)
        }

        val binding =
            NewsListItemBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsListViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = differ.currentList[position]

        if (viewType == VIEW_TYPE_REGULAR) {
            (holder as NewsListViewHolder).bind(article)
        } else {
            (holder as NewsListViewHolder2).bind(article)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewType == 1) VIEW_TYPE_REGULAR
        else VIEW_TYPE_BIG
    }

    inner class NewsListViewHolder(val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleDto: Article) {
            binding.txtTitle.text = articleDto.title
            binding.txtSource.text = articleDto.source?.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.txtDate.text = articleDto.publishedAt?.let {Utils.getFormattedDate(it)}
            } else {
                binding.txtDate.text = articleDto.publishedAt
            }


            Glide.with(binding.imageView.context).load(articleDto.urlToImage).into(binding.imageView)

            binding.root.setOnClickListener {
                itemClickListener?.let {
                    it(articleDto)
                }
            }
        }
    }

    inner class NewsListViewHolder2(val binding: NewsListItemBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleDto: Article) {
            binding.txtTitle.text = articleDto.title
            binding.txtSource.text = articleDto.source?.name
            binding.txtDesc.text = articleDto.description

            Glide.with(binding.imageView.context).load(articleDto.urlToImage).into(binding.imageView)

            binding.root.setOnClickListener {
                itemClickListener?.let {
                    it(articleDto)
                }
            }
        }
    }


    private var itemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        itemClickListener = listener
    }
}

