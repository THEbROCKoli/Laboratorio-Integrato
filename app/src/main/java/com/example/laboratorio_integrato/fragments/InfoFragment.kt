package com.example.laboratorio_integrato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.laboratorio_integrato.ActivityViewModel
import com.example.laboratorio_integrato.R
import com.example.laboratorio_integrato.databinding.FragmentInfoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val RESID = "resId"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var resId: Int? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: ActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resId = it.getInt(RESID)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        // Inflate the layout for this fragment
        val stringArray = resources.getStringArray(R.array.contents)

        binding.backButton.setOnClickListener {
            viewModel.navBarShouldHide.value= false
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.content.text = resId?.let {
            stringArray[it]
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param resId Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(resId: Int, /** param2: String*/) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(RESID, resId)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}