package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import ru.rychkovkirill.englishclub.ui.user.mainpage.news.NewsListAdapter
import java.util.Calendar
import javax.inject.Inject


class ActivitiesFragment : Fragment() {

    private var _binding: FragmentActivitiesBinding? = null
    private val binding: FragmentActivitiesBinding
        get() = _binding ?: throw RuntimeException("FragmentActivitiesBinding == null")


    private lateinit var viewModel: MainViewModel

    private lateinit var bottomSheetRoot: LinearLayout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }

    private val adapter = ActivitiesListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        subscribeGetActivities()
        setUpAdminUI()
        subscribeAddTask()
        subscribeGetAllActivities()
        subscribeGetMyActivities()
        bottomSheetRoot = binding.bottomSheet.root
        bottomSheetRoot.visibility = View.GONE
        setUpButtonTaskListClick()
        binding.createTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
        viewModel.getActiveTasks()
    }


    private fun setUpAdminUI() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            binding.createTask.isVisible = true
            binding.myTaskButton.isVisible = false
        } else {
            binding.createTask.isVisible = false
            binding.allTaskButton.isVisible = false
        }
    }

    private fun showAddTaskBottomSheet() {
        bottomSheetRoot.visibility = View.VISIBLE
        val mBottomBehavior =
            BottomSheetBehavior.from(bottomSheetRoot)
        mBottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        setUpDateListener(binding.bottomSheet.etStartDate)
        setUpDateListener(binding.bottomSheet.etEndDate)
        binding.bottomSheet.addTaskButton.setOnClickListener {
            val title = binding.bottomSheet.etTitle.text.toString()
            val description = binding.bottomSheet.etDescription.text.toString()
            val points = binding.bottomSheet.etPoints.text.toString()
            val startDate = binding.bottomSheet.etStartDate.text.toString() + "T17:10:53.962Z"
            val endDate = binding.bottomSheet.etEndDate.text.toString() + "T17:10:53.962Z"
            if(title.isNotBlank() && description.isNotBlank() && points.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank()){
                viewModel.addTask(title, description, points.toInt(), startDate, endDate)
            }else{
                Snackbar.make(
                    requireView(),
                    "Поля не могут быть пустыми! Заполните поля!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpAdapter(){
        if (authRepository.getUser()!![2] == "ADMIN") {
            adapter.onItemSelectListener = {
                val bundle = Bundle()
                bundle.putString("title", it.title)
                bundle.putString("startDate", it.start_date)
                bundle.putString("endDate", it.end_date)
                bundle.putString("content", it.description)
                bundle.putString("points", it.points.toString())
                if(it.is_active){
                    bundle.putString("isActive", "yes")
                }else{
                    bundle.putString("isActive", "no")
                }

                findNavController().navigate(R.id.action_activitiesFragmentAdmin_to_activitiesDetailsFragment2, bundle)
            }
        }else{
            adapter.onItemSelectListener = {
                val bundle = Bundle()
                bundle.putString("title", it.title)
                bundle.putString("startDate", it.start_date)
                bundle.putString("endDate", it.end_date)
                bundle.putString("content", it.description)
                bundle.putString("points", it.points.toString())
                bundle.putString("id", it.id.toString())
                if(it.is_active){
                    bundle.putString("isActive", "yes")
                }else{
                    bundle.putString("isActive", "no")
                }
                findNavController().navigate(R.id.action_activitiesFragment_to_activitiesDetailsFragment, bundle)
            }
        }
        binding.rvActivitiesList.adapter = adapter
    }

    private fun subscribeGetActivities(){
        viewModel.activeTasksListResult.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.pbTasks.isVisible = true
                }
                is ViewState.Error -> {
                    binding.pbTasks.isVisible = false
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ViewState.Success -> {
                    binding.pbTasks.isVisible = false
                    adapter.tasksList = it.result
                }
            }
        }
    }

    private fun subscribeGetAllActivities(){
        viewModel.allTasksListResult.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.pbTasks.isVisible = true
                }
                is ViewState.Error -> {
                    binding.pbTasks.isVisible = false
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ViewState.Success -> {
                    binding.pbTasks.isVisible = false
                    adapter.tasksList = it.result
                }
            }
        }
    }

    private fun subscribeGetMyActivities(){
        viewModel.myTasksListResult.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.pbTasks.isVisible = true
                }
                is ViewState.Error -> {
                    binding.pbTasks.isVisible = false
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ViewState.Success -> {
                    binding.pbTasks.isVisible = false
                    adapter.tasksList = it.result
                }
            }
        }
    }

    private fun subscribeAddTask(){
        viewModel.addTaskResult.observe(viewLifecycleOwner) {
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
                    bottomSheetRoot.visibility = View.GONE
                    binding.bottomSheet.etTitle.setText("")
                    binding.bottomSheet.etDescription.setText("")
                    binding.bottomSheet.etPoints.setText("")
                    binding.bottomSheet.etStartDate.setText("")
                    binding.bottomSheet.etEndDate.setText("")
                    viewModel.getActiveTasks()
                }
            }
        }
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

    private fun setUpButtonTaskListClick(){
        binding.activeTaskButton.setOnClickListener {
            setUpButtonTaskListBackground(it as TextView)
            viewModel.getActiveTasks()
        }
        binding.allTaskButton.setOnClickListener {
            setUpButtonTaskListBackground(it as TextView)
            viewModel.getAllTasks()
        }
        binding.myTaskButton.setOnClickListener {
            setUpButtonTaskListBackground(it as TextView)
            viewModel.getMyTasks()
        }
    }

    private fun setUpButtonTaskListBackground(currentTasksList: TextView){
        if(currentTasksList.text == "Активные"){
            setSelectedColor("ACTIVE")
        } else if(currentTasksList.text == "Все"){
            setSelectedColor("ALL")
        } else if(currentTasksList.text == "Мои"){
            setSelectedColor("MY")
        }
    }

    private fun setSelectedColor(currentTaskList: String){
        if(currentTaskList == "ACTIVE"){
            binding.activeTaskButton.setBackgroundResource(R.drawable.button_task_selected_background)
            binding.activeTaskButton.setTextColor(Color.WHITE)
            binding.allTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.allTaskButton.setTextColor(Color.BLACK)
            binding.myTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.myTaskButton.setTextColor(Color.BLACK)
        }else if(currentTaskList == "MY"){
            binding.activeTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.activeTaskButton.setTextColor(Color.BLACK)
            binding.allTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.allTaskButton.setTextColor(Color.BLACK)
            binding.myTaskButton.setBackgroundResource(R.drawable.button_task_selected_background)
            binding.myTaskButton.setTextColor(Color.WHITE)
        } else if(currentTaskList == "ALL"){
            binding.activeTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.activeTaskButton.setTextColor(Color.BLACK)
            binding.allTaskButton.setBackgroundResource(R.drawable.button_task_selected_background)
            binding.allTaskButton.setTextColor(Color.WHITE)
            binding.myTaskButton.setBackgroundResource(R.drawable.button_task_background)
            binding.myTaskButton.setTextColor(Color.BLACK)
        }
    }

}