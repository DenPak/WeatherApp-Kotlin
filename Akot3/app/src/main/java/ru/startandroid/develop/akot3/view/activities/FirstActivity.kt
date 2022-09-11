package ru.startandroid.develop.akot3.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ru.startandroid.develop.akot3.R
import ru.startandroid.develop.akot3.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moveAnimation()
    }

    override fun onRestart() {
        super.onRestart()
        moveAnimation()
    }

    private fun startThread() {
        Thread {
            Thread.sleep(5000)
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }.start()
    }

    private fun moveAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_in)
        binding.imageView.animation = animation
        animation.start()
        startThread()
    }
}