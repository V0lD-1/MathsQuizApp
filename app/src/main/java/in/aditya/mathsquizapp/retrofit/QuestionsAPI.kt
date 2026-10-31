package `in`.aditya.mathsquizapp.retrofit

import `in`.aditya.mathsquizapp.model.QuestionsList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsAPI {
// Interface declaring Retrofit HTTP GET endpoints to fetch questions.
    @GET("questionsapi.php")
    suspend fun getQuestions(): Response<QuestionsList>
}