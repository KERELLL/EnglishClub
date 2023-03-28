package ru.rychkovkirill.englishclub.ui.user.mainpage.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.databinding.FragmentNewsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding ?: throw RuntimeException("FragmentNewsBinding == null")

    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
        App.appComponent
    }

    private val adapter = NewsListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpAdminUI()
        subscribeAddNews()
        subscribeGetAllNews()
        binding.createNews.setOnClickListener {
            showAddNewsAlert()
        }
        viewModel.getNews()
    }

    private fun setUpAdminUI() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            binding.createNews.isVisible = true
        } else {
            binding.createNews.isVisible = false
        }
    }

    private fun showAddNewsAlert() {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Добавить новость")
        val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout, null)
        val etTitle = dialogLayout.findViewById<EditText>(R.id.et_title)
        val etContent = dialogLayout.findViewById<EditText>(R.id.et_content)
        builder.setPositiveButton("Добавить") { dialog, which ->
            viewModel.addNews(etTitle.text.toString(), etContent.text.toString())
        }
        .setNegativeButton("Назад") { dialog, which ->
            {
                dialog.dismiss()
            }
        }
        builder.setView(dialogLayout)
        builder.show()
    }

    private fun subscribeAddNews() {
        viewModel.addNewsResult.observe(viewLifecycleOwner) {
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
                        "Новость успешно добавлена!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.getNews()
                }
            }
        }
    }

    private fun subscribeGetAllNews(){
        viewModel.newsListResult.observe(viewLifecycleOwner) {
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
                    adapter.newsList = it.result
                }
            }
        }
    }

    private fun setUpAdapter() {
        if (authRepository.getUser()!![2] == "ADMIN") {
            adapter.onItemSelectListener = {
                findNavController().navigate(R.id.action_newsFragmentAdmin_to_newsDetailsFragmentAdmin)
            }
        }else{
            adapter.onItemSelectListener = {
                findNavController().navigate(R.id.action_newsFragment_to_newsDetailsFragment)
            }
        }

        binding.rvNewsList.adapter = adapter
    }

}