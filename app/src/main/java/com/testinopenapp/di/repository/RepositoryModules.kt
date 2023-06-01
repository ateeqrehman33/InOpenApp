package com.testinopenapp.di.repository

import com.testinopenapp.data.repository.dashboard.DashboardRepository
import com.testinopenapp.data.repository.dashboard.DashboardRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDashboardRepository(characterRepository: DashboardRepositoryImp): DashboardRepository
}
