package com.anggit97.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.loadAsync
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.detail.databinding.*

private typealias OnItemClickListener = (Int) -> Unit

internal class DetailListAdapter(
    onItemClick: (ContentItemUiModel) -> Unit
) : ListAdapter<ContentItemUiModel, DetailListAdapter.ViewHolder>(IdBasedDiffCallback { it.id }) {

    private val itemListener: OnItemClickListener = { position ->
        when (val item = getItem(position)) {
            is PlotItemUiModel -> {
                item.isExpanded = item.isExpanded.not()
                notifyItemChanged(position)
            }
            else -> onItemClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.detail_item_header ->
                HeaderViewHolder(DetailItemHeaderBinding.inflate(layoutInflater, parent, false))
            R.layout.detail_item_box_office ->
                BoxOfficeViewHolder(
                    DetailItemBoxOfficeBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ), itemListener
                )
            R.layout.detail_item_cgv ->
                CgvViewHolder(
                    DetailItemCgvBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_lotte ->
                LotteViewHolder(
                    DetailItemLotteBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_megabox ->
                MegaboxViewHolder(
                    DetailItemMegaboxBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_naver ->
                NaverViewHolder(
                    DetailItemNaverBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_imdb ->
                ImdbViewHolder(
                    DetailItemImdbBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_plot ->
                PlotViewHolder(
                    DetailItemPlotBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_cast ->
                CastViewHolder(DetailItemCastBinding.inflate(layoutInflater, parent, false))
            R.layout.detail_item_trailer_header ->
                TrailerHeaderViewHolder(
                    DetailItemTrailerHeaderBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ), itemListener
                )
            R.layout.detail_item_trailer ->
                TrailerViewHolder(
                    DetailItemTrailerBinding.inflate(layoutInflater, parent, false),
                    itemListener
                )
            R.layout.detail_item_trailer_footer ->
                TrailerFooterViewHolder(
                    DetailItemTrailerFooterBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ), itemListener
                )
            else -> throw IllegalArgumentException("This is not valid type.")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            if (headerHeight > 0) {
                holder.itemView.updateLayoutParams { height = headerHeight }
            }
        } else {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is HeaderItemUiModel -> R.layout.detail_item_header
        is BoxOfficeItemUiModel -> R.layout.detail_item_box_office
        is CgvItemUiModel -> R.layout.detail_item_cgv
        is LotteItemUiModel -> R.layout.detail_item_lotte
        is MegaboxItemUiModel -> R.layout.detail_item_megabox
        is NaverItemUiModel -> R.layout.detail_item_naver
        is ImdbItemUiModel -> R.layout.detail_item_imdb
        is PlotItemUiModel -> R.layout.detail_item_plot
        is CastItemUiModel -> R.layout.detail_item_cast
        is TrailerHeaderItemUiModel -> R.layout.detail_item_trailer_header
        is TrailerFooterItemUiModel -> R.layout.detail_item_trailer_footer
        is TrailerItemUiModel -> R.layout.detail_item_trailer
    }

    fun getSpanSize(position: Int): Int = when (getItem(position)) {
        is CgvItemUiModel,
        is LotteItemUiModel,
        is MegaboxItemUiModel -> 1
        else -> 3
    }

    private var headerHeight: Int = 0

    fun updateHeader(height: Int) {
        headerHeight = height
        notifyItemChanged(0)
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(item: ContentItemUiModel) {}
    }

    class HeaderViewHolder(
        binding: DetailItemHeaderBinding
    ) : ViewHolder(binding.root)

    class BoxOfficeViewHolder(
        private val binding: DetailItemBoxOfficeBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is BoxOfficeItemUiModel) return
            val context = itemView.context
            binding.rankText.text = context.getString(R.string.rank, item.rank)
            binding.rankDescription.text = context.getString(R.string.rank_date, item.rankDate)
            binding.audienceText.text = context.getString(R.string.audience, item.audience)
            binding.audienceDescription.text =
                context.getString(R.string.screen_days, item.screenDays)
            binding.ratingText.text = item.rating
        }
    }

    class CgvViewHolder(
        private val binding: DetailItemCgvBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is CgvItemUiModel) return
            itemView.isEnabled = item.hasInfo
            binding.eggLabel.text = item.rating
        }
    }

    class LotteViewHolder(
        private val binding: DetailItemLotteBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is LotteItemUiModel) return
            itemView.isEnabled = item.hasInfo
            binding.eggLabel.text = item.rating
        }
    }

    class MegaboxViewHolder(
        private val binding: DetailItemMegaboxBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is MegaboxItemUiModel) return
            itemView.isEnabled = item.hasInfo
            binding.eggLabel.text = item.rating
        }
    }

    class NaverViewHolder(
        private val binding: DetailItemNaverBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            binding.brandView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
            binding.infoButton.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is NaverItemUiModel) return
            binding.eggLabel.text = item.rating
        }
    }

    class ImdbViewHolder(
        private val binding: DetailItemImdbBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is ImdbItemUiModel) return
            binding.imdbRatingText.text = item.imdb
            binding.rtRatingIcon.setTomatoMeterIcon(item.rottenTomatoes)
            binding.rtRatingText.text = item.rottenTomatoes
            binding.metascoreRatingText.text = item.metascore
        }
    }

    class PlotViewHolder(
        private val binding: DetailItemPlotBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is PlotItemUiModel) return
            binding.moreIcon.isSelected = item.isExpanded
            binding.shortPlotText.isGone = item.isExpanded
            binding.shortPlotText.text = item.plot
            binding.longPlotText.isVisible = item.isExpanded
            binding.longPlotText.text = item.plot
        }
    }

    class CastViewHolder(
        binding: DetailItemCastBinding
    ) : ViewHolder(binding.root) {

        private val adapter = DetailPersonListAdapter()

        init {
            binding.root.adapter = adapter
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is CastItemUiModel) return
            adapter.submitList(item.persons)
        }
    }

    class TrailerHeaderViewHolder(
        private val binding: DetailItemTrailerHeaderBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            binding.privacyTip.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is TrailerHeaderItemUiModel) return
            binding.searchLabel.text =
                itemView.context.getString(R.string.trailer_search_result, item.movieTitle)
        }
    }

    class TrailerViewHolder(
        private val binding: DetailItemTrailerBinding,
        private val listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }

        override fun bind(item: ContentItemUiModel) {
            if (item !is TrailerItemUiModel) return
            binding.trailerImage.loadAsync("https://img.youtube.com/vi/${item.trailer.key}/0.jpg")
            binding.titleView.text = item.trailer.name
            binding.authorView.text = item.trailer.site
        }
    }

    class TrailerFooterViewHolder(
        binding: DetailItemTrailerFooterBinding,
        listener: OnItemClickListener
    ) : ViewHolder(binding.root) {

        init {
            itemView.setOnDebounceClickListener {
                listener(bindingAdapterPosition)
            }
        }
    }
}
