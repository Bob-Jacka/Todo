package com.kirill.todo.tasks.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.kirill.todo.R
import com.kirill.todo.tasks.data.GlobalSettings

class Steps : AppCompatActivity() {

    private lateinit var textInput: EditText
    private lateinit var acceptBtn: Button
    private var newStep: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)
        initActivity()
    }

    private fun initActivity() {
        textInput = findViewById(R.id.editTextText)
        acceptBtn = findViewById(R.id.AcceptBtn)
        acceptBtn.setOnClickListener { returnStep() }
        textInput.addTextChangedListener(afterTextChanged = { newStep = it.toString() })
    }

    private fun returnStep() {
        val intent = Intent(this, AddTask::class.java)
        intent.putExtra(
            GlobalSettings.serializeKeyStep,
            newStep ?: ""
        )
        startActivity(intent)
    }
}