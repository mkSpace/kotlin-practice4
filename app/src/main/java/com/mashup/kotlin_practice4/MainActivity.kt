package com.mashup.kotlin_practice4

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SampleViewModel>()
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private lateinit var adapter: SampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SampleAdapter()
        setUpViews()
        bindViewModels()
    }

    private fun setUpViews() {
        recyclerView.adapter = adapter
    }

    private fun bindViewModels() {
        viewModel.items
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.submitList(it)
                }
                .addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}