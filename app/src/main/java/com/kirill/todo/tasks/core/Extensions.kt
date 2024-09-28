package com.kirill.todo.tasks.core

import com.kirill.todo.tasks.data.AbstractTask
import com.kirill.todo.tasks.data.TasksEnum
import com.kirill.todo.tasks.tasksByTypes.EducationTask
import com.kirill.todo.tasks.tasksByTypes.MedicalTask
import com.kirill.todo.tasks.tasksByTypes.OtherTask
import com.kirill.todo.tasks.tasksByTypes.ReadTask
import com.kirill.todo.tasks.tasksByTypes.SportTask
import com.kirill.todo.tasks.tasksByTypes.WorkTask

fun getTask(type: TasksEnum): AbstractTask {
    return when (type) {
        TasksEnum.OTHER -> OtherTask()
        TasksEnum.READ -> ReadTask()
        TasksEnum.SPORT -> SportTask()
        TasksEnum.WORK -> WorkTask()
        TasksEnum.MEDICAL -> MedicalTask()
        TasksEnum.EDUCATION -> EducationTask()
    }
}