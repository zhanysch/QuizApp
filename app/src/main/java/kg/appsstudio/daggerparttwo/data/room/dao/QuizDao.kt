package kg.appsstudio.daggerparttwo.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(queiz : List<Quiz>)

    @Query("SELECT * FROM QUIZ")
    fun getAllQuestion(): Flowable<List<Quiz>>
}