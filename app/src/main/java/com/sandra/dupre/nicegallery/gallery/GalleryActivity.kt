package com.sandra.dupre.nicegallery.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sandra.dupre.nicegallery.R

interface GalleryView

class GalleryActivity : AppCompatActivity(), GalleryView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
    }
}
