package kg.appsstudio.daggerparttwo.ui.adapter

import kg.appsstudio.daggerparttwo.data.room.entity.Quiz

interface OnClickListener {

    fun onItemClick(quiz: Quiz, position: Int, answerIndex: Int)
}