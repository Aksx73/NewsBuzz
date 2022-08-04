package com.absut.newsapiclient.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.absut.newsapiclient.R
import com.absut.newsapiclient.databinding.FragmentHeadlinesBinding
import com.absut.newsapiclient.presentation.MainActivity
import com.absut.newsapiclient.utils.Resource
import com.absut.newsapiclient.presentation.adapter.NewsListAdapter
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.absut.newsapiclient.utils.Constants
import com.google.android.material.snackbar.Snackbar

class HeadlineFragment : Fragment() {

    private lateinit var binding: FragmentHeadlinesBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsListAdapter
    val country = Constants.QUERY_COUNTRY
    val page = Constants.QUERY_PAGE
    private var selectedChip = Constants.QUERY_CATEGORY_TECHNOLOGY


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHeadlinesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        newsAdapter = NewsListAdapter(NewsListAdapter.VIEW_TYPE_REGULAR)

        newsAdapter.setOnItemClickListener {

           /* val action = HeadlineFragmentDirections.actionHeadlineFragmentToNewsDetailFragment(it)
            findNavController().navigate(action)*/

            viewModel.selectItem(it)
            val action = HeadlineFragmentDirections.actionHeadlineFragmentToNewsDetailFragment()
            findNavController().navigate(action)

        }

        initRecyclerView()
        viewNewsByCategory()
        // setSearchView()

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_technology -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_TECHNOLOGY
                    viewNewsByCategory()
                }
                R.id.chip_science -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_SCIENCE
                    viewNewsByCategory()
                }
                R.id.chip_sports -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_SPORTS
                    viewNewsByCategory()
                }
                R.id.chip_business -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_BUSINESS
                    viewNewsByCategory()
                }
                R.id.chip_health -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_HEALTH
                    viewNewsByCategory()
                }
                R.id.chip_entertainemnt -> {
                    viewModel.currentCategory.value = Constants.QUERY_CATEGORY_ENTERTAINMENT
                    viewNewsByCategory()
                }
            }
        }


    }

    private fun initRecyclerView() {
        //  newsAdapter = NewsListAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

    private fun viewNewsByCategory() {
        viewModel.getNewsHeadlinesByCategory(country, page)
        viewModel.newsHeadlinesCategory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    it.data?.let {
                        newsAdapter.submitList(it.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    Snackbar.make(binding.recyclerView, "Error occurred: ${it.message.toString()}", Snackbar.LENGTH_SHORT)
                        .setAnchorView((activity as MainActivity).bottomNav)
                        .show()
                }
                is Resource.Loading -> {
                    // newsAdapter.differ.submitList(emptyList())  // -> this line just to have empty screen while loading next data
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

/*  override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
  }*/
}
