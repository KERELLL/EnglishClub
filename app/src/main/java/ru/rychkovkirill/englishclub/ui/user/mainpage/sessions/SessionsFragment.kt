package ru.rychkovkirill.englishclub.ui.user.mainpage.sessions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.databinding.FragmentSessionsBinding
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter


class SessionsFragment : Fragment() {

    private var _binding: FragmentSessionsBinding? = null
    private val binding: FragmentSessionsBinding
        get() = _binding ?: throw RuntimeException("FragmentSessionsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val adapter = MainListAdapter()
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_sessionsFragment_to_sessionsDetailsFragment)
        }
        binding.rvSessionsList.adapter = adapter
    }

}