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
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import ru.rychkovkirill.englishclub.ui.user.mainpage.news.NewsListAdapter
import javax.inject.Inject


class ActivitiesFragment : Fragment() {

    private var _binding: FragmentActivitiesBinding? = null
    private val binding: FragmentActivitiesBinding
        get() = _binding ?: throw RuntimeException("FragmentActivitiesBinding == null")


    private lateinit var viewModel: MainViewModel

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
        viewModel.getAllTasks()
    }


    private fun setUpAdapter(){
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_activitiesFragment_to_activitiesDetailsFragment)
        }
        binding.rvActivitiesList.adapter = adapter
    }

    private fun subscribeGetActivities(){
        viewModel.allTaskListResult.observe(viewLifecycleOwner) {
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
                    adapter.tasksList = it.result
                }
            }
        }
    }

}