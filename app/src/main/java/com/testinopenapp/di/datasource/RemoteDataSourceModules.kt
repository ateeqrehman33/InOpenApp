package com.testinopenapp.di.datasource

import com.testinopenapp.data.datasource.dashboard.DashboardRemoteDataSource
import com.testinopenapp.data.datasource.dashboard.DashboardRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardDatasourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDashboardDataSource(dashboardRemoteDataSource: DashboardRemoteDataSourceImp): DashboardRemoteDataSource
}