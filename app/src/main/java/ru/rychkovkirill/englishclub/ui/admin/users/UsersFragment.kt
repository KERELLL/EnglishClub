package ru.rychkovkirill.englishclub.ui.admin.users

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentUsersBinding
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter
import javax.inject.Inject

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding == null")

    private lateinit var viewModel: UsersViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val adapter = UsersListAdapter()

    private val component by lazy{
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
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLoginResult()
        setUpAdapter()
        viewModel.getUsers()
    }

    private fun setUpAdapter(){
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_activitiesFragment_to_activitiesDetailsFragment)
        }
        binding.rvUsersList.adapter = adapter
    }

    private fun subscribeLoginResult(){
        viewModel.usersList.observe(viewLifecycleOwner){
            when (it) {
                is ViewState.Loading -> {
//                    binding.pbLogin.isVisible = true
                }
                is ViewState.Error -> {
//                    binding.pbLogin.isVisible = false
                    Snackbar.make(
                        requireView(),
                        it.result.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ViewState.Success -> {
//                    binding.pbLogin.isVisible = false
                    adapter.userList = it.result
                }
            }
        }
    }

}