package dev.filipebezerra.app.propertiesfrommars.properties

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.ServiceLocator
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertiesScreenBinding

class PropertiesScreen : Fragment() {

    private var _binding: PropertiesScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PropertiesViewModel by viewModels{
        PropertiesViewModel.factory(ServiceLocator.proviceMarsApiService())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = PropertiesScreenBinding.inflate(inflater, container, false)
        .apply {
            _binding = this
            viewModel = this@PropertiesScreen.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}