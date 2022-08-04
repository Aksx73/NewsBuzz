package com.absut.newsapiclient.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.absut.newsapiclient.R
import com.absut.newsapiclient.databinding.FragmentSearchBinding
import com.absut.newsapiclient.presentation.MainActivity
import com.absut.newsapiclient.utils.Resource
import com.absut.newsapiclient.presentation.adapter.NewsListAdapter
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.absut.newsapiclient.utils.Constants
import com.absut.newsapiclient.utils.Utils
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsListAdapter
    val country = Constants.QUERY_COUNTRY
    val page = Constants.QUERY_PAGE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as MainActivity).viewModel
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        newsAdapter = NewsListAdapter(NewsListAdapter.VIEW_TYPE_REGULAR)

        newsAdapter.setOnItemClickListener {
            viewModel.selectItem(it)
            val action = SearchFragmentDirections.actionSearchFragmentToNewsDetailFragment()
            findNavController().navigate(action)
        }

        initRecyclerView()
        viewSearchedList()

        binding.searchIcon.setOnClickListener {
            Utils.hideKeyboard(requireActivity())
            if (!binding.etSearch.text.isNullOrEmpty()) {
                val query = binding.etSearch.text.toString()

                viewModel.searchNews()
               // viewSearchedList()
            }
        }

        binding.searchTextLayout.setEndIconOnClickListener {
            //todo clear edittext values and empty recycler view
            binding.etSearch.text!!.clear()
            viewModel.searchedNews.value = null
            newsAdapter.submitList(emptyList())
            binding.textView.visibility = View.GONE
        }

    }

    private fun initRecyclerView() {
        //  newsAdapter = NewsListAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }


    /*  private fun setSearchView() {
          binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
              override fun onQueryTextSubmit(query: String?): Boolean {
                  viewModel.searchNews(country,query.toString(),page)
                  viewSearchedList()

                  return false
              }

              override fun onQueryTextChange(newText: String?): Boolean {
                  MainScope().launch {
                      delay(2000)
                      viewModel.searchNews(country,newText.toString(),page)
                      viewSearchedList()
                  }
                  return false
              }
          })

          binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener{
              override fun onClose(): Boolean {
                  initRecyclerView()
                  viewNewsList()

                  return false
              }

          })

      }*/

    private fun viewSearchedList() {
        viewModel.searchedNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    it.data?.let { apiResponse ->
                        newsAdapter.submitList(apiResponse.articles.toList())
                        binding.textView.visibility = View.VISIBLE
                        binding.textView.text = "Results for \"${viewModel.searchQuery.value.toString()}\""
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    binding.textView.visibility = View.GONE
                    Snackbar.make(
                        binding.recyclerView,
                        "Error occurred: ${it.message.toString()}",
                        Snackbar.LENGTH_SHORT
                    ).setAnchorView((activity as MainActivity).bottomNav).show()
                }
                is Resource.Loading -> {
                    binding.textView.visibility = View.GONE
                    newsAdapter.submitList(emptyList())  // -> to avoid showing previous search result while loading search for next query
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