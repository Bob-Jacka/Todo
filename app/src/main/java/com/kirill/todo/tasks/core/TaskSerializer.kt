package com.kirill.todo.tasks.core

import com.kirill.todo.tasks.data.AbstractTask
import com.kirill.todo.tasks.data.TaskList
import com.kirill.todo.tasks.data.TasksEnum
import com.kirill.todo.tasks.tasksByTypes.EducationTask
import com.kirill.todo.tasks.tasksByTypes.MedicalTask
import com.kirill.todo.tasks.tasksByTypes.OtherTask
import com.kirill.todo.tasks.tasksByTypes.ReadTask
import com.kirill.todo.tasks.tasksByTypes.SportTask
import com.kirill.todo.tasks.tasksByTypes.WorkTask

private typealias str = String

/**
 * Object for serialize/deserialize actions
 */
object TaskSerializer {

    private const val DELIMITER: str = "|"
    private val FILTER_FUNCTION = { x: Char -> (x != '[' && x != ']') }

    fun task_serialize(task: AbstractTask): str {
        return "${task.taskName()}$DELIMITER${task.description()}$DELIMITER${task.type()}$DELIMITER${
            task.whichDaysOfWeek().toString().replace(" ", "")
        }$DELIMITER${task.steps()}$DELIMITER${task.createdAt()}$DELIMITER${task.checked()}$DELIMITER${task.whenActivated()}"
    }

    fun task_deserialize(string: str): AbstractTask? {
        val maybeStr = string.takeIf { string.length > 2 }
        if (maybeStr != null) {
            val str = maybeStr.split(DELIMITER)
            val task: AbstractTask =
                when (TasksEnum.valueOf(str[2].trim())) {
                    TasksEnum.READ -> ReadTask(str[0], str[1], TasksEnum.READ)
                    TasksEnum.OTHER -> OtherTask(str[0], str[1], TasksEnum.OTHER)
                    TasksEnum.WORK -> WorkTask(str[0], str[1], TasksEnum.WORK)
                    TasksEnum.SPORT -> SportTask(str[0], str[1], TasksEnum.SPORT)
                    TasksEnum.MEDICAL -> MedicalTask(str[0], str[1], TasksEnum.MEDICAL)
                    TasksEnum.EDUCATION -> EducationTask(str[0], str[1], TasksEnum.EDUCATION)
                }
            task.whichDaysOfWeek(getWhichDays(str[3]))
            task.steps(getSteps(str[4]))
            task.createdAt(str[5])
            task.checked(str[6].toBoolean())
            task.whenActivated(str[7].toInt())
            return task
        } else {
            println("Return null object, because error occurred")
            return null
        }
    }

    /**
     * Function for list serializing
     * @return string list representation
     */
    fun list_serialize(): str {
        return ""
    }

    /**
     * Function for list deserializing
     * @return TaskList object from given string
     */
    fun list_deserialize(): TaskList? {
        return null
    }

    private fun getWhichDays(str: str): List<str> {
        val listToReturn = ArrayList<str>()
        val values = str.filter { FILTER_FUNCTION(it) }.split(",")
        for (innerString in values) {
            listToReturn.add(innerString)
        }
        return listToReturn
    }

    private fun getSteps(stepsString: str): List<str> {
        val listToReturn = mutableListOf<str>()
        val splittedString = stepsString.filter { FILTER_FUNCTION(it) }.split(",")
        splittedString.map { x -> listToReturn.add(x) }
        return listToReturn
    }
}
