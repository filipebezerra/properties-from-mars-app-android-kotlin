package dev.filipebezerra.app.propertiesfrommars.propertydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertyDetailScreenBinding
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertyDetailScreenBinding.inflate
import dev.filipebezerra.app.propertiesfrommars.propertydetail.PropertyDetailViewModel.Companion.factory

class PropertyDetailScreen : BottomSheetDialogFragment() {

    private var _binding: PropertyDetailScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val arguments: PropertyDetailScreenArgs by navArgs()

    private val viewModel: PropertyDetailViewModel by viewModels{ factory(arguments.property) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflate(inflater, container, false)
        .apply {
            _binding = this
            viewModel = this@PropertyDetailScreen.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}