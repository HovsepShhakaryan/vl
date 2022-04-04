package com.lessplatform.common.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.vylo.common.R


class MainButton(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context!!, attrs, defStyleAttr) {

    private val mainLayout: ConstraintLayout
    private val icon: ImageView
    private val title: TextView
    private var buttonColor: Int = R.color.primary

    fun setTitle(name: String) { title.text = name }

    fun setTitleColor(color: Int) { title.setTextColor(color)}

    fun setIcon(image: Int) { icon.setImageDrawable(ContextCompat.getDrawable(context, image)) }

    fun showIcon(isShow: Int) { icon.visibility = isShow }

    fun setLayoutWidth(width: Int) { mainLayout.layoutParams = LayoutParams(width, LayoutParams.WRAP_CONTENT) }

    fun setButtonColor(color: Int) {
        buttonColor = color
        val background = mainLayout.background as GradientDrawable
        background.setColor(color)
    }

    fun clickOnButton(clickListener: OnClickListener) {
        mainLayout.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(buttonColor, BlendModeCompat.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
        mainLayout.setOnClickListener(clickListener)
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        inflate(context, R.layout.main_button, this)
        mainLayout = findViewById(R.id.main_layout)
        title = findViewById(R.id.title)
        icon = findViewById(R.id.icon)
        icon.visibility = GONE
    }
}
