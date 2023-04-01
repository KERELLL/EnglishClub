package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

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
import ru.rychkovkirill.englishclub.databinding.FragmentCheckTaskBinding
import ru.rychkovkirill.englishclub.databinding.FragmentCheckTaskListBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class CheckTaskFragment : Fragment() {

    private var _binding: FragmentCheckTaskBinding? = null
    private val binding: FragmentCheckTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentCheckTaskBinding == null")


    private lateinit var viewModel: MainViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentCheckTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = requireArguments().getString("id")
        val answer = requireArguments().getString("answer")
        binding.tvAnswer.text = answer
        subscribeCheckTask()
        viewModel.checkTask(id!!.toInt())
    }

    private fun subscribeCheckTask(){
        viewModel.myShiftsListResult.observe(viewLifecycleOwner) {
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
                        "Успешно проверено!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

}