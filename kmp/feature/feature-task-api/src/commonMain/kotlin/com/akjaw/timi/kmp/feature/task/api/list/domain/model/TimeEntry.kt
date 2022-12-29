package com.akjaw.timi.kmp.feature.task.api.list.domain.model

import com.akjaw.core.common.domain.model.TimestampMilliseconds

data class TimeEntry(
    val id: Long,
    val taskId: Long,
    // TODO add CalendarDay or something like that with time?
    val timeAmount: TimestampMilliseconds,
    val date: TimestampMilliseconds
)
