package com.absut.newsapiclient.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absut.newsapiclient.R
import com.absut.newsapiclient.databinding.FragmentSavedNewsBinding
import com.absut.newsapiclient.presentation.MainActivity
import com.absut.newsapiclient.presentation.adapter.NewsListAdapter
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = NewsListAdapter(NewsListAdapter.VIEW_TYPE_REGULAR)

        newsAdapter.setOnItemClickListener {
            viewModel.selectItem(it)
            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToNewsDetailFragment()
            findNavController().navigate(action)
        }

        initRecyclerView()

        //to get saved news from view model
        viewModel.getSavedNewsList().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            if(newsAdapter.itemCount == 0) binding.emptyView.visibility = View.VISIBLE
            else binding.emptyView.visibility = View.GONE
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                viewModel.deleteNews(article)
                Snackbar.make(view, "Successfully deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.saveNews(article)
                    }.setAnchorView((activity as MainActivity).bottomNav).show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}