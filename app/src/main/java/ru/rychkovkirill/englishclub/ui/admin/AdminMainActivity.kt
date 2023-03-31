package ru.rychkovkirill.englishclub.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.ActivityAdminMainBinding
import ru.rychkovkirill.englishclub.databinding.ActivityMainBinding
import ru.rychkovkirill.englishclub.ui.profile.ProfileActivity

class AdminMainActivity : AppCompatActivity() {
    private var _binding: ActivityAdminMainBinding? = null
    private val binding: ActivityAdminMainBinding
        get() = _binding ?: throw RuntimeException("ActivityAdminMainBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHost.findNavController()

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}