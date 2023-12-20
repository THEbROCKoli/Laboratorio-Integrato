package com.example.laboratorio_integrato.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.laboratorio_integrato.R
import com.example.laboratorio_integrato.databinding.FragmentMapBinding
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMapBinding
    protected var mUnityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mUnityPlayer = UnityPlayer(activity)
        var xDown= 0f
        var yDown= 0f
        binding = FragmentMapBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {

            it.elevation=1f
            mUnityPlayer.apply {
                val intent = Intent(requireContext(), UnityPlayerActivity::class.java)
                intent.putExtra("unity", "-force-gles30")
                val anim = it.background as AnimationDrawable

                anim.setExitFadeDuration(500)

                it.animate().apply {
                    scaleX(100f)
                    scaleY(100f)
                    duration=1000
                }.withEndAction {

                    UnityPlayer.UnitySendMessage("TextFromAndroid", "ValueFromAndroid", "Swipe")
                    /* Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
                     The command line arguments are passed as a string, separated by spaces
                     UnityPlayerActivity calls this from 'onCreate'
                     Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
                     See https://docs.unity3d.com/Manual/CommandLineArguments.html
                     @param cmdLine the current command line arguments, may be null
                     @return the modified command line string or null*/
                    startActivity(intent)
                }
                anim.start()
            }

        }

        binding.buttonAries.setOnClickListener {

            mUnityPlayer.apply {
                val intent = Intent(requireContext(), UnityPlayerActivity::class.java)
                intent.putExtra("unity", "-force-gles30")
                val anim = it.background as AnimationDrawable

                anim.setExitFadeDuration(500)

                it.animate().apply {
                    scaleX(100f)
                    scaleY(100f)
                    duration=1000
                }.withEndAction {

                    UnityPlayer.UnitySendMessage("TextFromAndroid", "ValueFromAndroid", "")
                    /* Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
                     The command line arguments are passed as a string, separated by spaces
                     UnityPlayerActivity calls this from 'onCreate'
                     Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
                     See https://docs.unity3d.com/Manual/CommandLineArguments.html
                     @param cmdLine the current command line arguments, may be null
                     @return the modified command line string or null*/
                    startActivity(intent)
                }
                anim.start()
            }

        }

        binding.image.setOnTouchListener { v, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        xDown = event.x
                        yDown = event.y
                    }

                    MotionEvent.ACTION_UP -> {
                        var shouldMoveX = 0f
                        var shouldMoveY = 0f

                        if (binding.image.x > 0)
                            shouldMoveX = -binding.image.x
                        else if (fromPixelsToDP(binding.image.x) < -1400) {
                            val movementDP = fromPixelsToDP(binding.image.x) + 1400
                            shouldMoveX = -fromDPToPixels(movementDP)
                        }
                        if (binding.image.y > 0)
                            shouldMoveY = -binding.image.y
                        else if (fromPixelsToDP(binding.image.y) < -130) {
                            val movementDP = fromPixelsToDP(binding.image.y) + 130
                            shouldMoveY = -fromDPToPixels(movementDP)
                        }

                        Log.d("posizione attuale y", fromPixelsToDP(binding.image.y).toString())
                        binding.image.animate().apply {
                            translationXBy(shouldMoveX)
                            translationYBy(shouldMoveY)
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val movedX = event.x
                        val movedY = event.y
                        val distanceX = movedX - xDown
                        val distanceY = movedY - yDown
                        binding.image.x = binding.image.x + distanceX
                        binding.image.y = binding.image.y + distanceY
                    }
                }

            true
        }


        return binding.root
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
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}