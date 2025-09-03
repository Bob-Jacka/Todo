package com.kirill.todo.tasks.data

private typealias str = String

/**
 * Class representing list of tasks.
 */
class TaskList {

    private var tasks_list: MutableList<AbstractTask> = mutableListOf()
    private val list_name: str
    val description: str

    constructor(tasks: List<AbstractTask>, name: str = "", list_description: str = "") {
        this.tasks_list = tasks.toMutableList()
        this.list_name = name
        this.description = list_description
    }

    fun sort_list() {
        tasks_list.sortBy { tasks_list.elementAt(0).taskName() }
    }

    fun delete_task(task: AbstractTask?) {
        if (task != null) {
            tasks_list.remove(task)
        } else {
            //
        }
    }

    fun add_task(task: AbstractTask?) {
        if (task != null) {
            tasks_list.add(task)
        } else {
            println("Error occurred in add task algorithm")
        }
    }
}