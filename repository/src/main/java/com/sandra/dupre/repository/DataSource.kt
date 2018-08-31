package com.sandra.dupre.repository

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException

interface DataSource<T> {
    fun getAll(): T

    @Throws(NetworkException::class, NoOtherPageException::class)
    fun loadNextPage()
}