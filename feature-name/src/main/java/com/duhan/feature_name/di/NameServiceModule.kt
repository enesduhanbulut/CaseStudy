package com.duhan.feature_name.di

import com.duhan.feature_name.data.nameservice.DataSource
import com.duhan.feature_name.data.paging.NameServicePagingSource
import com.duhan.feature_name.data.repository.NameRepository
import com.duhan.feature_name.domain.GetNames
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class NameServiceModule {

    @Provides
    @ViewModelScoped
    fun provideNameService(): DataSource {
        return DataSource()
    }

    @Provides
    fun provideNameServicePagingSource(nameService: DataSource): NameServicePagingSource {
        return NameServicePagingSource(nameService)
    }

    @Provides
    fun provideNameRepository(nameServicePagingSource: NameServicePagingSource): NameRepository {
        return NameRepository(nameServicePagingSource)
    }

    @Provides
    fun provideGetNames(nameRepository: NameRepository): GetNames {
        return GetNames(nameRepository)
    }
}
