package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import saberliou.demo.profile.R
import saberliou.demo.profile.SleepNight
import saberliou.demo.profile.databinding.FragmentSleepNightsBinding
import saberliou.demo.profile.util.EventObserver

@AndroidEntryPoint
class SleepNightsFragment : Fragment() {
    private lateinit var binding: FragmentSleepNightsBinding
    private lateinit var sleepNightAdapter: SleepNightAdapter
    private val viewModel: SleepNightsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sleep_nights,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        sleepNightAdapter = SleepNightAdapter(object : SleepNightAdapter.OnSleepNightClickListener {
            override fun onSleepNightClicked(night: SleepNight) {
                viewModel.onSleepNightClicked(night.id)
            }
        })
        binding.rvSleepNights.adapter = sleepNightAdapter
        binding.rvSleepNights.layoutManager = GridLayoutManager(requireActivity(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when (position) {
                    0 -> 3
                    else -> 1
                }
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)

//        viewModel.nights.observe(viewLifecycleOwner, { nights ->
//            nights?.let {
////                sleepNightAdapter.nights = nights
//                sleepNightAdapter.submitListWithHeader(nights)
//            }
//        })

        viewModel.navigateToSaveSleepNightEntity.observe(
            viewLifecycleOwner,
            EventObserver { id ->
                findNavController().navigate(
                    SleepNightsFragmentDirections.actionSleepNightsFragmentToSaveSleepNightFragment(
                        id
                    )
                )
            })

        viewModel.navigateToSleepNightEntityDetail.observe(
            viewLifecycleOwner,
            EventObserver { id ->
                findNavController().navigate(
                    SleepNightsFragmentDirections.actionSleepNightsFragmentToSleepNightDetailFragment(
                        id
                    )
                )
            })

        viewModel.showSnackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                getString(R.string.cleared_message),
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }
}
