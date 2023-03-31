package ru.rychkovkirill.englishclub.ui.admin.users

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesDetailsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentUserDetailsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding: FragmentUserDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentUserDetailsBinding == null")

    private lateinit var viewModel: UsersViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdminUI()
        subscribeUpdateUser()
        fillUI()
    }

    private fun setUpAdminUI() {
        binding.updateUserButton.setOnClickListener {
            val firstName = binding.firstnameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val nickname = binding.usernameEditText.text.toString()
            val mediaLink = binding.mediaLinkEditText.text.toString()
            val email = requireArguments().getString("email")
            if (email != null) {
                viewModel.updateUser(email, firstName, lastName, nickname, mediaLink)
            }
        }
    }

    private fun subscribeUpdateUser(){
        viewModel.updateUser.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Error -> {
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ViewState.Success -> {
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }


    private fun fillUI(){
        val firstName = requireArguments().getString("firstName")
        val lastName = requireArguments().getString("lastName")
        val nickname = requireArguments().getString("nickname")
        val media_link = requireArguments().getString("media_link")
        binding.firstnameEditText.setText(firstName)
        binding.lastNameEditText.setText(lastName)
        binding.usernameEditText.setText(nickname)
        binding.mediaLinkEditText.setText(media_link)
    }

}