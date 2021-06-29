package kg.appsstudio.daggerparttwo.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
import kg.appsstudio.daggerparttwo.data.room.dao.AppDataBase
import kg.appsstudio.daggerparttwo.utils.QUIZ_DATA_FILENAME

class DataInitializer(context: Context,workerParams: WorkerParameters):Worker(context,workerParams) {
    override fun doWork(): Result {
        Log.e("TAG", " >>> Starting initializing data in database")
        var result= Result.failure()
        try {
            applicationContext.assets.open(QUIZ_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Quiz>>() {}.type
                    val plantList: List<Quiz> = Gson().fromJson(jsonReader, plantType)

                    val database = AppDataBase.getInstance(applicationContext)
                    database!!.quizDao().saveAll(plantList)

                    result = Result.success()
                    Log.e("TAG", " >>> Data initialization success")
                }
            }
        } catch (ex: Exception) {
            Log.d("TAG", "Error in data initialization into database", )
            result = Result.failure()
        }
        return result
    }

}