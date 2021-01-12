package es.sublimestudio.kotlinquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import es.sublimestudio.kotlinquiz.models.Quiz
import kotlinx.android.synthetic.main.activity_main.*
import render.animations.Attention
import render.animations.Fade
import render.animations.Render
import render.animations.Zoom

class MainActivity : AppCompatActivity() {

    val render = Render(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appear()
        tada()
        disappear()
        nextActivity()
    }

    fun appear() {
        render.setAnimation(Zoom().In(logo))
        render.setDuration(500)
        render.start()
    }

    fun disappear() {
        Handler(Looper.getMainLooper()).postDelayed({
            render.setAnimation(Fade().Out(logo))
            render.setDuration(700)
            render.start()
        }, 1500)
    }

    fun tada() {
        Handler(Looper.getMainLooper()).postDelayed({
            render.setAnimation(Attention().Tada(logo))
            render.setDuration(700)
            render.start()
        }, 1000)
    }

    fun nextActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }, 2000)
    }
}