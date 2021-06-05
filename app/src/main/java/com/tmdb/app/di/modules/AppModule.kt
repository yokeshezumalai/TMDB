package com.tmdb.app.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.tmdb.app.AppCache


@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {

    @Provides
    @Singleton
    fun cacheProvider(): AppCache {
        return AppCache
    }


}