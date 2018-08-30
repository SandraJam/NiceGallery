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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

interface DetailView {
    fun displayPicture(urls: List<String>, position: Int)
    fun displayFinish()
}

class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        private const val EXTRA_ID = "extra_id"

        fun newIntent(context: Context, id: Int): Intent =
                Intent(context, DetailActivity::class.java).putExtra(EXTRA_ID, id)
    }

    @Inject
    lateinit var interactor: DetailInteractor

    private val adapter = DetailAdapter()

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        DaggerDetailComponent.builder()
                .mainComponent(MainDependencies.instance.mainComponent)
                .detailModule(DetailModule(this))
                .build()
                .inject(this)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detaiRecyclerView.layoutManager = linearLayoutManager
        detaiRecyclerView.adapter = adapter

        launch(CommonPool) {
            interactor.pickPicture(intent.getIntExtra(EXTRA_ID, 0))
        }
    }

    override fun displayPicture(urls: List<String>, position: Int) {
        launch(UI) {
            adapter.replace(urls)
            linearLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    override fun displayFinish() {
        launch(UI) {
            finish()
        }
    }
}