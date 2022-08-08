package com.absut.newsapiclient.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absut.newsapiclient.R
import com.absut.newsapiclient.databinding.FragmentForYouBinding
import com.absut.newsapiclient.presentation.MainActivity
import com.absut.newsapiclient.utils.Resource
import com.absut.newsapiclient.presentation.adapter.NewsListAdapter
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ForYouFragment : Fragment() {


    private lateinit var binding: FragmentForYouBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_for_you, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForYouBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsListAdapter(NewsListAdapter.VIEW_TYPE_BIG)

        newsAdapter.setOnItemClickListener {

            viewModel.selectItem(it)
            val action = ForYouFragmentDirections.actionForYouFragmentToNewsDetailFragment()
            findNavController().navigate(action)

        }

        newsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        initRecyclerView()

        viewNewsList()

    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun viewNewsList() {
        //viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    it.data?.let { newsAdapter.submitList(it.articles.toList()) }
                }
                is Resource.Error -> {
                    hideProgress()
                    Snackbar.make(binding.recyclerView, "Error occurred: ${it.message.toString()}", Snackbar.LENGTH_SHORT)
                        .setAnchorView((activity as MainActivity).bottomNav)
                        .show()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }
    }

    private fun showProgress() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressCircular.visibility = View.INVISIBLE
    }


}