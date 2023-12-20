package com.example.laboratorio_integrato.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio_integrato.ActivityViewModel
import com.example.laboratorio_integrato.R
import com.example.laboratorio_integrato.adapters.IntrestPointsAdapter
import com.example.laboratorio_integrato.databinding.FragmentHomeBinding
import com.example.laboratorio_integrato.model.IntrestPoint
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: Int? = null
    private var param2: Int? = null
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mapFragment = MapFragment.newInstance()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        var mapIsExpanded = false

        binding.recycler.apply {
            var dataList = mutableListOf<IntrestPoint>()
            listOf("lollo","pizza","ermenegildo").forEach {
                dataList.add(IntrestPoint(it))
            }
            adapter = IntrestPointsAdapter(dataList, requireActivity().supportFragmentManager, ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java))
            layoutManager = LinearLayoutManager(
                this.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            //layoutManager = GridLayoutManager(this.context, 2)
        }

        binding.xpansion.setOnClickListener {

            if(!mapIsExpanded){
                animatedRemoveMarginFromView(binding.mapView)
                animatedExpansionToTop(binding.mapView, binding.parent, binding.xpansion)
                listOf(binding.title, binding.intrestPoints).forEach {
                    it.animate().apply {
                        alpha(0f)
                        duration = 50
                    }
                }
                binding.xpansion.animate().apply {
                    translationYBy(-((binding.parent.height - binding.mapView.height).toFloat()) + fromDPToPixels(40f))
                }.withEndAction {
                    changeTextWithAlpha(binding.xpansion, "Riduci")
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        setTransition(TRANSIT_FRAGMENT_FADE)
                        add( R.id.fragmentContainer, mapFragment)
                        commit()
                    }
                }
            } else {
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    setTransition(TRANSIT_FRAGMENT_FADE)
                    remove(mapFragment)
                    commit()
                }
                binding.mapView.animate().apply {
                    scaleX(1f)
                    scaleY(1f)
                }
                binding.xpansion.animate().apply {
                    translationYBy(((binding.parent.height - binding.mapView.height).toFloat()) - fromDPToPixels(40f))
                }.withEndAction {
                    changeTextWithAlpha(binding.xpansion, "Espandi")
                }
                listOf(binding.title, binding.intrestPoints).forEach {
                    it.animate().alpha(1f)
                }

            }
            mapIsExpanded= !mapIsExpanded
            //Toast.makeText(this.context, binding.mapView.marginEnd, Toast.LENGTH_SHORT).show()
        }





        return binding.root
    }

    private fun animatedRemoveMarginFromView(view: View){
        val originalWidth = view.width
        val desiredWidth = originalWidth + view.marginEnd + view.marginStart
        val multiplier = desiredWidth.toFloat() / originalWidth.toFloat()
        view.animate().apply {
            scaleX(multiplier)
        }
    }
    private fun animatedExpansionToTop(expandingView: View, parent: LinearLayout, label : TextView){
        val currentHeight = expandingView.height
        val desiredHeight = parent.height - label.height
        val multiplier = desiredHeight.toFloat() / currentHeight.toFloat()
        expandingView.pivotY = expandingView.height.toFloat()
        expandingView.animate().apply {
            scaleY(multiplier)
        }
    }
    private fun changeTextWithAlpha(textView: TextView, newText : String){
        textView.animate().apply {
            alpha(0f)
            duration =100
        }.withEndAction {
            textView.text = newText
            textView.animate().apply {
                alpha(1f)
                duration =100
            }
        }
    }

    private fun fromDPToPixels(desiredDP: Float) : Float {
        val scale = resources.displayMetrics.density
        return (desiredDP * scale + 0.5f)
    }
    private fun fromPixelsToDP(currentPixels: Float) : Float{
        val scale = resources.displayMetrics.density
        return (currentPixels / scale + 0.5f)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: Int, param2: Int*/) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    //putInt(ARG_PARAM1, param1)
                    //putInt(ARG_PARAM2, param2)
                }
            }
    }
}