package kg.appsstudio.daggerparttwo.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.appsstudio.daggerparttwo.data.model.pojo.Answer
import kg.appsstudio.daggerparttwo.data.model.pojo.Result
import kg.appsstudio.daggerparttwo.repository.Repository

class MainViewModel(private val repository: Repository): ViewModel() {

    var observableState: MutableLiveData<MainState> = MutableLiveData()

    private val compositDisposable = CompositeDisposable()

    private var state = MainState()
    set(value){
        field = value
        publishState(value)
    }

    fun getAllQuestion(){
        Log.d(TAG, " >>> Received call to get question list")
        state = state.copy(loading = true,success = false,failure = false,list = null)
        compositDisposable.add(
            repository.getAllItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state = state.copy(loading = false,success = true,failure = false,list = it)
                },{
                    state = state.copy(
                        loading = false,
                        failure = true,
                        success = false,
                        message = it.localizedMessage
                    )
                })
        )
    }

    fun chcekAnswers(answerList : List<Answer>, totalQuestion :Int): Result{
        Log.d(TAG, " >>> Received call to verify all answers : $answerList")
        Log.d(TAG, " >>> Total Question : $totalQuestion || Total Attempted Question : ${answerList.size}")

        var totalCorrectAnswer = 0
        var totalWrongAnswer = 0
        answerList.forEach { answer ->
            if (answer.answerIndex == answer.correctIndex) totalCorrectAnswer ++
            else totalWrongAnswer ++
        }

        Log.d(TAG, " >>> Total correct answer : $totalCorrectAnswer || Total wrong answer : $totalWrongAnswer")

        return Result(
            totalQuestion = totalQuestion,
            attemptedQuestion = answerList.size,
            correctAnswer = totalCorrectAnswer,
            wrongAnswer = totalWrongAnswer,
            skippedQuestion = totalQuestion - answerList.size
        )
    }

    private fun publishState(state: MainState){
        Log.d(TAG," >>> Publish State : $state")
        compositDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable")
        compositDisposable.clear()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}