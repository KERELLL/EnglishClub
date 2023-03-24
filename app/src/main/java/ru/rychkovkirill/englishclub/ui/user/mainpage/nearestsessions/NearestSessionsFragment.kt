package ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentNearestSessionsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentNewsBinding
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter


class NearestSessionsFragment : Fragment() {

    private var _binding: FragmentNearestSessionsBinding? = null
    private val binding: FragmentNearestSessionsBinding
        get() = _binding ?: throw RuntimeException("FragmentNearestSessionsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearestSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter(){
        val adapter = MainListAdapter()
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_nearestSessionsFragment_to_nearestSessionsDetailsFragment)
        }
        binding.rvNearestSessionsList.adapter = adapter
    }
}