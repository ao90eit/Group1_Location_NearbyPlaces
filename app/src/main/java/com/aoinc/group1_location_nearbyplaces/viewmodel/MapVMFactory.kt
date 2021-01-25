@file:Suppress("UNCHECKED_CAST")

package com.aoinc.group1_location_nearbyplaces.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object MapVMFactory : ViewModelProvider.Factory {
    var viewModel : MapViewModel? = MapViewModel()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}