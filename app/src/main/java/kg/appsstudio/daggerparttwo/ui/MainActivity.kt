package kg.appsstudio.daggerparttwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.appsstudio.daggerparttwo.R
import kg.appsstudio.daggerparttwo.data.model.pojo.Answer
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
import kg.appsstudio.daggerparttwo.databinding.ActivityMainBinding
import kg.appsstudio.daggerparttwo.repository.Repository
import kg.appsstudio.daggerparttwo.ui.adapter.QuizAdapter
import kg.appsstudio.daggerparttwo.ui.helper.SnapHelperByOne
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var vm : MainViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : QuizAdapter

    private var quizList : List<Quiz> = listOf()
    private var answeredList : List<Answer> = mutableListOf()
    private val snapHelper = SnapHelperByOne()

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}