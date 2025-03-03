package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.date.format
import com.akjaw.timi.kmp.core.shared.time.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.database.entry.TimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.TaskDetailViewModel
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.model.TimeEntryUi
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TODO should have list in the name?
internal class CommonTaskDetailViewModel(
    private val taskId: Long,
    private val timeEntryRepository: TimeEntryRepository,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) : TaskDetailViewModel {
    // TODO move logic to the domain?

    override fun getTimeEntries(day: CalendarDay): Flow<List<TimeEntryUi>> {
        // TODO could be moved to a property
        return timeEntryRepository.getByTaskIds(listOf(taskId)).map { entries: List<TimeEntry> ->
            entries
                .filter { it.date == day }
                .map { entry ->
                    TimeEntryUi(
                        id = entry.id,
                        date = entry.date,
                        formattedTime = timestampMillisecondsFormatter.formatWithoutMilliseconds(entry.timeAmount),
                        formattedDate = entry.date.format()
                    )
                }
        }
    }

    override fun addTimeEntry(timeAmount: TimestampMilliseconds, day: CalendarDay) {
        timeEntryRepository.insert(taskId, timeAmount, day)
    }

    override fun removeTimeEntry(id: Long) {
        timeEntryRepository.deleteById(id)
    }
}
