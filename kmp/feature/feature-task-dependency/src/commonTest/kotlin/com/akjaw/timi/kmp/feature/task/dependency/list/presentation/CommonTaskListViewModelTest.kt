package com.akjaw.timi.kmp.feature.task.dependency.list.presentation

import com.akjaw.timi.kmp.core.test.coroutines.TestDispatcherProvider
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.composition.databaseModule
import com.akjaw.timi.kmp.feature.database.test.createTestSqlDriver
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import com.squareup.sqldelight.db.SqlDriver
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

// TODO refactor?
@OptIn(ExperimentalCoroutinesApi::class)
internal class CommonTaskListViewModelTest : KoinComponent {

    companion object {
        val TASK1 = Task(
            id = 0,
            name = "name",
            backgroundColor = TaskColor(22f, 22f, 22f),
            isSelected = false
        )
        val TASK2 = Task(
            id = 1,
            name = "name2",
            backgroundColor = TaskColor(33f, 33f, 33f),
            isSelected = false
        )
    }

    private val taskEntityQueries: TaskEntityQueries by inject()
    private val taskSelectionTrackerFactory = TaskSelectionTrackerFactory()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private lateinit var systemUnderTest: CommonTaskListViewModel

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                databaseModule,
                taskListModule,
                module {
                    single<SqlDriver> { createTestSqlDriver() }
                }
            )
        }
        systemUnderTest = CommonTaskListViewModel(
            getTasks = get(),
            deleteTasks = get(),
            addTask = get(),
            dispatcherProvider = TestDispatcherProvider(testCoroutineDispatcher),
            taskSelectionTrackerFactory = taskSelectionTrackerFactory
        )
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Adding a task changes the list`(): Unit = runBlocking {
        givenTasks(TASK1)

        systemUnderTest.addTask(TASK2)

        val result = systemUnderTest.tasks.first()
        result shouldBe listOf(TASK1, TASK2)
    }

    @Test
    fun `Deleting tasks changes the list`(): Unit = runBlocking {
        givenTasks(TASK1, TASK2)

        systemUnderTest.deleteTasks(listOf(TASK1, TASK2))

        val result = systemUnderTest.tasks.first()
        result shouldBe emptyList()
    }

    private fun givenTasks(vararg task: Task) {
        task.forEach {
            taskEntityQueries.insertTask(
                id = it.id,
                position = 0,
                name = it.name,
                color = it.backgroundColor
            )
        }
    }
}
