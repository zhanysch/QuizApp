package kg.appsstudio.daggerparttwo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kg.appsstudio.daggerparttwo.ui.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivityProvider(): MainActivity
}