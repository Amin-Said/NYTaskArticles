package com.am.taskarticles.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.taskarticles.R
import com.am.taskarticles.databinding.FragmentListBinding
import com.am.taskarticles.helper.ConfigHelper.KEY_VALUE
import com.am.taskarticles.helper.EspressoIdlingResource
import com.am.taskarticles.helper.Resource
import com.am.taskarticles.helper.Status
import com.am.taskarticles.model.Article
import com.am.taskarticles.model.ResponseBase
import com.am.taskarticles.viewmodel.MainViewModel

class ListFragment : Fragment(), ArticlesListAdapter.OnArticlesListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mArticleList: MutableList<Article>

    private val mAdapter = ArticlesListAdapter(arrayListOf(), this)

    private val responseDataObserver = Observer<Resource<ResponseBase>> { it ->
        it?.let {resource ->
           when(resource.status){
               Status.SUCCESS ->{
                       EspressoIdlingResource.decrement()

                   binding.progressBar.visibility = View.GONE
                   binding.errorTv.visibility = View.GONE
                   resource.data?.let {
                       if (it.results.isNullOrEmpty()){
                           binding.noDataTv.visibility = View.VISIBLE
                       }else{
                           binding.articlesRv.visibility = View.VISIBLE
                           mArticleList = it.results.toMutableList()
                           mAdapter.updateArticlesList(it.results)
                       }
                   }

               }

               Status.LOADING ->{
                   EspressoIdlingResource.increment()

                   binding.progressBar.visibility = View.VISIBLE
                   binding.articlesRv.visibility = View.GONE
                   binding.errorTv.visibility = View.GONE
                   binding.noDataTv.visibility = View.GONE

               }
               Status.ERROR ->{
                   binding.progressBar.visibility = View.GONE
                   binding.articlesRv.visibility = View.GONE
                   binding.errorTv.visibility = View.VISIBLE
                   binding.noDataTv.visibility = View.GONE
               }

           }
       }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.setActionBarTitle(getString(R.string.app_name))
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        viewModel.getResponse(KEY_VALUE).observe(viewLifecycleOwner, responseDataObserver)

        binding.articlesRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onArticleClick(pos: Int, view: View) {
        val clickedArticle = mArticleList[pos]
        val action = ListFragmentDirections.actionGoToDetails(clickedArticle)
        Navigation.findNavController(view).navigate(action)
    }

}