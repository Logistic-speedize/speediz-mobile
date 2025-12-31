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
    private val _packageUiState = MutableStateFlow<PackageUiState>(PackageUiState.Loading)
    val packageUiState = _packageUiState.asStateFlow()
    private val _packageList = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    private val packageList = _packageList.asStateFlow()

    private val _packageInMap = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageInMap = _packageInMap.asStateFlow()
    private val _packageFilterInMap = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageFilterInMap = _packageFilterInMap.asStateFlow()

    private val _packageFilter = MutableStateFlow<List<PackageResponse.DataPackage.PackageItem>>(emptyList())
    val packageFilter = _packageFilter.asStateFlow()
    init {

        fetchPackageList()
        packageFilter

    }
    fun fetchPackageList(searchQuery: String? = null) {
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
                val sortedList = allPackages.sortedByDescending(PackageResponse.DataPackage.PackageItem::id)
                Log.d( "PackageTrackingVM" , "Fetched Packages: $sortedList" )
                _packageList.value = sortedList
                _packageFilter.value = sortedList
                val filteredList = sortedList.filter { item ->
                    val matchQuery = searchQuery?.let {
                        item.id.toString().contains(it, ignoreCase = true) ||
                        item.packageNumber.contains(it, ignoreCase = true)
                    } ?: true
                    matchQuery
                }
                _packageFilter.value = filteredList
                if (filteredList.isEmpty()){
                    _packageUiState.value = PackageUiState.Success(emptyList())
                } else {
                    _packageUiState.value = PackageUiState.Success(filteredList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
fun fetchPackageListInMap(searchQuery: String ?= null) {
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
            if (searchQuery != null && searchQuery.isNotEmpty()) {
                val filteredList = inTransitPackages.filter {
                    it.id.toString().contains(searchQuery, ignoreCase = true)
                }
                _packageInMap.value = filteredList
                _packageFilterInMap.value = filteredList
                if (filteredList.isEmpty()) {
                    _packageUiState.value = PackageUiState.Success(emptyList())
                } else {
                    _packageUiState.value = PackageUiState.Success(filteredList)
                }
                return@launch
            }

            _packageInMap.value = inTransitPackages
            _packageFilterInMap.value = inTransitPackages

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


//    fun searchPackageInMapByNId(id: String) {
//        val filteredList = if (id.isEmpty()) {
//            packageInMap.value
//        } else {
//            packageInMap.value.filter { it.packageNumber.contains(id, ignoreCase = true) }
//        }
//        _packageFilterInMap.value = filteredList
//            .sortedByDescending(
//            { it.id }
//        )
//    }
//    fun searchPackageById(searchId: String) {
//        val filteredList = if (searchId.isEmpty()) {
//            packageList.value
//        } else {
//             packageList.value.filter { it.id.toString().contains(searchId, ignoreCase = true) }
//        }
//        _packageFilter.value = filteredList.sortedByDescending { it.id  }
//    }
}