package dev.olog.msc.domain.interactor.base

import dev.olog.msc.domain.executors.Schedulers
import io.reactivex.Flowable

abstract class FlowableUseCaseWithParam<T, in Param>(
        private val schedulers: Schedulers
) {

    protected abstract fun buildUseCaseObservable(param: Param): Flowable<T>

    fun execute(param: Param): Flowable<T> {
        return Flowable.defer { this.buildUseCaseObservable(param)
                .subscribeOn(schedulers.worker)
                .observeOn(schedulers.ui) }
                .doOnError { it.printStackTrace() }
    }

}