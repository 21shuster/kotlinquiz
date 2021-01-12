package es.sublimestudio.kotlinquiz.views

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import es.sublimestudio.kotlinquiz.DataHolder
import es.sublimestudio.kotlinquiz.R
import es.sublimestudio.kotlinquiz.models.Question
import kotlinx.android.synthetic.main.fragment_questions.*

class QuestionsFragment : Fragment() {

    lateinit var questions: List<Question>
    lateinit var timerCount: CountDownTimer
    var position = 0
    var isClickable = true

    val TIME_MAX = 5000
    var mainHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questions = DataHolder.quiz.questions
        loadQuestion()


        btn1.setOnClickListener {
            checkAnswer(it as MaterialButton)
        }

        btn2.setOnClickListener {
            checkAnswer(it as MaterialButton)
        }

        btn3.setOnClickListener {
            checkAnswer(it as MaterialButton)
        }

        btn4.setOnClickListener {
            checkAnswer(it as MaterialButton)
        }

    }

    fun initTimer() {
        progress.max = TIME_MAX
        progress.progress = TIME_MAX
        isClickable = true

        timerCount = object : CountDownTimer(TIME_MAX.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                progress.setProgress(millisUntilFinished.toInt(), true)
            }

            override fun onFinish() {
                progress.setProgress(0, true)
                timeFinshed()
            }
        }
        timerCount.start()
    }

    fun timeFinshed(btnSelected: MaterialButton? = null) {
        timerCount.cancel()
        checkOtherButtons(btnSelected)
        mainHandler.postDelayed({
            nextQuestion()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainHandler.removeCallbacksAndMessages(null)
        timerCount.cancel()
    }

    fun checkAnswer(btnSelected: MaterialButton, isFinised: Boolean = false) {
        if (isClickable || isFinised) {
            isClickable = false

            changeColorButtons(btnSelected, isFinised)
            timeFinshed(btnSelected)
        }
    }

    fun changeColorButtons(btnSelected: MaterialButton, isFinised: Boolean) {
        val respuesta = questions[position].answers[btnSelected.tag as Int]

        Log.v(
            "MIAPP",
            "La respuesta ${btnSelected.tag} es ${respuesta.isCorrect} => ${respuesta.title}"
        )

        if (respuesta.isCorrect) {
            btnSelected.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    btnSelected.context,
                    R.color.green
                )
            );
        } else if (isFinised) {
            btnSelected.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    btnSelected.context,
                    R.color.darkbluegray
                )
            );
        } else {
            btnSelected.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    btnSelected.context,
                    R.color.red
                )
            );
        }
    }

    fun checkOtherButtons(btnSelected: MaterialButton?) {
        if (btn1 != btnSelected) {
            changeColorButtons(btn1, true)
        }
        if (btn2 != btnSelected) {
            changeColorButtons(btn2, true)
        }
        if (btn3 != btnSelected) {
            changeColorButtons(btn3, true)
        }
        if (btn4 != btnSelected) {
            changeColorButtons(btn4, true)
        }
    }

    fun nextQuestion() {
        if (position < questions.size - 1) {
            position++
            loadQuestion()
        } else {
            Log.v("MIAPP", "HEMOS FINALIZADO")
        }
    }

    fun loadQuestion() {
        qTitle.text = questions[position].title

        val q = questions[position]
        btn1.text = q.answers[0].title
        btn1.tag = 0

        btn2.text = q.answers[1].title
        btn2.tag = 1

        btn3.text = q.answers[2].title
        btn3.tag = 2

        btn4.text = q.answers[3].title
        btn4.tag = 3

        initTimer()

        resetButtons(btn1)
        resetButtons(btn2)
        resetButtons(btn3)
        resetButtons(btn4)
    }

    fun resetButtons(btnSelected: MaterialButton) {
        btnSelected.setBackgroundTintList(
            ContextCompat.getColorStateList(
                btnSelected.context,
                R.color.purple_500
            )
        );
    }
}