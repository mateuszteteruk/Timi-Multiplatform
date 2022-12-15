package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar

data class DayViewState(
    val day: Int,
    val month: Int = 0,
    val year: Int = 0,
    val isSelected: Boolean = false,
)
