package ru.rychkovkirill.englishclub.ui.user.mainpage.sessions

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
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.databinding.FragmentSessionsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions.UpcomingShiftsListAdapter
import javax.inject.Inject


class SessionsFragment : Fragment() {

    private var _binding: FragmentSessionsBinding? = null
    private val binding: FragmentSessionsBinding
        get() = _binding ?: throw RuntimeException("FragmentSessionsBinding == null")

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }

    val adapter = UpcomingShiftsListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeGetUserShifts()
        viewModel.getMyShifts()
        setUpAdapter()
    }

    private fun subscribeGetUserShifts(){
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
                    adapter.upcomingShiftsList = it.result
                }
            }
        }
    }

    private fun setUpAdapter() {

        adapter.onItemSelectListener = {
            val bundle = Bundle()
            bundle.putString("name", it.name)
            bundle.putString("startDate", it.start_date)
            bundle.putString("endDate", it.end_date)
            bundle.putString("content", it.description)
            bundle.putString("participantsNumber", it.participants_number.toString())
            bundle.putString("id", it.id.toString())
            bundle.putString("number", it.number.toString())
            findNavController().navigate(R.id.action_sessionsFragment_to_sessionsDetailsFragment, bundle)
        }
        binding.rvSessionsList.adapter = adapter
    }

}