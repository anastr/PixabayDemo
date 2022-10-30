package com.anastr.pixabaydemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anastr.pixabaydemo.databinding.FragmentHomeBinding
import com.anastr.pixabaydemo.state.CallState
import com.anastr.pixabaydemo.ui.adpter.ImagesAdapter
import com.anastr.pixabaydemo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val imagesAdapter = ImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagesAdapter.setOnItemClickListener { imageInfo ->
            val direction = HomeFragmentDirections.actionHomeFragmentToImageDetailFragment(imageInfo)
            findNavController().navigate(direction)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = imagesAdapter

        binding.bReload.setOnClickListener { viewModel.loadImages() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.imagesState.collect { imagesState ->
                        when(imagesState) {
                            is CallState.Error -> {
                                binding.recyclerView.visibility = View.GONE
                                binding.layoutError.visibility = View.VISIBLE
                                binding.progress.visibility = View.GONE
                                binding.tvErrorMessage.text = imagesState.message
                            }
                            CallState.Loading -> {
                                binding.recyclerView.visibility = View.GONE
                                binding.layoutError.visibility = View.GONE
                                binding.progress.visibility = View.VISIBLE
                            }
                            is CallState.Success -> {
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.layoutError.visibility = View.GONE
                                binding.progress.visibility = View.GONE
                                imagesAdapter.updateImages(imagesState.data)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
