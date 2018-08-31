package com.sandra.dupre.nicegallery.detail.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandra.dupre.business.detail.DetailInteractor
import com.sandra.dupre.niceDetail.detail.DaggerDetailComponent
import com.sandra.dupre.niceDetail.detail.DetailModule
import com.sandra.dupre.nicegallery.MainDependencies
import com.sandra.dupre.nicegallery.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

interface DetailView {
    fun displayPicture(urls: List<String>)
    fun displayFinish()
    fun stopLoadPictures()
}

class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        private const val EXTRA_POSITION = "extra_position"

        fun newIntent(context: Context, position: Int): Intent =
                Intent(context, DetailActivity::class.java).putExtra(EXTRA_POSITION, position)
    }

    @Inject
    lateinit var interactor: DetailInteractor

    private val adapter = DetailAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            linearLayoutManager.apply {
                val firstItem = findFirstVisibleItemPosition()
                if (job != null && childCount + firstItem >= itemCount && firstItem >= 0) {
                    position = firstItem
                    load()
                }
            }
        }
    }

    private var job: Job? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        DaggerDetailComponent.builder()
                .mainComponent(MainDependencies.instance.mainComponent)
                .detailModule(DetailModule(this))
                .build()
                .inject(this)

        position = intent.getIntExtra(EXTRA_POSITION, 0)
        initDetail()
        load()
    }

    override fun displayPicture(urls: List<String>) {
        launch(UI) {
            adapter.replace(urls)
            job = null
            linearLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    override fun displayFinish() {
        launch(UI) {
            finish()
        }
    }

    override fun stopLoadPictures() {
        launch(UI) {
            detaiRecyclerView.removeOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    private fun load() {
        job = launch(CommonPool) {
            interactor.pickPictures()
        }
    }

    private fun initDetail() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detaiRecyclerView.layoutManager = linearLayoutManager
        detaiRecyclerView.adapter = adapter
        detaiRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }
}
