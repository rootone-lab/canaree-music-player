package dev.olog.msc.presentation.about

import android.arch.lifecycle.Lifecycle
import android.databinding.ViewDataBinding
import android.support.v4.content.ContextCompat
import dagger.Lazy
import dev.olog.msc.BR
import dev.olog.msc.R
import dev.olog.msc.dagger.qualifier.ActivityLifecycle
import dev.olog.msc.presentation.base.adapter.AbsAdapter
import dev.olog.msc.presentation.base.adapter.DataBoundViewHolder
import dev.olog.msc.presentation.model.DisplayableItem
import dev.olog.msc.presentation.navigator.NavigatorAbout
import dev.olog.msc.utils.k.extension.setOnClickListener
import kotlinx.android.synthetic.main.item_about.view.*
import javax.inject.Inject


class AboutActivityAdapter @Inject constructor(
        @ActivityLifecycle lifecycle: Lifecycle,
        private val navigator: Lazy<NavigatorAbout>

) : AbsAdapter<DisplayableItem>(lifecycle) {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
        viewHolder.setOnClickListener(controller) { item, _, _ ->
            when (item.mediaId){
                AboutActivityPresenter.AUTHOR_ID -> navigator.get().toFacebook()
                AboutActivityPresenter.THIRD_SW_ID -> navigator.get().toLicensesFragment()
                AboutActivityPresenter.SPECIAL_THANKS_ID -> navigator.get().toSpecialThanksFragment()
                AboutActivityPresenter.RATE_ID -> navigator.get().toMarket()
                AboutActivityPresenter.REPORT_BUGS -> navigator.get().reportBugs()
                AboutActivityPresenter.WEBSITE_ID -> navigator.get().toWebsite()
                AboutActivityPresenter.PRIVACY_POLICY -> navigator.get().toPrivacyPolicy()
            }
        }
    }

    override fun bind(binding: ViewDataBinding, item: DisplayableItem, position: Int) {
        binding.setVariable(BR.item, item)
        if (position == 0){
            val color = ContextCompat.getColor(binding.root.context, R.color.accent)
            binding.root.subtitle.setTextColor(color)
        }
    }

}