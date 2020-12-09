package com.mashup.kotlin_practice4

import androidx.hilt.lifecycle.ViewModelInject
import com.mashup.kotlin_practice4.scheduler.BaseSchedulerProvider
import io.reactivex.Flowable

class SampleViewModel @ViewModelInject constructor(
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(schedulerProvider) {

    companion object {
        private val SAMPLE_ITEMS =
            listOf(
                SampleAdapterItem.Text(0, "hello"),
                SampleAdapterItem.Text(1, "My name is"),
                SampleAdapterItem.Text(2, "Jaemin"),
                SampleAdapterItem.Image(
                    3,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScgioXP0bhnueOQVYBNz2C3lij0JdlffnOyw&usqp=CAU"
                )
            )
    }

    val items: Flowable<List<SampleAdapterItem>> = Flowable.just(SAMPLE_ITEMS).subscribeOnIO()
}