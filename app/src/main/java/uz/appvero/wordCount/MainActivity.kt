package uz.appvero.wordCount

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import uz.appvero.wordCount.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resultTextViewWord.text = "0"
        binding.resultTextViewChar.text = "0"
        binding.resultTextViewSpace.text = "0"

        val sharedPreferences = getSharedPreferences("Mode", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        val actualNightModeActive: Boolean

        if (sharedPreferences.contains("night")) {
            actualNightModeActive = sharedPreferences.getBoolean("night", false)
        } else {
            val currentSystemNightMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            actualNightModeActive = currentSystemNightMode
            editor.putBoolean("night", actualNightModeActive)
            editor.apply()
        }

        if (actualNightModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding.switchBtn.isChecked = !actualNightModeActive

        binding.switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("night", true) // night mode is ON
            }
            editor.apply()
        }



        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.moveBtn.visibility = View.VISIBLE

                val wordCount = WordCounter.countWordsInText(s?.toString())
                val charCount = s?.length ?: 0
                val spaceCount = s?.count { it.isWhitespace() } ?: 0

                binding.resultTextViewWord.text = wordCount.toString()
                binding.resultTextViewChar.text = charCount.toString()
                binding.resultTextViewSpace.text = spaceCount.toString()

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