package com.nam.samples.common

import android.content.Context
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes id: Int): String
}

class ResourceManagerImpl(
    private val context: Context
) : ResourceManager {

    override fun getString(id: Int): String =
        context.getString(id)
}
