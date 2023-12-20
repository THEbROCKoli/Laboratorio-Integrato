package com.example.laboratorio_integrato

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.VISIBLE
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.laboratorio_integrato.databinding.ActivityTempBinding
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class TempActivity : UnityPlayerActivity() {
    lateinit var binding: ActivityTempBinding
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ActivityTempBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        val metrics = windowManager.currentWindowMetrics
        val bounds = metrics.bounds
        val windowInsets = metrics.windowInsets

        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars()
                    or WindowInsets.Type.displayCutout()
                    or WindowInsets.Type.statusBars())

        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom
        val height = bounds.height()//-insetsHeight
        val width = bounds.width()-insetsWidth

        setContentView(binding.root)
        var xDown= 0f
        var yDown= 0f



        binding.button.setOnClickListener {

            it.elevation=1f
            val unityIntent = Intent(this, UnityPlayerActivity::class.java)
            unityIntent.putExtra("unity", "-force-gles30")

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
                startActivity(unityIntent)
                //lollo pizza
            }
            anim.start()
        }

        binding.buttonAries.setOnClickListener {

            val unityIntent = Intent(this, UnityPlayerActivity::class.java)
            unityIntent.putExtra("unity", "-force-gles30")

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
                startActivity(unityIntent)
            }
            anim.start()
        }

        binding.image.setOnTouchListener { v, event ->

            Log.d("dimensioni", "schermo alto $height, Y = ${binding.image.height}")
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    yDown = event.y
                }

                MotionEvent.ACTION_UP ->{
                    var shouldMoveX = 0f
                    var shouldMoveY = 0f

                    if(binding.image.x > 0)
                        shouldMoveX = -binding.image.x
                    else if ( -(binding.image.x - width)  > binding.image.width )
                        shouldMoveX = -(binding.image.width + (binding.image.x - width))

                    if (binding.image.y > 0)
                        shouldMoveY = -(binding.image.height + (binding.image.y - height))
                    else if ( -(binding.image.y - height)  > binding.image.height )
                        shouldMoveY = -(binding.image.height + (binding.image.y - height))


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
    }


    private fun fromDPToPixels(desiredDP: Float): Float {
        val scale = resources.displayMetrics.density
        return (desiredDP * scale + 0.5f)
    }
}