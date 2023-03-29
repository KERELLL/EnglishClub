package ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun showAddNewsAlert() {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Добавить сессию")
        val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout, null)
        val etTitle = dialogLayout.findViewById<EditText>(R.id.et_title)
        val etContent = dialogLayout.findViewById<EditText>(R.id.et_content)
        builder.setPositiveButton("Добавить") { dialog, which ->

        }
            .setNegativeButton("Назад") { dialog, which ->
                {
                    dialog.dismiss()
                }
            }
        builder.setView(dialogLayout)
        builder.show()
    }

    private fun setUpAdapter(){
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_nearestSessionsFragment_to_nearestSessionsDetailsFragment)
        }
        binding.rvNearestSessionsList.adapter = adapter
    }
}