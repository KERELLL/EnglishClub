package ru.rychkovkirill.englishclub.ui.user.mainpage.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentNewsBinding
import ru.rychkovkirill.englishclub.databinding.FragmentSessionsBinding
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainListAdapter


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding ?: throw RuntimeException("FragmentNewsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter(){
        val adapter = MainListAdapter()
        adapter.onItemSelectListener = {
            findNavController().navigate(R.id.action_newsFragment_to_newsDetailsFragment)
        }
        binding.rvNewsList.adapter = adapter
    }
}