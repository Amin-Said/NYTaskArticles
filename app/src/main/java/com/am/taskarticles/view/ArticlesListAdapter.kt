package com.am.taskarticles.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.taskarticles.databinding.ArticleItemBinding
import com.am.taskarticles.helper.getProgressDrawable
import com.am.taskarticles.helper.loadImage
import com.am.taskarticles.model.Article

class ArticlesListAdapter(
    private val articleList: ArrayList<Article>,private var mArticleListener: OnArticlesListener
) : RecyclerView.Adapter<ArticlesListAdapter.RecyclerViewHolder>() {

        override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(
            ArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),mArticleListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val itemArticle = articleList[position]
        holder.bindArticle(itemArticle)
    }


    override fun getItemCount(): Int = articleList.size


    class RecyclerViewHolder(private val binding: ArticleItemBinding, private var mListener: OnArticlesListener)
        : RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(article: Article) {
            binding.articleAuthorTv.text = article.byline
            binding.articleDateTv.text = article.publishedDate
            binding.articleDetailsTv.text = article.abstract
            binding.articleTitleTv.text = article.title
           if (article.media?.size!! >0){
               if (article.media[0].mediaMetadata?.size!! >0){
                   binding.articleIv.loadImage(article.media[0].mediaMetadata?.get(0)?.url, getProgressDrawable(binding.root.context))
               }
           }

            binding.root.setOnClickListener{ mListener.onArticleClick(adapterPosition,it) }

        }


    }

    fun updateArticlesList(newArticlesList: List<Article>) {
        articleList.clear()
        articleList.addAll(newArticlesList)
        notifyDataSetChanged()

    }

    interface OnArticlesListener {
        fun onArticleClick(pos: Int,view:View)
    }


}


