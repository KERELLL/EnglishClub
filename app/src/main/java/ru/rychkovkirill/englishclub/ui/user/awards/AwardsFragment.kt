package ru.rychkovkirill.englishclub.ui.user.awards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rychkovkirill.englishclub.databinding.FragmentAwardsBinding
import ru.rychkovkirill.englishclub.ui.user.MainListAdapter


class AwardsFragment : Fragment() {

    private var _binding: FragmentAwardsBinding? = null
    private val binding: FragmentAwardsBinding
        get() = _binding ?: throw RuntimeException("FragmentAwardsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAwardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainListAdapter()
        binding.rvAwardsList.adapter = adapter
    }

}