package com.testinopenapp.data.datasource.dashboard

import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.api.OpenInAppApi
import com.testinopenapp.data.model.dashboard.DashboardResponse
import com.testinopenapp.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import javax.inject.Inject


class DashboardRemoteDataSourceImp @Inject constructor(
    private val api: OpenInAppApi,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    DashboardRemoteDataSource {
    override suspend fun getDashboard(): NetworkResponse<DashboardResponse>
     {
        return try {
            val response = api.getDashboard()
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}