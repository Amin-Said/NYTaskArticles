package com.am.taskarticles.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.taskarticles.databinding.FragmentDetailsBinding
import com.am.taskarticles.helper.getProgressDrawable
import com.am.taskarticles.helper.loadImage
import com.am.taskarticles.model.Article

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    var article: Article?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            article= DetailsFragmentArgs.fromBundle(it).article
        }

        (activity as MainActivity?)?.setActionBarTitle((article?.title))


        context?.let {
            if (article?.media?.size!! >0){
                if (article!!.media?.get(0)?.mediaMetadata?.size!! >0){
                    val size = article?.media?.get(0)?.mediaMetadata?.size
                    binding.articleIv.loadImage(article?.media?.get(0)?.mediaMetadata?.get(size?.minus(1)!!)?.url, getProgressDrawable(it))
                }
            }
        }

        binding.articleTitle.text = article?.title
        binding.articleDate.text = article?.publishedDate
        binding.articleAuthor.text = article?.byline
        binding.articleDetails.text = article?.abstract

    }

}