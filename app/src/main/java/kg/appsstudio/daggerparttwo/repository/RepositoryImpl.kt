package kg.appsstudio.daggerparttwo.repository

import io.reactivex.Flowable
import kg.appsstudio.daggerparttwo.data.room.dao.AppDataBase
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz


interface Repository {
    fun getAllItem(): Flowable<List<Quiz>>
}
class RepositoryImpl(private val db : AppDataBase): Repository {
    override fun getAllItem(): Flowable<List<Quiz>> {
        return db.quizDao().getAllQuestion()
    }
}
