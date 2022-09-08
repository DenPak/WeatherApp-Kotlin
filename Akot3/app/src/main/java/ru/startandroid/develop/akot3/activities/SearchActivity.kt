package ru.startandroid.develop.akot3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.startandroid.develop.akot3.activities.MainActivity

import ru.startandroid.develop.akot3.databinding.ActivitySearchBinding
import ru.startandroid.develop.akot3.fragments.FragmentDays

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MainActivity::class.java)

        binding.buttonSearch.setOnClickListener {
            if (!getChekInput()){

                val edCity = binding.textInputC.text.toString()
                val edDays:Int = binding.textInputD.text.toString().toInt()
                intent.putExtra("city",edCity)
                intent.putExtra("days",edDays)

                startActivity(intent)
            }
        }
    }

   private fun getChekInput():Boolean{
        binding.apply {
            if (textInputC.text.isNullOrEmpty())textInputC.error = "Заполните все поля!"
            if(textInputD.text.isNullOrEmpty())textInputD.error = "Заполните все поля!"
            return textInputC.text.isNullOrEmpty()||textInputD.text.isNullOrEmpty()
        }
    }

    override fun onBackPressed() {

    }

    }
