package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.databinding.FragmentCheckTaskListBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class CheckTaskListFragment : Fragment() {
    private var _binding: FragmentCheckTaskListBinding? = null
    private val binding: FragmentCheckTaskListBinding
        get() = _binding ?: throw RuntimeException("FragmentCheckTaskListBinding == null")


    private lateinit var viewModel: MainViewModel

    private val adapter = CheckTasksListAdapter()

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
        _binding = FragmentCheckTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeCheckTasks()
        setUpAdapter()
        viewModel.getCheckResponses()
    }
    private fun subscribeCheckTasks(){
        viewModel.checkResponsesListResult.observe(viewLifecycleOwner) {
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
                    adapter.responsesList = it.result
                }
            }
        }
    }

    private fun setUpAdapter(){
        adapter.onItemSelectListener = {
            val bundle = Bundle()
            bundle.putString("id", it.id.toString())
            bundle.putString("answer", it.answer)
            findNavController().navigate(R.id.action_checkTaskListFragment_to_checkTaskFragment, bundle)
        }
        binding.rvCheckList.adapter = adapter
    }
}