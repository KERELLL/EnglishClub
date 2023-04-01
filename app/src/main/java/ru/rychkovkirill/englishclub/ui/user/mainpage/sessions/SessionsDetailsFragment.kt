package ru.rychkovkirill.englishclub.ui.user.mainpage.sessions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesDetailsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentSessionsDetailsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class SessionsDetailsFragment : Fragment() {

    private var _binding: FragmentSessionsDetailsBinding? = null
    private val binding: FragmentSessionsDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentSessionsDetailsBinding == null")

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
        _binding = FragmentSessionsDetailsBinding.inflate(inflater, container, false)
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

        binding.tvTitle.text = name
        binding.tvDate.text = startDate!!.split('T')[0] + " - " + endDate!!.split('T')[0]
        binding.tvContent.text = content
        binding.tvParticipantsNumber.text = participantsNumber + " участников"
        binding.tvNumber.text = number
    }

}