package `in`.aditya.mathsquizapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import `in`.aditya.mathsquizapp.R
import `in`.aditya.mathsquizapp.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark("#42429D".toColorInt())
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.scoreTextView.text = "Your Score: " + MainActivity.result + "/" + MainActivity.totalQuestions

        binding.btnBack.setOnClickListener {
            restartQuiz()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                restartQuiz()
            }
        })
    }

    private fun restartQuiz() {
        val intent = Intent(this@ResultsActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}