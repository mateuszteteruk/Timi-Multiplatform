package com.example.timicompose.common.domain

import com.example.timicompose.stopwatch.domain.model.TimestampMilliseconds
import com.example.timicompose.stopwatch.domain.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime
import javax.inject.Inject

class KlockTimestampProvider @Inject constructor() : TimestampProvider {

    override fun getMilliseconds(): TimestampMilliseconds =
        DateTime.nowUnixLong().toTimestampMilliseconds()
}
