package com.duhan.feature_name.presentation

import com.duhan.feature_name.data.nameservice.Person

sealed class UIState {
    object Loading : UIState()
    data class Idle(val list: List<Person>)
    data class Error(val title: String, val message: String)
}