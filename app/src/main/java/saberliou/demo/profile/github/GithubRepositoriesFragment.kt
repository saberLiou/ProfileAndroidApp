package saberliou.demo.profile.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import saberliou.demo.profile.AnyViewModelFactory
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentGithubRepositoriesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GithubRepositoriesFragment.newInstance] factory method to create an instance of this fragment.
 */
class GithubRepositoriesFragment : Fragment() {
    companion object {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of fragment GithubRepositoriesFragment.
         */
        @JvmStatic
        fun newInstance() = GithubRepositoriesFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    private lateinit var binding: FragmentGithubRepositoriesBinding
    private val viewModel: GithubRepositoriesViewModel by lazy {
        ViewModelProvider(this, AnyViewModelFactory {
            GithubRepositoriesViewModel()
        }).get(GithubRepositoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_github_repositories, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
