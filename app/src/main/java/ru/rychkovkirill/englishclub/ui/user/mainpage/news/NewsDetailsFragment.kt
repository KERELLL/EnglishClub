package ru.rychkovkirill.englishclub.ui.user.mainpage.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.databinding.FragmentNewsDetailsBinding
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.ViewModelFactory
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.ui.user.mainpage.MainViewModel
import javax.inject.Inject


class NewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding: FragmentNewsDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentNewsDetailsBinding == null")

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy {
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
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeGetAllNews()
        val title = requireArguments().getString("title")
        val date = requireArguments().getString("date")
        val content = requireArguments().getString("content")
        binding.tvTitle.text = title
        binding.tvDate.text = date
        binding.tvContent.text = content
    }

    private fun subscribeGetAllNews(){
        viewModel.newsInfoResult.observe(viewLifecycleOwner) {
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
                    binding.tvContent.text = it.result.content.toString()
                    binding.tvDate.text = it.result.created_at.toString()
                    binding.tvTitle.text = it.result.title.toString()
                }
            }
        }
    }

}