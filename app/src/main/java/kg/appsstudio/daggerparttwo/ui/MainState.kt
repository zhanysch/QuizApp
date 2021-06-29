package kg.appsstudio.daggerparttwo.ui

import kg.appsstudio.daggerparttwo.data.room.entity.Quiz


data class MainState(
    var loading: Boolean = false,
    var success: Boolean = false,
    var failure: Boolean = false,
    var message: String? = null,
    var list: List<Quiz>? = listOf()
)