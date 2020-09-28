package saberliou.demo.profile.sleepqualitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import saberliou.demo.profile.AnyViewModelFactory
import saberliou.demo.profile.AppDatabase
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentSleepNightDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SleepNightDetailFragment.newInstance] factory method to create an instance of this fragment.
 */
class SleepNightDetailFragment : Fragment() {
    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment [SleepNightDetailFragment].
     */
    companion object {
        fun newInstance() = SleepNightDetailFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    private lateinit var binding: FragmentSleepNightDetailBinding
    private lateinit var viewModel: SleepNightDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_night_detail, container, false)

        val nightId = SleepNightDetailFragmentArgs.fromBundle(requireArguments()).nightId
        viewModel = ViewModelProvider(this, AnyViewModelFactory {
            SleepNightDetailViewModel(
                nightId,
                AppDatabase.getInstance(requireActivity().application).sleepNightDao
            )
        }).get(SleepNightDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToSleepNights.observe(viewLifecycleOwner, { toNavigate ->
            when (toNavigate) {
                true -> {
                    findNavController().navigate(SleepNightDetailFragmentDirections.actionSleepNightDetailFragmentToSleepNightsFragment())

                    viewModel.onSleepNightsNavigationDone()
                }
            }
        })

        return binding.root
    }
}
