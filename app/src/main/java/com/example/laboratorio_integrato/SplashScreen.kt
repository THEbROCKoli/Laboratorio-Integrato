package com.example.laboratorio_integrato

import android.animation.AnimatorInflater
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import com.example.laboratorio_integrato.databinding.ActivitySplashScreenBinding


class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        binding.pen.x=fromDPToPixels(500f)
        binding.penAlt.x=fromDPToPixels(500f)
        binding.penAlt.y= fromDPToPixels(-133f)
        binding.rightBracketAlt.scaleY=0f
        binding.leftBracketAlt.scaleY=0f
        binding.title.scaleY=0f
        binding.title.scaleX=0f

            /*binding.eraser.animate().apply {
                binding.eraser.pivotX=0f
                scaleX(0f)
            }*/

           /* binding.pen.animate().apply {
                translationXBy(fromDPToPixels(-500f))
                rotation(-720f)
                duration=500
            }.withEndAction {
                binding.pen.pivotX=0f
                binding.pen.pivotY= fromDPToPixels(132f)


                val animateEraser = AnimatorInflater.loadAnimator( this.baseContext, R.animator.eraser_animation)
                animateEraser.setTarget(binding.eraser)
                animateEraser.start()
                val animatePen = AnimatorInflater.loadAnimator( this.baseContext, R.animator.pen_animation)
                animatePen.setTarget(binding.pen)
                animatePen.start()

                val animateLeftBracket = AnimatorInflater.loadAnimator(this.baseContext, R.animator.left_bracket_animation)
                animateLeftBracket.setTarget(binding.leftBracket)
                animateLeftBracket.start()
                val animateRightBracket = AnimatorInflater.loadAnimator(this.baseContext, R.animator.right_bracket_animation)
                animateRightBracket.setTarget(binding.rightBracket)
                animateRightBracket.start()
                animateRightBracket.doOnEnd {*/
                    binding.penAlt.animate().apply {
                        translationXBy(fromDPToPixels(-557f))
                        rotation(-720f)
                        duration=500
                    }.withEndAction {
                        binding.leftBracketAlt.animate().apply {
                            scaleY(1f)
                            duration=400
                        }
                        binding.rightBracketAlt.animate().apply {
                            scaleY(1f)
                            duration=400
                            startDelay=900
                        }
                        val animateEraserAlt = AnimatorInflater.loadAnimator(this.baseContext, R.animator.eraser_animation)
                        animateEraserAlt.setTarget(binding.eraserAlt)
                        animateEraserAlt.startDelay=600
                        animateEraserAlt.start()
                        val animatePenAlt = AnimatorInflater.loadAnimator( this.baseContext, R.animator.pen_animation_alt)
                        animatePenAlt.setTarget(binding.penAlt)
                        animatePenAlt.start()
                        animatePenAlt.doOnEnd {
                            binding.inkDrop.visibility= View.VISIBLE
                            binding.inkDrop.animate().apply {
                                translationX(-38f)
                                translationY(50f)
                            }.withEndAction {
                                binding.title.animate().apply {
                                    scaleY(1f)
                                    scaleX(0.02f)
                                    duration=100
                                }.withEndAction {
                                    binding.title.animate().apply {
                                        scaleX(1f)
                                        duration=300
                                    }.withEndAction {
                                        val tempIntent = Intent(this, TempActivity::class.java)
                                        val intent = Intent(this, StartingActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            }

                        }
                    }
                //}
            //}
        setContentView(binding.root)
    }
    private fun fromDPToPixels(desiredDP: Float): Float {
        val scale = resources.displayMetrics.density
        return (desiredDP * scale + 0.5f)
    }
}