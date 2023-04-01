package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesDetailsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentNewsDetailsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject

class ActivitiesDetailsFragment : Fragment() {

    private var _binding: FragmentActivitiesDetailsBinding? = null
    private val binding: FragmentActivitiesDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentActivitiesDetailsBinding == null")

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
        _binding = FragmentActivitiesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = requireArguments().getString("title")
        val startDate = requireArguments().getString("startDate")
        val endDate = requireArguments().getString("endDate")
        val content = requireArguments().getString("content")
        val points = requireArguments().getString("points")
        val id = requireArguments().getString("id")
        val isActive = requireArguments().getString("isActive")
        subscribeResponseTask()
        setUpAdminUI()
        if(isActive == "no"){
            binding.tvDate.setTextColor(Color.RED)
        }
        binding.tvTitle.text = title
        binding.tvDate.text = startDate!!.split('T')[0] + " - " + endDate!!.split('T')[0]
        binding.tvContent.text = content
        binding.tvPoints.text = points + " очков"

        binding.responseTaskButton.setOnClickListener {
            setUpAlert()
        }
    }

    private fun setUpAdminUI() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            binding.responseTaskButton.isVisible = false
        } else {
            binding.responseTaskButton.isVisible = true
        }
    }


    private fun subscribeResponseTask(){
        viewModel.submitTaskResult.observe(viewLifecycleOwner) {
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
                        "Ответ принят!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setUpAlert(){
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Добавить ответ")
        val dialogLayout = layoutInflater.inflate(R.layout.edit_text_answer, null)
        val answer = dialogLayout.findViewById<EditText>(R.id.et_answer)
        builder.setPositiveButton("Добавить") { dialog, which ->
            viewModel.submitTask(requireArguments().getString("id")!!.toInt(), answer.toString())
        }
            .setNegativeButton("Назад") { dialog, which ->
                {
                    dialog.dismiss()
                }
            }
        builder.setView(dialogLayout)
        builder.show()
    }
}