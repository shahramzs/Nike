package com.example.nike.data

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow:Boolean,
    @StringRes val messageResTd:Int = 0,
    val mustShowCallToActionButton:Boolean = false
)
