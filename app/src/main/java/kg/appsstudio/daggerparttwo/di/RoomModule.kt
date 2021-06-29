package kg.appsstudio.daggerparttwo.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kg.appsstudio.daggerparttwo.data.room.dao.AppDataBase
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(applicationContext: Application) : AppDataBase{
        return AppDataBase.getInstance(applicationContext)!!
    }
}