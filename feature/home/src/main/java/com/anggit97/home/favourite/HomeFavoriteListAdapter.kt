package com.anggit97.home.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.consume
import com.anggit97.core.ext.loadAsync
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.home.R
import com.anggit97.home.databinding.HomeItemMovieBinding
import com.anggit97.model.model.Movie

class HomeFavoriteListAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Movie> = IdBasedDiffCallback { it.movieId.toString() },
    private val listener: (Movie, Array<Pair<View, String>>) -> Unit
) : PagingDataAdapter<Movie, HomeFavoriteListAdapter.MovieViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = HomeItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding).apply {
            itemView.setOnDebounceClickListener(delay = 150L) {
                val index = bindingAdapterPosition
                if (index in 0..itemCount) {
                    val movie: Movie? = getItem(index)
                    movie?.let { it1 -> listener(it1, createSharedElements(movie)) }
                }
            }
            itemView.setOnLongClickListener {
                consume {
                    val index = bindingAdapterPosition
                    if (index in 0..itemCount) {
                        val movie: Movie? = getItem(index)
                        it?.context?.showToast(movie?.title ?: "-")
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int) = R.layout.home_item_movie

    private fun MovieViewHolder.createSharedElements(movie: Movie): Array<Pair<View, String>> {
        itemView.run {
            val sharedElements = mutableListOf(
                backgroundView to R.string.transition_background,
                posterView to R.string.transition_poster,
            )
            return sharedElements.toTypedArray()
        }
    }

    private infix fun View.to(@StringRes tagId: Int): Pair<View, String> {
        return Pair(this, context.getString(tagId))
    }

    class MovieViewHolder(private val binding: HomeItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val backgroundView = binding.backgroundView
        val posterView = binding.posterView

        fun bind(item: Movie) {
            binding.container.tag = item.movieId
            posterView.loadAsync(item.getPosterUrl(), R.drawable.bg_on_surface_dim)
            posterView.contentDescription = item.title
        }
    }
}
