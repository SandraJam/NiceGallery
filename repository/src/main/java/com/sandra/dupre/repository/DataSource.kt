package com.sandra.dupre.repository

import com.sandra.dupre.business.NetworkException

interface DataSource<T> {
    @Throws(NetworkException::class)
    fun get(): T
}