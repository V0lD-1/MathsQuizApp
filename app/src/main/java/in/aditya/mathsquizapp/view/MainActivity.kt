package `in`.aditya.mathsquizapp.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import `in`.aditya.mathsquizapp.R
import `in`.aditya.mathsquizapp.databinding.ActivityMainBinding
import `in`.aditya.mathsquizapp.model.Questions
import `in`.aditya.mathsquizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: QuizViewModel
    lateinit var questionsList: List<Questions>
    private var keepSplashScreen = true

    companion object{
        var result=0
        var totalQuestions=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashScreen = false
        }, 1000)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark("#42429D".toColorInt())
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //resetting the scores
        result=0
        totalQuestions=0
        //getting the response from the retrofit
        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]


        //displaying the first question
        GlobalScope.launch(Dispatchers.Main){
            viewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.isNotEmpty()) {
                    questionsList = it
                    Log.i("TAGY","this is the first question: ${questionsList[0]}")
                    binding.apply{
                        questionTextView.text=questionsList[0]!!.question // !! ignored
                        radioButton1.text=questionsList[0].option1
                        radioButton2.text=questionsList[0].option2
                        radioButton3.text=questionsList[0].option3
                        radioButton4.text=questionsList[0].option4
                    }
                }
            })
        }
        //next button functionality
        var i=1
        binding.apply{
            BtnNext.setOnClickListener(View.OnClickListener{
                val selectedOption= radioGroup?.checkedRadioButtonId

                if(selectedOption!=-1){
                    val radbutton=findViewById<View>(selectedOption!!)as RadioButton

                    questionsList.let{
                        if (i<it.size!!){
                            //getting the no. of questions
                            totalQuestions=it.size
                            //checking for the first question
                            if(radbutton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                resultTextView?.text="Correct Answer: $result"
                            }

                            //displaying the next questions
                            questionTextView.text = "Question ${i+1}: " + questionsList[i].question
                            radioButton1.text = it[i].option1
                            radioButton2.text = it[i].option2
                            radioButton3.text = it[i].option3
                            radioButton4.text = it[i].option4
                            //radioGroup.clearCheck()
                            //i++
                            //checking if it is the last question
                            if(i == it.size!!.minus(1)){
                                BtnNext.text="FINISH"
                            }
                            radioGroup.clearCheck()
                            i++
                        } else {
                            if (radbutton.text.toString().equals(it[i-1].correct_option)) {
                                result++
                                resultTextView?.text = "Correct Answer: $result"
                            } else{
                            }
                            val intent= Intent(this@MainActivity, ResultsActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }else{
                    showCustomToast("Please select one option")
                }
            })
        }
    }

    private fun showCustomToast(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint("#42429D".toColorInt())
            setTextColor(Color.WHITE)
            show()
        }
    }
}