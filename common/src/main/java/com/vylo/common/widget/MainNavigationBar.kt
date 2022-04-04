package com.vylo.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.vylo.common.R

class MainNavigationBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context!!, attrs, defStyleAttr) {

    private val bottomBorder: View
    private val title: TextView
    private val buttonNext: TextView
    private val buttonBack: ImageView

    fun showBottomBorder(isVisible: Int) { bottomBorder.visibility = isVisible }
    fun showButtonNext(isVisible: Int) { buttonNext.visibility = isVisible }
    fun showButtonBack(isVisible: Int) { buttonBack.visibility = isVisible }

    fun setButtonNextText(name: String) { buttonNext.text = name }
    fun setTitle(titleName: String) { title.text = titleName }

    fun setColorButtonNext(color: Int) { buttonNext.setTextColor(color) }
    fun setColorBottomBorder(color: Int) { bottomBorder.setBackgroundColor(color) }

    fun clickOnButtonNext(clickListener: OnClickListener?) { buttonNext.setOnClickListener(clickListener) }
    fun clickOnButtonBack(clickListener: OnClickListener?) { buttonBack.setOnClickListener(clickListener) }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        inflate(context, R.layout.main_navigation_bar, this)
        bottomBorder = findViewById(R.id.bottom_border)
        buttonNext = findViewById(R.id.button_next)
        buttonBack = findViewById(R.id.button_back)
        title = findViewById(R.id.screen_title)
        buttonBack.visibility = INVISIBLE
        bottomBorder.visibility = GONE
        buttonNext.visibility = GONE
    }
}