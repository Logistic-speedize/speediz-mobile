package com.example.speediz.ui.feature.authorized.vendor.packageTracking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.core.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageTrackingViewModel @Inject constructor(
    val repository : PackageRepository
): ViewModel() {
    private val _packageList = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    private val packageList = _packageList.asStateFlow()

    private val _packageInMap = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageInMap = _packageInMap.asStateFlow()
    private val _packageFilterInMap = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageFilterInMap = _packageFilterInMap.asStateFlow()

    private val _packageFilter = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageFilter = _packageFilter.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
    init {

        fetchPackageList()
        packageFilter

    }

//    fun fetchPackageList() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = repository.packageList()
//                Log.d("PackageTrackingViewModel" , "Fetched packages: ${response.data.packages}")
//                _packageList.value = response.data.packages
//                _packageFilter.value = response.data.packages
//            } catch (e: Exception) {
//                e.printStackTrace()
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    fun fetchPackageList() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val allPackages = mutableListOf<PackageResponse.DataPackage.PackageItem>()
                var page = 1
                var hasMore = true

                while (hasMore) {
                    val response = repository.packageList(page)
                    val packages = response.data.packages
                    allPackages += packages
                    hasMore = page <= response.data.lastPage
                    page++
                }

                Log.d("PackageTrackingViewModel", "Fetched packages: $allPackages")
                _packageList.value = allPackages.sortedByDescending(PackageResponse.DataPackage.PackageItem::id)
                _packageFilter.value = allPackages.sortedByDescending(PackageResponse.DataPackage.PackageItem::id)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun fetchPackageListInMap(){
//        _isLoading.value = true
//        viewModelScope.launch {
//
//            val response = repository.packageList()
//            val packageList = response.data.packages.filter { it.status == "in_transit" }
//            _packageInMap.value = packageList
//            _packageFilterInMap.value = packageList
//        }
//        _isLoading.value = false
//    }
fun fetchPackageListInMap() {
    _isLoading.value = true
    viewModelScope.launch {
        try {
            val allPackages = mutableListOf<PackageResponse.DataPackage.PackageItem>()
            var page = 1
            val pageSize = 50
            var hasMore = true

            while (hasMore) {
                val response = repository.packageList(page) // make sure this calls API with ?page=page
                val packages = response.data.packages
                allPackages += packages
                hasMore = packages.size == pageSize
                page++
            }

            // Filter packages that are in transit
            val inTransitPackages = allPackages.filter { it.status == "in_transit" }

            _packageInMap.value = inTransitPackages
            _packageFilterInMap.value = inTransitPackages

            Log.d("PackageTrackingViewModel", "Fetched in-transit packages: $inTransitPackages")

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.value = false
        }
    }
}


    fun searchPackageInMapByNId(id: String) {
        val filteredList = if (id.isEmpty()) {
            packageInMap.value
        } else {
            packageInMap.value.filter { it.packageNumber.contains(id, ignoreCase = true) }
        }
        _packageFilterInMap.value = filteredList
            .sortedByDescending(
            { it.id }
        )
    }
    fun searchPackageById(searchId: String) {
        val filteredList = if (searchId.isEmpty()) {
            packageList.value
        } else {
             packageList.value.filter { it.id.toString().contains(searchId, ignoreCase = true) }
        }
        _packageFilter.value = filteredList.sortedByDescending { it.id  }
    }
}