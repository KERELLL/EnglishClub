package ru.rychkovkirill.englishclub.ui.profile

import android.content.Context
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
import ru.rychkovkirill.englishclub.databinding.FragmentProfileBinding
import ru.rychkovkirill.englishclub.databinding.FragmentProfileEditBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.admin.users.UsersViewModel
import javax.inject.Inject


class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding: FragmentProfileEditBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileEditBinding == null")

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
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setUpUI() {
        binding.updateUserButton.setOnClickListener {
            val firstName = binding.firstnameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val nickname = binding.usernameEditText.text.toString()
            val mediaLink = binding.mediaLinkEditText.text.toString()
            viewModel.updateMe(firstName, lastName, nickname, mediaLink)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUpdateMe()
        setUpUI()
        fillUI()
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

    private fun subscribeUpdateMe(){
        viewModel.updateMe.observe(viewLifecycleOwner) {
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
}