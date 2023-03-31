package ru.rychkovkirill.englishclub.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.ActivityMainBinding
import ru.rychkovkirill.englishclub.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding: ActivityProfileBinding
        get() = _binding ?: throw RuntimeException("ActivityProfileBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}