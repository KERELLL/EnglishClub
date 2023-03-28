package ru.rychkovkirill.englishclub.ui.user.mainpage.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentActivitiesBinding
import ru.rychkovkirill.englishclub.databinding.FragmentAwardsBinding
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter


class ActivitiesFragment : Fragment() {

    private var _binding: FragmentActivitiesBinding? = null
    private val binding: FragmentActivitiesBinding
        get() = _binding ?: throw RuntimeException("FragmentActivitiesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }


    private fun setUpAdapter(){
        val adapter = MainListAdapter()
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_activitiesFragment_to_activitiesDetailsFragment)
        }
        binding.rvActivitiesList.adapter = adapter
    }

}