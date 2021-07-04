package com.anggit97.home.tab

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.isScrolling
import com.anggit97.core.ext.isTop
import com.anggit97.core.ui.base.OnBackPressedListener


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
abstract class HomeTabFragment : Fragment, OnBackPressedListener {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    abstract fun scrollToTop()

    protected fun RecyclerView.scrollToTopInternal(force: Boolean = false): Boolean {
        if (isTop().not()) {
            if (isScrolling()) {
                stopScroll()
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
            return true
        }
        if (force) {
            stopScroll()
            scrollToPosition(0)
        }
        return false
    }
}