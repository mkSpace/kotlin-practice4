package com.mashup.kotlin_practice4

import androidx.hilt.lifecycle.ViewModelInject
import com.mashup.kotlin_practice4.scheduler.BaseSchedulerProvider
import io.reactivex.Flowable

class SampleViewModel @ViewModelInject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    companion object {
        private val SAMPLE_ITEMS = listOf(SampleAdapterItem, SampleAdapterItem, SampleAdapterItem)
    }

    val items: Flowable<List<SampleAdapterItem>> = Flowable.just(SAMPLE_ITEMS).subscribeOnIO()
}