package dev.olog.msc.presentation.library.tab.span.size.lookup

import android.content.Context
import dev.olog.msc.utils.k.extension.configuration

class BaseSpanSizeLookup(
        context: Context,
        private val isPortrait: Boolean

) : AbsSpanSizeLookup() {

    private val smallestWidthDip = context.configuration.smallestScreenWidthDp
    private val isTablet = smallestWidthDip >= 600

    override fun getSpanSize(position: Int): Int {
        var span = if (isPortrait) 3 else 4

        if (isTablet) {
            span++
        }

        return spanCount / span
    }
}