package es.sublimestudio.kotlinquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import es.sublimestudio.kotlinquiz.models.Quiz
import es.sublimestudio.kotlinquiz.views.NameFragment
import es.sublimestudio.kotlinquiz.views.QuestionsFragment
import kotlinx.android.synthetic.main.activity_quiz.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class QuizActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        DataHolder.quiz = readJsonFromFile()

        supportFragmentManager.beginTransaction().replace(container.id, NameFragment()).commit()

    }

    fun readJsonFromFile(): Quiz {
        var json = ""
        try {
            val url = "questions.json"
            val bufferedReader = BufferedReader(
                InputStreamReader(assets.open(url))
            )
            val paramsBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                paramsBuilder.append(line)
                line = bufferedReader.readLine()
            }
            json = paramsBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val heroes = Gson().fromJson(json, Quiz::class.java)
        return heroes
    }
}