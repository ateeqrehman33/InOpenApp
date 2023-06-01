package com.testinopenapp.data.repository.dashboard

import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.model.dashboard.DashboardResponse
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    suspend fun getDashboard(): Flow<NetworkResponse<DashboardResponse>>

}