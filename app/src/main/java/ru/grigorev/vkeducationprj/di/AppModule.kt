package ru.grigorev.vkeducationprj.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.grigorev.vkeducationprj.data.model.AppMapper
import ru.grigorev.vkeducationprj.data.repository.AppRepositoryImpl
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Singleton

// @Provides — для классов, которые Hilt может просто создать вызовом конструктора/фабрики.
@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideAppMapper(): AppMapper = AppMapper()
}

// @Binds — для интерфейсов: говорит, какую реализацию подставить вместо интерфейса.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(
        impl: AppRepositoryImpl // AppRepositoryImpl получает AppMapper через DI автоматически
    ): AppRepository
}
