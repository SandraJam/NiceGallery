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

interface GalleryView {
    fun displayPictures(pictures: List<PictureViewModel>)
    fun displayError()
}

class GalleryActivity : AppCompatActivity(), GalleryView {

    companion object {
        private const val DATA_CHILD = 0
        private const val ERROR_CHILD = 1
    }

    @Inject
    lateinit var interactor: GalleryInteractor

    private val adapter: GalleryAdapter = GalleryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        DaggerGalleryComponent.builder()
                .mainComponent(MainDependencies.instance.mainComponent)
                .galleryModule(GalleryModule(this))
                .build()
                .inject(this)

        galleryRecyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.gallery_span_count))
        galleryRecyclerView.adapter = adapter

        launch(CommonPool) {
            interactor.findPictures()
        }
    }

    override fun displayPictures(pictures: List<PictureViewModel>) {
        launch(UI) {
            adapter.replace(pictures)
            if (galleryViewFlipper.displayedChild != DATA_CHILD) {
                galleryViewFlipper.displayedChild = DATA_CHILD
            }
        }
    }

    override fun displayError() {
        launch(UI) {
            galleryViewFlipper.displayedChild = ERROR_CHILD
            retryButton.setOnClickListener {
                interactor.findPictures()
            }
        }
    }
}
