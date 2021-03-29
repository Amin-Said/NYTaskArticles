package com.am.taskarticles.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.taskarticles.R
import com.am.taskarticles.databinding.FragmentListBinding
import com.am.taskarticles.helper.ConfigHelper.KEY_VALUE
import com.am.taskarticles.model.Article
import com.am.taskarticles.viewmodel.MainViewModel


class ListFragment : Fragment(), ArticlesListAdapter.OnArticlesListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    lateinit var mArticleList: MutableList<Article>


    private val mAdapter = ArticlesListAdapter(arrayListOf(), this)
    private val articlesListDataObserver = Observer<List<Article>> { list ->
        list?.let {
            binding.articlesRv.visibility = View.VISIBLE
            mArticleList = it.toMutableList()
            mAdapter.updateArticlesList(it)

        }


    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            binding.articlesRv.visibility = View.GONE
            binding.errorTv.visibility = View.GONE
        }


    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        binding.errorTv.visibility = if (isError) View.VISIBLE else View.GONE

    }

    private val noDataLiveDataObserver = Observer<Boolean> { noData ->
        binding.noDataTv.visibility = if (noData) View.VISIBLE else View.GONE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.setActionBarTitle(getString(R.string.app_name))
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.articles.observe(viewLifecycleOwner, articlesListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.noData.observe(viewLifecycleOwner, noDataLiveDataObserver)


        viewModel.loadData(KEY_VALUE)


        binding.articlesRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onArticleClick(pos: Int, view: View) {
        var clickedArticle = mArticleList.get(pos)
        val action = ListFragmentDirections.actionGoToDetails(clickedArticle)
        Navigation.findNavController(view).navigate(action)
    }

}