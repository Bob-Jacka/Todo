package com.kirill.todo.tasks.tasksByTypes

import com.kirill.todo.tasks.data.AbstractTask
import com.kirill.todo.tasks.data.TasksEnum

class SportTask : AbstractTask {
    constructor()

    constructor(taskName: String, description: String?, type: TasksEnum) {
        this.taskName = taskName
        this.description = description!!
        this.type = type
    }
}
