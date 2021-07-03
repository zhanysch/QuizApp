package kg.appsstudio.daggerparttwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.appsstudio.daggerparttwo.R
import kg.appsstudio.daggerparttwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vm : MainViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}