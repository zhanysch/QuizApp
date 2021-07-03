package kg.appsstudio.daggerparttwo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kg.appsstudio.daggerparttwo.R
import kg.appsstudio.daggerparttwo.data.model.pojo.Answer
import kg.appsstudio.daggerparttwo.data.room.entity.Quiz
import kg.appsstudio.daggerparttwo.databinding.QuizDataBinding

class QuizAdapter(var quizList : List<Quiz>,
                  var answerList: List<Answer>,
                  private val onCliclListener: OnClickListener) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
       return QuizViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_single_item,parent,false),
           quizList,answerList,onCliclListener)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
       holder.bind(quizList[position],position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
       return quizList.size
    }

    inner class QuizViewHolder(
        private val quizDataBinding : QuizDataBinding,
        private val quizList : List<Quiz>,
        private val answerList: List<Answer>,
        private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(quizDataBinding.root) {


        fun bind(quiz: Quiz, position: Int) {
            quizDataBinding.tvFirstAnswer.text = quiz.answer[0]
            quizDataBinding.tvSecondAnswer.text = quiz.answer[1]
            quizDataBinding.tvThirdAnswer.text = quiz.answer[2]
            quizDataBinding.tvFourthAnswer.text = quiz.answer[3]

            quizDataBinding.position = position.inc().toString() + "of" + quizList

            answerList.forEach { answer ->
                if (quiz.question == answer.question && position == answer.selectedPosition){
                    updateAnswer(answer.answerIndex)
                }
            }

            onClick(quiz,position)
        }

        private fun onClick(quiz: Quiz, position: Int) {
            quizDataBinding.tvFirstAnswer.setOnClickListener {
                resetColor()
                hideTickIcon()
                changeView(quizDataBinding.tvFirstAnswer, quizDataBinding.ivFirstAnswerCheck)
                onClickListener.onItemClick(quiz, position, 0)
            }
            quizDataBinding.tvSecondAnswer.setOnClickListener {
                resetColor()
                hideTickIcon()
                changeView(quizDataBinding.tvSecondAnswer, quizDataBinding.ivSecondAnswerCheck)
                onClickListener.onItemClick(quiz, position, 1)
            }
            quizDataBinding.tvThirdAnswer.setOnClickListener {
                resetColor()
                hideTickIcon()
                changeView(quizDataBinding.tvThirdAnswer, quizDataBinding.ivThirdAnswerCheck)
                onClickListener.onItemClick(quiz, position, 2)
            }
            quizDataBinding.tvFourthAnswer.setOnClickListener {
                resetColor()
                hideTickIcon()
                changeView(quizDataBinding.tvFourthAnswer, quizDataBinding.ivFourthAnswerCheck)
                onClickListener.onItemClick(quiz, position, 3)
            }
        }

        private fun hideTickIcon() {
            quizDataBinding.ivFirstAnswerCheck.visibility = View.GONE
            quizDataBinding.ivSecondAnswerCheck.visibility = View.GONE
            quizDataBinding.ivThirdAnswerCheck.visibility = View.GONE
            quizDataBinding.ivFourthAnswerCheck.visibility = View.GONE
        }

        private fun updateAnswer(answerIndex : Int){
            resetColor()
            when(answerIndex){
                 0 -> changeView(quizDataBinding.tvFirstAnswer,quizDataBinding.ivFirstAnswerCheck)
                 1 -> changeView(quizDataBinding.tvSecondAnswer, quizDataBinding.ivSecondAnswerCheck)
                2 -> changeView(quizDataBinding.tvThirdAnswer, quizDataBinding.ivThirdAnswerCheck)
                3 -> changeView(quizDataBinding.tvFourthAnswer, quizDataBinding.ivFourthAnswerCheck)
            }
        }

        private fun resetColor() {
            quizDataBinding.tvFirstAnswer.setTextColor(ContextCompat.getColor(quizDataBinding.root.context, android.R.color.background_dark))
            quizDataBinding.tvSecondAnswer.setTextColor(ContextCompat.getColor(quizDataBinding.root.context, android.R.color.background_dark))
            quizDataBinding.tvThirdAnswer.setTextColor(ContextCompat.getColor(quizDataBinding.root.context, android.R.color.background_dark))
            quizDataBinding.tvFourthAnswer.setTextColor(ContextCompat.getColor(quizDataBinding.root.context, android.R.color.background_dark))
        }

        private fun changeView(textView : AppCompatTextView, imageView: AppCompatImageView){
            textView.setTextColor(ContextCompat.getColor(quizDataBinding.root.context, R.color.black))
        }

        }
}