package com.sandra.dupre.nicegallery.gallery.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
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


interface GalleryView {
    fun displayPictures(previewPictures: List<PreviewPictureViewModel>)
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

    private val adapter: GalleryAdapter = GalleryAdapter { id ->
        startActivity(DetailActivity.newIntent(this, id))
    }
    private lateinit var layoutManager: GridLayoutManager

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            layoutManager.apply {
                if (!isLoading && childCount + findFirstVisibleItemPosition() >= itemCount
                        && findFirstVisibleItemPosition() >= 0) {
                    load()
                }
            }
        }
    }

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        DaggerGalleryComponent.builder()
                .mainComponent(MainDependencies.instance.mainComponent)
                .galleryModule(GalleryModule(this))
                .build()
                .inject(this)

        initGallery()
        load()
    }

    override fun displayPictures(previewPictures: List<PreviewPictureViewModel>) {
        launch(UI) {
            adapter.replace(previewPictures)
            if (galleryViewFlipper.displayedChild != DATA_CHILD) {
                galleryViewFlipper.displayedChild = DATA_CHILD
            }
            isLoading = false
        }
    }

    override fun displayError() {
        launch(UI) {
            galleryViewFlipper.displayedChild = ERROR_CHILD
            retryButton.setOnClickListener {
                load()
            }
        }
    }

    override fun stopLoadPictures() {
        launch(UI) {
            galleryRecyclerView.removeOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    private fun load() {
        isLoading = true
        launch(CommonPool) {
            interactor.findPictures()
        }
    }

    private fun initGallery() {
        layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.gallery_span_count))
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = adapter
        galleryRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }
}
