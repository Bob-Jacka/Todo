package com.kirill.todo.tasks.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.kirill.todo.R
import com.kirill.todo.tasks.data.TaskList

/**
 * Page for adding list of task
 */
class AddList : AppCompatActivity() {

    private lateinit var nameOfList_widget: EditText
    private lateinit var goBack_btn: Button
    private lateinit var nameOfList: String

    private lateinit var new_task_list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_list)
        nameOfList_widget = findViewById(R.id.listName)
        goBack_btn = findViewById(R.id.goBackBtn)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameOfList_widget.addTextChangedListener { editable ->
            nameOfList = editable.toString()
        }
        goBack_btn.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}