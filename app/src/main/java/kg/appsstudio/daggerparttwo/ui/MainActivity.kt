package kg.appsstudio.daggerparttwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import kg.appsstudio.daggerparttwo.R
import kg.appsstudio.daggerparttwo.data.model.pojo.Answer
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
import kg.appsstudio.daggerparttwo.databinding.ActivityMainBinding
import kg.appsstudio.daggerparttwo.extension.createFactory
import kg.appsstudio.daggerparttwo.repository.Repository
import kg.appsstudio.daggerparttwo.ui.adapter.OnClickListener
import kg.appsstudio.daggerparttwo.ui.adapter.QuizAdapter
import kg.appsstudio.daggerparttwo.ui.dialog.CustomResultDialog
import kg.appsstudio.daggerparttwo.ui.dialog.OnDialogListener
import kg.appsstudio.daggerparttwo.ui.helper.SnapHelperByOne
import kg.appsstudio.daggerparttwo.ui.helper.SnapOnScrollListener
import kg.appsstudio.daggerparttwo.ui.helper.SnapOnScrollListener.Companion.NOTIFY_ON_SCROLL
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AndroidInjection.inject(this)
        init()
        getAllList()
        setObserver()
        setRecyclerView()
        setRecyclerOnScrollListener()
        onClick()
    }


    private fun init(){
        Log.d(TAG, " >>> Initializing viewModel")

        val factory = MainViewModel(repository).createFactory()
        vm = ViewModelProvider(this,factory).get(MainViewModel::class.java)
    }

    private fun getAllList() {
       vm.getAllQuestion()
    }

    private fun setObserver() {
       vm.observableState.observe(this, Observer {
           when{
               it.success -> {
                   Handler().postDelayed({
                       binding.state = it
                   },700)

                   quizList = it.list!!
                   adapter.quizList = it.list!!
                   adapter.notifyDataSetChanged()
               }
               else -> binding.state = it

           }
       })
    }

    private fun onClick(){
        binding.btnSubmit.setOnClickListener {
            var result = vm.chcekAnswers(answeredList,quizList.size)

            val fm = this.supportFragmentManager
            val customResultDialog = CustomResultDialog.newInstance(result,object : OnDialogListener{
                override fun onClick() {
                    answeredList = listOf()
                    adapter.answerList = answeredList
                    adapter.quizList = quizList
                    adapter.notifyDataSetChanged()
                    adapter.notifyItemChanged(0)
                    binding.rvAnswers.smoothScrollToPosition(0)
                }
            })
            customResultDialog.retainInstance = true
            customResultDialog.showNow(fm, CUSTOM_RESULT_DIALOG)
        }
    }

    private fun setRecyclerView(){
        binding.rvAnswers.layoutManager = LinearLayoutManager(
            this,RecyclerView.HORIZONTAL,false)

        adapter = QuizAdapter(quizList,answeredList, object : OnClickListener{
            override fun onItemClick(quiz: Quiz, position: Int, answerIndex: Int) {
                if (answeredList.isEmpty()){
                    answeredList = answeredList + Answer(quiz.question,
                    position,answerIndex,quiz.correctIndex)
                }else{
                    var isItemFound = false
                    answeredList.find {
                        if (it.selectedPosition == position){
                            it.answerIndex = answerIndex
                            isItemFound = true
                            true
                        }else{
                            false
                        }
                    }

                    if (!isItemFound){
                        answeredList = answeredList + Answer(
                            quiz.question,
                            position,
                            answerIndex,
                            quiz.correctIndex
                        )
                    }
                }

                adapter.answerList = answeredList
                adapter.notifyItemChanged(position)
            }

        })
        binding.rvAnswers.adapter = adapter
        binding.rvAnswers.itemAnimator = null
    }

    private fun setRecyclerOnScrollListener() {
        binding.rvAnswers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, i: Int, i2: Int) {
                val childCount: Int = recyclerView.childCount
                val width: Int = recyclerView.getChildAt(0).width
                val padding: Int = (recyclerView.width - width) / 2
                for (j in 0 until childCount) {
                    val v: View = recyclerView.getChildAt(j)

                    var rate = 0f
                    if (v.left <= padding) {
                        rate = if (v.left >= padding - v.width) {
                            (padding - v.left) * 1f / v.width
                        } else {
                            1f
                        }
                        v.scaleY = 1 - rate * 0.1f
                        v.scaleX = 1 - rate * 0.1f
                    } else {
                        if (v.left <= recyclerView.width - padding) {
                            rate = (recyclerView.width - padding - v.left) * 1f / v.width
                        }
                        v.scaleY = 0.9f + rate * 0.1f
                        v.scaleX = 0.9f + rate * 0.1f
                    }
                }
            }
        })

        binding.rvAnswers.addOnScrollListener(SnapOnScrollListener(snapHelper, NOTIFY_ON_SCROLL) { position ->
            binding.selectedQuestion = position
            binding.shouldShowSubmitBtn = (position + 1) == quizList.size
            adapter.notifyItemChanged(position)

            Log.d(TAG, "Scroll Position : $position List Size : ${quizList.size}")
        })
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val CUSTOM_RESULT_DIALOG = "CUSTOM_RESULT_DIALOG"
    }
}