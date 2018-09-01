package com.sandra.dupre.nicegallery.gallery.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandra.dupre.business.gallery.GalleryInteractor
import com.sandra.dupre.nicegallery.MainDependencies
import com.sandra.dupre.nicegallery.R
import com.sandra.dupre.nicegallery.gallery.DaggerGalleryComponent
import com.sandra.dupre.nicegallery.gallery.GalleryModule
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import com.sandra.dupre.nicegallery.detail.view.DetailActivity
import kotlinx.coroutines.experimental.Job
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sandra.dupre.nicegallery.gallery.ColorAdapter
import kotlinx.android.synthetic.main.bottom_sheet_color.*
import kotlinx.android.synthetic.main.include_error.*


interface GalleryView {
    fun displayPictures(previewPictures: List<PreviewPictureViewModel>)
    fun displayColorBottom(colorList: List<Pair<Int, String>>)
    fun displayError()
    fun stopLoadPictures()
}

class GalleryActivity : AppCompatActivity(), GalleryView {

    companion object {
        private const val DATA_CHILD = 0
        private const val ERROR_CHILD = 1
    }

    @Inject
    lateinit var interactor: GalleryInteractor

    private val adapter: GalleryAdapter = GalleryAdapter { position ->
        startActivity(DetailActivity.newIntent(this, position))
    }
    private val colorAdapter = ColorAdapter()
    private lateinit var layoutManager: GridLayoutManager

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            layoutManager.apply {
                if (job == null && childCount + findFirstVisibleItemPosition() >= itemCount
                        && findFirstVisibleItemPosition() >= 0) {
                    loadGallery(color)
                }
            }
        }
    }

    private var job: Job? = null
    private var color: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        DaggerGalleryComponent.builder()
                .mainComponent(MainDependencies.instance.mainComponent)
                .galleryModule(GalleryModule(this))
                .build()
                .inject(this)

        initGallery()
        initColor()

        loadGallery(color)
        launch(CommonPool) {
            interactor.findColor()
        }
    }

    override fun displayPictures(previewPictures: List<PreviewPictureViewModel>) {
        launch(UI) {
            adapter.replace(previewPictures)
            if (galleryViewFlipper.displayedChild != DATA_CHILD) {
                galleryViewFlipper.displayedChild = DATA_CHILD
            }
            job = null
        }
    }

    override fun displayColorBottom(colorList: List<Pair<Int, String>>) {
        launch(UI) {
            colorAdapter.colorList = colorList
            colorAdapter.listener = { color ->
                this@GalleryActivity.color = color
                loadGallery(color)
            }
        }
    }

    override fun displayError() {
        launch(UI) {
            galleryViewFlipper.displayedChild = ERROR_CHILD
            retryButton.setOnClickListener {
                loadGallery(color)
            }
            job = null
        }
    }

    override fun stopLoadPictures() {
        launch(UI) {
            galleryRecyclerView.removeOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    private fun loadGallery(color: String?) {
        job = launch(CommonPool) {
            interactor.findPictures(color)
        }
    }

    private fun initGallery() {
        layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.gallery_span_count))
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = adapter
        galleryRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun initColor() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBehavior)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        colorRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorRecyclerView.adapter = colorAdapter
    }
}
