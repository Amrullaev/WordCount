package com.example.slideview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.slideview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val words = s?.trim()?.split("\\s+".toRegex())?.filter { it.isNotEmpty() }
                val wordCount = words?.size ?: 0
                val charCount = s?.length ?: 0
                val spaceCount = s?.count { it.isWhitespace() } ?: 0

                binding.resultTextView.text =
                    "So'zlar: $wordCount\nHarflar: $charCount\nProbel: $spaceCount"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.moveBtn.setOnTouchListener(object : View.OnTouchListener {
            var dx = 0f
            var dy = 0f
            var x = 0f
            var y = 0f


            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // Save initial touch point and button position
                        dx = event.rawX - view.x
                        dy = event.rawY - view.y
                    }

                    MotionEvent.ACTION_MOVE -> {
                        // Calculate new button position
                        x = event.rawX - dx
                        y = event.rawY - dy

                        // Update button position
                        view.x = x
                        view.y = y
                    }

                    MotionEvent.ACTION_UP -> {
                        // Handle the button release if needed
                    }
                }
                return true
            }

        })
    }

}