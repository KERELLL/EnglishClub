package ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentNearestSessionsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import java.util.*
import javax.inject.Inject


class NearestSessionsFragment : Fragment() {

    private var _binding: FragmentNearestSessionsBinding? = null
    private val binding: FragmentNearestSessionsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearestSessionsBinding == null")

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
        _binding = FragmentNearestSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpAdminUI()
        subscribeGetUpcomingShifts()
        viewModel.getUpcomingShifts()
        subscribeAddShift()
        binding.createSession.setOnClickListener {
            showAddNewsAlert()
        }
    }

    private fun setUpAdminUI() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            binding.createSession.isVisible = true
        } else {
            binding.createSession.isVisible = false
        }
    }

    private fun subscribeGetUpcomingShifts(){
        viewModel.upcomingShiftsListResult.observe(viewLifecycleOwner) {
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

    private fun subscribeAddShift() {
        viewModel.addShiftResult.observe(viewLifecycleOwner) {
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
                        "Новость успешно добавлена!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.getUpcomingShifts()
                }
            }
        }
    }

    private fun showAddNewsAlert() {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Добавить сессию")
        val dialogLayout = layoutInflater.inflate(R.layout.edit_text_create_session, null)
        val etTitle = dialogLayout.findViewById<EditText>(R.id.et_title)
        val etStartDate = dialogLayout.findViewById<EditText>(R.id.et_start_date)
        val etEndDate = dialogLayout.findViewById<EditText>(R.id.et_end_date)
        val etDescription = dialogLayout.findViewById<EditText>(R.id.et_description)
        val etNumber = dialogLayout.findViewById<EditText>(R.id.et_number)
        etStartDate.setOnClickListener {
            setUpDateListener(it as EditText)
        }
        etEndDate.setOnClickListener {
            setUpDateListener(it as EditText)
        }
        builder.setPositiveButton("Добавить") { dialog, which ->
            viewModel.addShift(etTitle.text.toString(),
                etNumber.text.toString().toInt(),
                etDescription.text.toString(),
                etStartDate.text.toString() + "T17:10:53.962Z",
                etEndDate.text.toString() + "T17:10:53.962Z")
        }
            .setNegativeButton("Назад") { dialog, which ->
                {
                    dialog.dismiss()
                }
            }
        builder.setView(dialogLayout)
        builder.show()
    }

    private fun setUpDateListener(editText: EditText){
        editText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener
            {
                override fun onDateSet(
                    view: DatePicker?,
                    year: Int,
                    month: Int,
                    dayOfMonth: Int
                ) {
                    editText.setText(year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString())
                }

            },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    private fun setUpAdapter(){

            adapter.onItemSelectListener = {
                val bundle = Bundle()
                bundle.putString("name", it.name)
                bundle.putString("startDate", it.start_date)
                bundle.putString("endDate", it.end_date)
                bundle.putString("content", it.description)
                bundle.putString("participantsNumber", it.participants_number.toString())
                bundle.putString("id", it.id.toString())
                bundle.putString("number", it.number.toString())
                if (authRepository.getUser()!![2] == "USER") {
                    findNavController().navigate(
                        R.id.action_nearestSessionsFragment_to_nearestSessionsDetailsFragment,
                        bundle
                    )
                }else{
                    findNavController().navigate(
                        R.id.action_nearestSessionsFragmentAdmin_to_nearestSessionsDetailsFragment2,
                        bundle
                    )
            }
        }
        binding.rvNearestSessionsList.adapter = adapter
    }
}