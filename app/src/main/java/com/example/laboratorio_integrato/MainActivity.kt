package com.example.laboratorio_integrato

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class MainActivity : UnityPlayerActivity() {
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        val metrics = windowManager.currentWindowMetrics

        val bounds = metrics.bounds


        val windowInsets = metrics.windowInsets
        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars()
                    or WindowInsets.Type.displayCutout()
                    or WindowInsets.Type.statusBars()

        )

        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val height = bounds.height()//-insetsHeight
        val width = bounds.width()-insetsWidth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var xDown= 0f
        var yDown= 0f
        val imageView = findViewById<ConstraintLayout>(R.id.image)
        val lowButton = findViewById<View>(R.id.button)
        val ariesButton = findViewById<View>(R.id.buttonAries)

        Log.d("distanze", "X = ${imageView.x}, Y = ${imageView.y}")

        lowButton.setOnClickListener {

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
            }
            anim.start()
        }

        ariesButton.setOnClickListener {

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

        imageView.setOnTouchListener { v, event ->

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    yDown = event.y
                }

                MotionEvent.ACTION_UP ->{
                    var shouldMoveX = 0f
                    var shouldMoveY = 0f

                    if(imageView.x > 0)
                        shouldMoveX = -imageView.x
                    else if ( -(imageView.x - width)  > imageView.width )
                        shouldMoveX = -(imageView.width + (imageView.x - width))

                    if (imageView.y > 0)
                        shouldMoveY = -(imageView.height + (imageView.y - height))
                    else if ( -(imageView.y - height)  > imageView.height )
                        shouldMoveY = -(imageView.height + (imageView.y - height))


                    imageView.animate().apply {
                        translationXBy(shouldMoveX)
                        translationYBy(shouldMoveY)
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    val movedX = event.x
                    val movedY = event.y
                    val distanceX = movedX - xDown
                    val distanceY = movedY - yDown
                    imageView.x = imageView.x + distanceX
                    imageView.y = imageView.y + distanceY
                }
            }
            true
        }
    }
}