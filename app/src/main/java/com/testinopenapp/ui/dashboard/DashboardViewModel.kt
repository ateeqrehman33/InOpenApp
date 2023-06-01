package com.testinopenapp.ui.dashboard

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testinopenapp.common.Constants.GONE_AND_NO_MORE_DATA
import com.testinopenapp.common.Constants.VISIBLE_AND_DELAYED
import com.testinopenapp.common.Constants.VISIBLE_AND_ERROR
import com.testinopenapp.common.Constants.VISIBLE_AND_LOADING
import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.model.dashboard.DashboardResponse
import com.testinopenapp.data.model.dashboard.RecentLink
import com.testinopenapp.data.model.dashboard.TopLink
import com.testinopenapp.data.repository.dashboard.DashboardRepository
import com.testinopenapp.di.coroutine.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(private val dashboardRepository: DashboardRepository,
                       @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO, ): ViewModel()  {


    private val _dashboarddata = MutableLiveData<NetworkResponse<DashboardResponse>>()
    val dashboarddata: LiveData<NetworkResponse<DashboardResponse>> get() = _dashboarddata

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> get() = _progressBarVisibility

    var isLoading = false

    var toplinksResultList = mutableListOf<TopLink>()


    init {
        getDashboard()
    }


    private fun getDashboard() {
        viewModelScope.launch(ioDispatcher) {
            dashboardRepository.getDashboard().collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        isLoading = true
                        _progressBarVisibility.postValue(VISIBLE_AND_LOADING) // visible and loading
                        _dashboarddata.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        isLoading = false
                        _dashboarddata.postValue(NetworkResponse.Success(it.result))
                        _progressBarVisibility.postValue(GONE_AND_NO_MORE_DATA) // gone and no more data

                    }
                    is NetworkResponse.Error -> {
                        isLoading = false
                        _dashboarddata.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }
    }



}