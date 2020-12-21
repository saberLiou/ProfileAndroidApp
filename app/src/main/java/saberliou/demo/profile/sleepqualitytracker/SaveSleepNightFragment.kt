package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentSaveSleepNightBinding
import saberliou.demo.profile.util.EventObserver

@AndroidEntryPoint
class SaveSleepNightFragment : Fragment() {
    private lateinit var binding: FragmentSaveSleepNightBinding
    private val args: SaveSleepNightFragmentArgs by navArgs()
    private val viewModel: SaveSleepNightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_save_sleep_night,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)

        viewModel.navigateToSleepNights.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                SaveSleepNightFragmentDirections.actionSaveSleepNightFragmentToSleepNightsFragment()
            )
        })

        viewModel.initialize(args.nightId)
    }
}
