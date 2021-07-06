package com.anggit97.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.loadAsync
import com.anggit97.core.ext.observeEvent
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.clipToOval
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.core.util.spanSizeLookup
import com.anggit97.core.util.viewBindings
import com.anggit97.core.widget.ElasticDragDismissFrameLayout
import com.anggit97.detail.databinding.ActivityDetailBinding
import com.anggit97.stfalcon_imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), DetailViewRenderer, DetailViewAnimation {

    private val binding: ActivityDetailBinding by viewBindings(ActivityDetailBinding::inflate)

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailActivityArgs by navArgs()

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            binding.header.root.run {
                val maxOffset = max(
                    height,
                    recyclerView.resources.getDimensionPixelSize(R.dimen.detail_header_height)
                )
                val headerIsShown =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() == 0
                val offset = if (headerIsShown) {
                    min(maxOffset, recyclerView.computeVerticalScrollOffset()).toFloat()
                } else {
                    maxOffset.toFloat()
                }
                translationY = -offset
            }
        }
    }

    private val chromeFader = object : ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {

        override fun onDragDismissed() {
            finishAfterTransition()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        Insetter.builder()
            .setOnApplyInsetsListener { container, insets, initialState ->
                val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                container.updatePadding(
                    left = initialState.paddings.left + systemInsets.left,
                    right = initialState.paddings.right + systemInsets.right,
                )
            }
            .applyToView(binding.container)
        Insetter.builder()
            .setOnApplyInsetsListener { header, insets, initialState ->
                header.updatePadding(top = initialState.paddings.top + insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                ).top)
            }
            .applyToView(binding.header.root)
        Insetter.builder()
            .setOnApplyInsetsListener { listView, insets, initialState ->
                val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                listView.updatePadding(
                    top = initialState.paddings.top + systemWindowInsets.top,
                    bottom = initialState.paddings.bottom + systemWindowInsets.bottom
                )
            }
            .applyToView(binding.listView)
        Insetter.builder()
            .setOnApplyInsetsListener { share, insets, initialState ->
                val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                share.updatePadding(
                    top = initialState.paddings.top + systemWindowInsets.top,
                    bottom = initialState.paddings.bottom + systemWindowInsets.bottom
                )
            }
            .applyToView(binding.share.root)

        postponeEnterTransition()
        initViewState(binding)

        viewModel.init(args.movie)
        viewModel.uiEvent.observeEvent(this) {
            when (it) {
//                is ShareAction -> executeShareAction(it)
                is ToastAction -> showToast(it.resId)
            }
        }
    }


    private fun initViewState(binding: ActivityDetailBinding) {
        binding.header.apply {
            posterView.loadAsync(
                args.movie.getPosterUrl(),
                doOnEnd = {
                    startPostponedEnterTransition()
                }
            )
            posterCard.setOnDebounceClickListener(delay = 150L) {
                showPosterViewer(from = posterView)
            }
            favoriteButton.setOnDebounceClickListener {
                val isFavorite = favoriteButton.isSelected.not()
                viewModel.onFavoriteButtonClick(isFavorite)
            }
            shareButton.setOnDebounceClickListener {
//                analytics.clickShare()
//                binding.toggleShareButton()
            }
        }
        binding.errorRetryButton.setOnDebounceClickListener {
            Timber.d("retry")
            viewModel.onRetryClick()
        }
        binding.share.apply {
            fun onShareClick(target: ShareTarget) {
//                viewModel.requestShareImage(target, movieCard.drawToBitmap())
            }
            root.setOnDebounceClickListener {
//                binding.toggleShareButton()
            }
            facebookShareButton.clipToOval(true)
            facebookShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.Facebook)
            }
            twitterShareButton.clipToOval(true)
            twitterShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.Twitter)
            }
            instagramShareButton.clipToOval(true)
            instagramShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.Instagram)
            }
            lineShareButton.clipToOval(true)
            lineShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.LINE)
            }
            kakaoTalkShareButton.clipToOval(true)
            kakaoTalkShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.KakaoLink)
            }
            etcShareButton.clipToOval(true)
            etcShareButton.setOnDebounceClickListener {
                onShareClick(ShareTarget.Others)
            }
        }
        val listAdapter = DetailListAdapter { item ->
            val ctx: Context = this@DetailActivity
            when (item) {
                is BoxOfficeItemUiModel -> {
//                    ctx.executeWeb(item.webLink)
                }
                is CgvItemUiModel -> {
//                    analytics.clickCgvInfo()
//                    ctx.executeWeb(item.webLink)
                }
                is LotteItemUiModel -> {
//                    analytics.clickLotteInfo()
//                    ctx.executeWeb(item.webLink)
                }
                is MegaboxItemUiModel -> {
//                    analytics.clickMegaboxInfo()
//                    ctx.executeWeb(item.webLink)
                }
                is NaverItemUiModel -> {
//                    ctx.executeWeb(item.webLink)
                }
                is ImdbItemUiModel -> {
//                    ctx.executeWeb(item.webLink)
                }
                is TrailerHeaderItemUiModel -> {
//                    val message = SpannableString(ctx.getText(R.string.trailer_dialog_message))
//                    Linkify.addLinks(message, Linkify.WEB_URLS)
//                    AlertDialog.Builder(ctx, R.style.AlertDialogTheme)
//                        .setTitle(R.string.trailer_dialog_title)
//                        .setMessage(message)
//                        .setPositiveButton(R.string.trailer_dialog_button, null)
//                        .show()
//                        .apply {
//                            findViewById<TextView>(android.R.id.message)?.movementMethod =
//                                LinkMovementMethod.getInstance()
//                        }
                }
                is TrailerFooterItemUiModel -> {
//                    analytics.clickMoreTrailers()
//                    YouTube.executeAppWithQuery(ctx, item.movieTitle)
                }
            }
        }
        binding.listView.apply {
            layoutManager = GridLayoutManager(this@DetailActivity, 3).apply {
                spanSizeLookup = spanSizeLookup(listAdapter::getSpanSize)
            }
            adapter = listAdapter
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = 200
                removeDuration = 200
                supportsChangeAnimations = false
            }
        }
        viewModel.favoriteUiModel.observe(this) { isFavorite ->
            binding.header.favoriteButton.isSelected = isFavorite
        }
        viewModel.headerUiModel.observe(this) {
            binding.render(it)
        }
        viewModel.contentUiModel.observe(this) {
            listAdapter.submitList(it.items)
            listAdapter.updateHeader(height = binding.header.root.measuredHeight)
        }
        viewModel.isError.observe(this) {
            binding.errorGroup.isVisible = it
        }
    }

    private fun showPosterViewer(from: ImageView) {
        StfalconImageViewer
            .Builder(from.context, listOf(args.movie.getPosterUrl())) { view, imageUrl ->
                view.loadAsync(imageUrl)
            }
            .withTransitionFrom(from)
            .withHiddenStatusBar(false)
            .show()
    }

    override fun onResume() {
        super.onResume()
        binding.draggableFrame.addListener(chromeFader)
        binding.listView.addOnScrollListener(scrollListener)
    }

    override fun onPause() {
        binding.draggableFrame.removeListener(chromeFader)
        binding.listView.removeOnScrollListener(scrollListener)
        super.onPause()
    }
}