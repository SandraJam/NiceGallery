package com.sandra.dupre.repository

import com.sandra.dupre.business.NetworkException
import com.sandra.dupre.business.NoOtherPageException

interface DataSource<T> {
    @Throws(NetworkException::class, NoOtherPageException::class)
    fun get(page: Int): T

    fun getAll(): T
}