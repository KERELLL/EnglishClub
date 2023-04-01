package ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions

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
import ru.rychkovkirill.englishclub.databinding.FragmentNearestSessionsDetailsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentSessionsDetailsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class NearestSessionsDetailsFragment : Fragment() {

    private var _binding: FragmentNearestSessionsDetailsBinding? = null
    private val binding: FragmentNearestSessionsDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearestSessionsDetailsBinding == null")

    private lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentNearestSessionsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = requireArguments().getString("name")
        val startDate = requireArguments().getString("startDate")
        val endDate = requireArguments().getString("endDate")
        val content = requireArguments().getString("content")
        val participantsNumber = requireArguments().getString("participantsNumber")
        val id = requireArguments().getString("id")
        val number = requireArguments().getString("number")
        subscribeReserveShift()
        setUpAdminUI()
        binding.tvTitle.text = name
        binding.tvDate.text = startDate!!.split('T')[0] + " - " + endDate!!.split('T')[0]
        binding.tvContent.text = content
        binding.tvParticipantsNumber.text = participantsNumber + " участников"
        binding.tvNumber.text = number

        binding.responseTaskButton.setOnClickListener {
            viewModel.reserveShift(id!!.toInt())
        }
    }
    private fun setUpAdminUI() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            binding.responseTaskButton.isVisible = false
        } else {
            binding.responseTaskButton.isVisible = true
        }
    }

    fun subscribeReserveShift(){
        viewModel.reserveShiftResult.observe(viewLifecycleOwner){
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
                        "Заявка успешно отправлена!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }
}