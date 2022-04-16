@file:JvmName("TaskListModuleKt")

package com.akjaw.timi.android.task.list.ui.composition

import com.akjaw.timi.android.task.list.view.TaskListScreenCreator
import com.akjaw.timi.android.task.list.view.TaskListScreenCreatorImpl
import org.koin.dsl.module

val taskUiModule = module {
    factory<TaskListScreenCreator> { TaskListScreenCreatorImpl() }
}
