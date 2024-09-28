package com.kirill.todo.tasks.core

import com.kirill.todo.tasks.data.AbstractTask
import com.kirill.todo.tasks.data.TasksEnum
import com.kirill.todo.tasks.tasksByTypes.EducationTask
import com.kirill.todo.tasks.tasksByTypes.MedicalTask
import com.kirill.todo.tasks.tasksByTypes.OtherTask
import com.kirill.todo.tasks.tasksByTypes.ReadTask
import com.kirill.todo.tasks.tasksByTypes.SportTask
import com.kirill.todo.tasks.tasksByTypes.WorkTask

object TaskSerializer {

    private const val DELIMITER: String = "|"
    private val FILTER_FUNCTION = { x: Char -> (x != '[' && x != ']') }

    fun serialize(task: AbstractTask): String {
        return "${task.taskName()}$DELIMITER${task.description()}$DELIMITER${task.type()}$DELIMITER${
            task.whichDaysOfWeek().toString().replace(" ", "")
        }$DELIMITER${task.steps()}$DELIMITER${task.createdAt()}$DELIMITER${task.checked()}$DELIMITER${task.whenActivated()}"
    }

    fun deserialize(string: String): AbstractTask? {
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
            return null
        }
    }

    private fun getWhichDays(str: String): List<String> {
        val listToReturn = ArrayList<String>()
        val values = str.filter { FILTER_FUNCTION(it) }.split(",")
        for (innerString in values) {
            listToReturn.add(innerString)
        }
        return listToReturn
    }

    private fun getSteps(stepsString: String): List<String> {
        val listToReturn = mutableListOf<String>()
        val splittedString = stepsString.filter { FILTER_FUNCTION(it) }.split(",")
        splittedString.map { x -> listToReturn.add(x) }
        return listToReturn
    }
}
