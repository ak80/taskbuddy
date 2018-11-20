package org.ak80.taskbuddy.ui.tasks

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.aTask
import org.ak80.taskbuddy.model.Task
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TasksPresenterTest {

    private lateinit var presenter: TasksPresenter

    @RelaxedMockK
    lateinit var taskRepository: TaskGateway

    @RelaxedMockK
    lateinit var view: TasksContract.View

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = TasksPresenter(taskRepository)
    }

    @Test
    fun loadAllTasksFromRepositoryAndLoadIntoView_whenTakeView() {
        // Given
        val taskList = listOf(aTask(), aTask(), aTask())
        taskRepository.answerLoadCallbackWith(taskList)

        // When
        presenter.takeView(view)

        // Then
        verify { view.showTasks(taskList) }
    }

    @Test
    fun setTaskPassed_whenCompletedTask() {
        // Given
        val task = aTask(passed = false)

        // When
        presenter.completeTask(task)

        // Then

        assertThat(task.passed).isTrue()
    }

    @Test
    fun informView_whenCompletedTask() {
        // Given
        val task = aTask(passed = false)
        presenter.takeView(view)

        // When
        presenter.completeTask(task)

        // Then

        verify { view.showTaskComplete() }
    }

    @Test
    fun setTaskNotPassed_whenActivateTask() {
        // Given
        val task = aTask(passed = true)
        presenter.takeView(view)

        // When
        presenter.activateTask(task)

        // Then

        assertThat(task.passed).isFalse()
    }

    @Test
    fun informView_whenActivateTask() {
        // Given
        val task = aTask(passed = true)
        presenter.takeView(view)

        // When
        presenter.activateTask(task)

        // Then

        verify { view.showTaskActive() }
    }

    @Test
    fun clearAllTaskInRepository_whenClearTasks() {
        // Given
        val taskList = listOf(aTask(), aTask(), aTask())
        taskRepository.answerLoadCallbackWith(taskList)
        presenter.takeView(view)

        // When
        presenter.clearTask()

        // Then
        verify { taskRepository.clearAll() }
    }

    @Test
    fun loadAllTaskFromRepositoryAndLoadIntoView_whenClearTasks() {
        // Given
        val taskList = listOf(aTask(), aTask(), aTask())
        taskRepository.answerLoadCallbackWith(taskList)
        presenter.takeView(view)

        // When
        presenter.clearTask()

        // Then
        verify(exactly = 2) { view.showTasks(taskList) }
    }

    @Test
    fun showAboutInView_whenAbout() {
        // Given
        val taskList = listOf(aTask(), aTask(), aTask())
        taskRepository.answerLoadCallbackWith(taskList)
        presenter.takeView(view)

        // When
        presenter.about()

        // Then
        verify { view.showAbout(R.string.about) }
    }

    @Test
    fun showMessageInView_whenAddNewTask() {
        // Given
        val taskList = listOf(aTask(), aTask(), aTask())
        taskRepository.answerLoadCallbackWith(taskList)
        presenter.takeView(view)

        // When
        presenter.addNewTask()

        // Then
        verify { view.showMessage(R.string.add_new_task) }
    }

    private fun TaskGateway.answerLoadCallbackWith(taskList: List<Task>) {
        val loadCallback = slot<(List<Task>) -> Unit>()
        every {
            loadTasks(capture(loadCallback))
        } answers {
            loadCallback.captured.invoke(taskList)
        }
    }

}