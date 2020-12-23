package saberliou.demo.profile.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import saberliou.demo.profile.R
import saberliou.demo.profile.databinding.FragmentGithubRepositoriesBinding

@AndroidEntryPoint
class GithubRepositoriesFragment : Fragment() {
    //    private val viewModel: GithubRepositoriesViewModel by viewModels {
//        AnyViewModelFactory {
//            GithubRepositoriesViewModel()
//        }
//    }

    private lateinit var binding: FragmentGithubRepositoriesBinding
    private val viewModel: GithubRepositoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_github_repositories,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
