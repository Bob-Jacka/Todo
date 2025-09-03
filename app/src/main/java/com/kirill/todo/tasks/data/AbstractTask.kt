package com.kirill.todo.tasks.data

import java.io.Serializable

abstract class AbstractTask : Serializable {

    private lateinit var createdAt: String
    private var whenActivated: Int = 0

    private var checked: Boolean = false
    private var whichDaysOfWeek: List<String> = mutableListOf()

    private var steps: List<String> = mutableListOf()

    protected lateinit var taskName: String
    protected lateinit var description: String
    protected lateinit var type: TasksEnum

    fun taskName(): String {
        return taskName
    }

    fun steps(): List<String> {
        return steps
    }

    fun steps(steps: List<String>) {
        this.steps = steps
    }

    fun taskName(taskName: String): Unit {
        this.taskName = taskName
    }

    fun description(): String {
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
