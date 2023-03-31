package ru.rychkovkirill.englishclub.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentLoginBinding
import ru.rychkovkirill.englishclub.databinding.FragmentProfileBinding
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.SplashActivity
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.admin.users.UsersViewModel
import ru.rychkovkirill.englishclub.ui.user.auth.AuthActivity
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding == null")

    private val component by lazy{
        App.appComponent
    }
    private lateinit var viewModel: UsersViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvEmail.text = authRepository.getUser()!![1]
        binding.tvUsername.text = authRepository.getUser()!![3]
        if(authRepository.getUser()!![2] == "USER"){
            binding.tvUser.text = "Ученик"
        }else{
            binding.tvUser.text = "Учитель"
        }
        binding.tvMedia.text = authRepository.getUser()!![6]
        binding.tvName.text = authRepository.getUser()!![4] + " " + authRepository.getUser()!![5]
        binding.logoutButton.setOnClickListener {
            authRepository.logout()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
        }

        val bundle = Bundle()
        bundle.putString("firstName", authRepository.getUser()!![4])
        bundle.putString("lastName", authRepository.getUser()!![5])
        bundle.putString("nickname", authRepository.getUser()!![2])
        if(authRepository.getUser()!![6].isNullOrBlank()){
            bundle.putString("media_link", "")
        }else{
            bundle.putString("media_link", authRepository.getUser()!![6])
        }
//        binding.updateButton.setOnClickListener {
//            findNavController().navigate(R.id.action_profileFragment_to_profileEditFragment, bundle)
//        }
    }


}


