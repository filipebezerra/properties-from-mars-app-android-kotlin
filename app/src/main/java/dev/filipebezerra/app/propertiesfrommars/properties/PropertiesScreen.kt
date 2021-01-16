package dev.filipebezerra.app.propertiesfrommars.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.filipebezerra.app.propertiesfrommars.ServiceLocator
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertiesScreenBinding
import dev.filipebezerra.app.propertiesfrommars.util.ext.setupSnackbar

class PropertiesScreen : Fragment() {

    private var _binding: PropertiesScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PropertiesViewModel by viewModels{
        PropertiesViewModel.factory(ServiceLocator.proviceMarsApiService())
    }

    private val navController: NavController by lazy { findNavController() }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setupSnackbar(
            viewLifecycleOwner,
            viewModel.snackbarTextResource,
            Snackbar.LENGTH_LONG,
        )
        with(binding) {
            propertiesList.adapter = MarsPropertyAdapter(MarsPropertyItemListener { propertyTapped ->
              navController.navigate(PropertiesScreenDirections
                  .actionPropertiesScreenToPropertyDetailScreen(propertyTapped))
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}