package com.example.laboratorio_integrato

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.lifecycle.ViewModelProvider
import com.example.laboratorio_integrato.databinding.ActivityStartingBinding
import com.example.laboratorio_integrato.fragments.HomeFragment
import com.example.laboratorio_integrato.fragments.StoryFragment

class StartingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartingBinding
    private lateinit var viewModel : ActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartingBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        binding.fragmentContainer.pivotY=0f
        viewModel.navBarShouldHide
            .observe(this){
            if (it){
                binding.navBar.animate().apply {
                    translationY(fromDPToPixels(80f))
                }
                animatedResizeToDesiredHeight(binding.fragmentContainer, (binding.fragmentContainer.height)+fromDPToPixels(80f))

            } else{
                binding.navBar.animate().apply {
                    translationY(fromDPToPixels(0f))
                }
                binding.fragmentContainer.animate().apply {
                    scaleY(1f)
                }
            }
        }

        val allHighLightables = listOf(binding.homeSelected, binding.centerSelected, binding.storySelected)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, HomeFragment.newInstance())
            commit()
        }
        allHighLightables.forEach {
            if(it != binding.homeSelected){
                it.scaleX=0f
                it.scaleY=0f
            }
        }

        var selectedItem: Menu
        val itemList = listOf(binding.home, binding.center, binding.story)

        itemList.forEach {
            val index = itemList.indexOf(it)
            it.setOnClickListener {

                selectedItem= Menu.values()[index]
                changeMenuItem(selectedItem, allHighLightables)
                supportFragmentManager.beginTransaction().apply {
                    val fragments = listOf(
                        HomeFragment.newInstance(),
                        HomeFragment.newInstance(),
                        StoryFragment.newInstance()
                    )

                    replace(R.id.fragmentContainer, fragments[index])
                    commit()
                }
            }
        }

        setContentView(binding.root)
    }



    enum class Menu {
        HOME, CENTER, STORY
    }
    private fun changeMenuItem(selectedItem: Menu, views: List<View>){
        views.forEach {
            animatedHiding(it)
        }
        when(selectedItem){
            Menu.values()[0]-> animatedAppearing(views[0])
            Menu.values()[1]-> animatedAppearing(views[1])
            Menu.values()[2]-> animatedAppearing(views[2])
            else -> Toast.makeText(this, "lollo", Toast.LENGTH_SHORT).show()
        }
    }
    private fun animatedHiding(view: View){
        view.animate().apply {
            scaleX(0f)
            scaleY(0f)

        }
    }
    private fun animatedAppearing(view: View){
        view.animate().apply {
            scaleX(1f)
            scaleY(1f)

        }
    }
    private fun fromDPToPixels(desiredDP: Float) : Float {
        val scale = resources.displayMetrics.density
        return (desiredDP * scale + 0.5f)
    }

    private fun animatedResizeToDesiredHeight(view: View, desiredHeight : Float){
        val originalHeight = view.height
        val multiplier = desiredHeight / originalHeight.toFloat()
        view.animate().apply {
            scaleY(multiplier)
        }
    }
}