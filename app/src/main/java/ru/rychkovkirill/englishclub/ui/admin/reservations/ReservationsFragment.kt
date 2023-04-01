package ru.rychkovkirill.englishclub.ui.admin.reservations

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.databinding.FragmentNearestSessionsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentReservationsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import ru.rychkovkirill.englishclub.ui.user.mainpage.activities.ActivitiesListAdapter
import javax.inject.Inject


class ReservationsFragment : Fragment() {

    private var _binding: FragmentReservationsBinding? = null
    private val binding: FragmentReservationsBinding
        get() = _binding ?: throw RuntimeException("FragmentReservationsBinding == null")


    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }

    private val adapter = MainListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        subscribeGetUserShifts()
        subscribeApproveShift()
        viewModel.getReservations()
    }

    private fun subscribeGetUserShifts(){
        viewModel.reservationsListResult.observe(viewLifecycleOwner) {
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
                    adapter.reservationList = it.result
                }
            }
        }
    }

    private fun subscribeApproveShift(){
        viewModel.approveShiftResult.observe(viewLifecycleOwner) {
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
                        "Вы записали пользователя!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.getReservations()
                }
            }
        }
    }

    private fun setUpAdapter() {
        adapter.onApproveClickListener = {
            viewModel.approveShift(it.id)
        }
        adapter.onItemSelectListener = {

        }
        binding.rvReservationsList.adapter = adapter
    }
}