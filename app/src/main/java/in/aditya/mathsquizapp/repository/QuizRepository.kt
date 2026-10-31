package `in`.aditya.mathsquizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import `in`.aditya.mathsquizapp.model.QuestionsList
import `in`.aditya.mathsquizapp.retrofit.QuestionsAPI
import `in`.aditya.mathsquizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//Acts as a single source of truth for quiz data , Executes background network requests
//using Kotlin Co routine To questions api Add posts response data into live data
class QuizRepository {
    var questionsAPI: QuestionsAPI
    init {
        //call to create a questions api object
        questionsAPI= RetrofitInstance().getRetrofitInstance()
            .create(QuestionsAPI::class.java)
    }

    fun getQuestionsFromAPI(): LiveData<QuestionsList>{
        val data = MutableLiveData<QuestionsList>()
        var questionsList: QuestionsList
        GlobalScope.launch(Dispatchers.IO){
            //returning the response in questionsList
            val response=questionsAPI.getQuestions()
            if (response != null){
                //saving data to list
                questionsList=response.body()!!

                data.postValue(questionsList) //use with globalScope coroutine
                Log.i("TAG",""+data.value)
            }
        }
        return data
    }
}