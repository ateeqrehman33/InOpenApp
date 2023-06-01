package com.testinopenapp.data.datasource.dashboard

import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.model.dashboard.DashboardResponse
import okhttp3.ResponseBody

interface DashboardRemoteDataSource {

    suspend fun getDashboard(): NetworkResponse<DashboardResponse>
}