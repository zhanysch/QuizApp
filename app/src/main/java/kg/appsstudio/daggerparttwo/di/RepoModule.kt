package kg.appsstudio.daggerparttwo.di

import dagger.Module
import dagger.Provides
import kg.appsstudio.daggerparttwo.repository.Repository
import kg.appsstudio.daggerparttwo.repository.RepositoryImpl
import kg.appsstudio.daggerparttwo.data.room.dao.AppDataBase
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideQuiezRepo(appDataBase: AppDataBase): Repository {
        return RepositoryImpl(appDataBase)
    }

}
