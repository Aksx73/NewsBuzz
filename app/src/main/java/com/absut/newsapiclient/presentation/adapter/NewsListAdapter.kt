package com.absut.newsapiclient.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.databinding.NewsListItemBigBinding
import com.absut.newsapiclient.databinding.NewsListItemBinding
import com.absut.newsapiclient.utils.Utils

class NewsListAdapter(private val viewType: Int) : ListAdapter<Article,RecyclerView.ViewHolder>(callback) {

    companion object {
        const val VIEW_TYPE_REGULAR = 1
        const val VIEW_TYPE_BIG = 2

        //DiffUtils
        private val callback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

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
        val article = getItem(position)

        if (viewType == VIEW_TYPE_REGULAR) {
            (holder as NewsListViewHolder).bind(article)
        } else {
            (holder as NewsListViewHolder2).bind(article)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewType == 1) VIEW_TYPE_REGULAR
        else VIEW_TYPE_BIG
    }

    inner class NewsListViewHolder(val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.txtTitle.text = article.title
            binding.txtSource.text = article.source?.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.txtDate.text = article.publishedAt?.let {Utils.getFormattedDate(it)}
            } else {
                binding.txtDate.text = article.publishedAt
            }

            binding.imageView.load(article.urlToImage){
                crossfade(true)
            }

            binding.root.setOnClickListener {
                itemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    inner class NewsListViewHolder2(val binding: NewsListItemBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.txtTitle.text = article.title
            binding.txtSource.text = article.source?.name
            binding.txtDesc.text = article.description

            binding.imageView.load(article.urlToImage){
                crossfade(true)
            }

            binding.root.setOnClickListener {
                itemClickListener?.let {
                    it(article)
                }
            }
        }
    }


    private var itemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        itemClickListener = listener
    }
}

