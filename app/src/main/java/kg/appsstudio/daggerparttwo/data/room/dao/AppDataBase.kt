package kg.appsstudio.daggerparttwo.data.room.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
import kg.appsstudio.daggerparttwo.data.room.entity.QuizTypeConverter
import kg.appsstudio.daggerparttwo.worker.DataInitializer

@Database(entities = [Quiz::class],version = 1,exportSchema = false)
@TypeConverters(QuizTypeConverter::class)
abstract class AppDataBase : RoomDatabase(){

    abstract fun quizDao():QuizDao

    companion object{
        private const val TAG = "AppDatabase"
        private const val DATABASE_NAME = "quiz_application.db"

        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getInstance(context: Context) : AppDataBase?{
            if (INSTANCE == null){
                  synchronized(this){
                      INSTANCE = Room.databaseBuilder(
                          context.applicationContext,
                          AppDataBase::class.java,
                          DATABASE_NAME
                      ).addCallback(object : RoomDatabase.Callback(){
                          override fun onCreate(db: SupportSQLiteDatabase) {
                              super.onCreate(db)
                              val request = OneTimeWorkRequestBuilder<DataInitializer>().build()
                              WorkManager.getInstance(context).enqueue(request)
                          }
                      }).build()
                  }
            }

            return INSTANCE
        }
    }
}