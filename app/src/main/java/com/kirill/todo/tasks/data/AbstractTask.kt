package com.kirill.todo.tasks.data

import java.io.Serializable

abstract class AbstractTask : Serializable {

    protected lateinit var taskName: String
    protected lateinit var description: String
    protected lateinit var createdAt: String

    protected lateinit var type: TasksEnum
    protected var whenActivated: Int = 0
    protected var checked: Boolean = false

    protected var whichDaysOfWeek: List<String> = mutableListOf()
    protected var steps: List<String> = mutableListOf()

    fun taskName(): String {
        return taskName
    }

    fun steps(): List<String>? {
        return steps.takeIf { steps.isNotEmpty() }
    }

    fun steps(steps: List<String>): Unit {
        this.steps = steps
    }

    fun taskName(taskName: String): Unit {
        this.taskName = taskName
    }

    fun description(): String? {
        return description
    }

    fun description(description: String) {
        this.description = description
    }

    fun type(): TasksEnum {
        return type
    }

    fun type(type: TasksEnum) {
        this.type = type
    }

    fun whenActivated(): Int {
        return whenActivated
    }

    fun whenActivated(whenActivated: Int) {
        this.whenActivated = whenActivated
    }

    fun createdAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun createdAt(): String {
        return createdAt
    }

    fun whichDaysOfWeek(): List<String> {
        return whichDaysOfWeek
    }

    fun whichDaysOfWeek(newValue: List<String>): Unit {
        this.whichDaysOfWeek = newValue
    }

    fun checked(): Boolean {
        return checked
    }

    fun checked(newValue: Boolean): Unit {
        this.checked = newValue
    }
}
