package com.anastr.pixabaydemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anastr.pixabaydemo.R
import com.anastr.pixabaydemo.databinding.FragmentImageDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

class ImageDetailFragment: Fragment() {

    private var _binding: FragmentImageDetailBinding? = null
    private val binding
        get() = _binding!!

    private val args: ImageDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load(args.imageInfo.imageUrl)
            .into(binding.ivImage)

        val imageSize = args.imageInfo.imageSize.toFloat() / 1_000_000f
        binding.tvImageSize.text = getString(R.string.image_size, imageSize)
        binding.tvImageType.text = getString(R.string.image_type, args.imageInfo.type)

        val chips = args.imageInfo.tags.trim()
            .splitToSequence(", ")
            .map { createChip(it) }
        chips.forEach { binding.chipGroup.addView(it) }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.bMoreDetails.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.tvUserName.text = args.imageInfo.userName
        binding.tvLikes.text = args.imageInfo.likes.toString()
        binding.tvViews.text = args.imageInfo.views.toString()
        binding.tvComments.text = args.imageInfo.comments.toString()
        binding.tvDownloads.text = args.imageInfo.downloads.toString()
    }

    private fun createChip(text: String) = Chip(requireContext()).apply {
        this.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
