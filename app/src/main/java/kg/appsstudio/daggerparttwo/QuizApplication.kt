package kg.appsstudio.daggerparttwo

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kg.appsstudio.daggerparttwo.di.DaggerAppComponent


class QuizApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       if (BuildConfig.DEBUG){
           Stetho.initializeWithDefaults(this)
       }
        return DaggerAppComponent.builder().application(this).build()
    }
}