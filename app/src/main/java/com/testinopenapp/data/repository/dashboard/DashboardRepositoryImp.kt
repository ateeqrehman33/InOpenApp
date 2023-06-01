package com.testinopenapp.data.repository.dashboard

import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.datasource.dashboard.DashboardRemoteDataSource
import com.testinopenapp.data.model.dashboard.DashboardResponse
import com.testinopenapp.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DashboardRepositoryImp @Inject constructor(
    private val dashboardRemoteDataSource: DashboardRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DashboardRepository {
    override suspend fun getDashboard(): Flow<NetworkResponse<DashboardResponse>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = try {
                dashboardRemoteDataSource.getDashboard()
            } catch (e: Exception) {
                NetworkResponse.Error(e)
            }
            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }.flowOn(ioDispatcher)
    }
}