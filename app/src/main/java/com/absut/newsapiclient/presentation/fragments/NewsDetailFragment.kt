package com.absut.newsapiclient.presentation.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.absut.newsapiclient.R
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.databinding.FragmentNewsDetailBinding
import com.absut.newsapiclient.presentation.MainActivity
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var viewModel: NewsViewModel

    //  private val args by navArgs<NewsDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewsDetailBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel


       viewModel.selectedArticle.observe(viewLifecycleOwner){article ->
           article?.let {
               setUpWebView(article)
           }
       }

        binding.fabSave.setOnClickListener {
            val article: Article? = viewModel.selectedArticle.value
            if (article != null)
            viewModel.saveNews(article)
            Snackbar.make(binding.fabSave, "Saved successfully", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setUpWebView(article: Article){
        binding.webView.apply {
            settings.javaScriptEnabled
            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    binding.progressCircular.visibility = View.GONE
                }

                override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    Snackbar.make(binding.webView, "Error code: $errorCode, Description: $description", Snackbar.LENGTH_SHORT).setAnchorView(binding.fabSave).show()
                }
            }
            if (article.url != null) {
                loadUrl(article.url)
            } else {
                Snackbar.make(this, "Empty url", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

}