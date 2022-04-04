package com.vylo.common.widget

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import com.vylo.common.R
import com.vylo.common.adapter.ListTypeAdapter

class MainInputType(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context!!, attrs, defStyleAttr), ListTypeAdapter.ListTypeAdapterCallback {

    interface DoActionByViewTag {
        fun doActionByViewTag(viewTag: Int)
    }

    interface DoAction {
        fun doAction()
    }

    private var doActionByViewTag: DoActionByViewTag? = null
    private var doAction: DoAction? = null
    private val inputContainer: ConstraintLayout
    private val fullContainer: ConstraintLayout
    private val dropList: RecyclerView
    private val actionIconIntoInput: ImageView
    private val inputRequired: ImageView
    private val actionIcon: ImageView
    private val inputType: EditText
    private val inputTypeInfo: TextView
    private val inputTitle: TextView
    private val errorTitle: TextView
    private val listTypeAdapter: ListTypeAdapter
    private var data = listOf<String>()
    private var isChangeIcon = false
    private var isShowList = false
    private var isDoAction = false
    private var toggleIconNoAction: Int = 0
    private var toggleIconAction: Int = 0
    private var viewTag: Int = 0

    fun setDoingAction(isDoAction: Boolean) {
        this.isDoAction = isDoAction
    }

    fun doActionByViewTag(action: DoActionByViewTag) {
        doActionByViewTag = action
    }

    fun doAction(action: DoAction) {
        doAction = action
    }

    fun setTag(tag: Int) {
        viewTag = tag
    }

    fun disableInput() {
        inputType.isEnabled = false
    }

    fun getInputText(): String = inputType.text.toString()

    fun setInputText(value: String?) {
        inputType.setText(value)
    }

    fun clearInputText() {
        inputType.setText("")
    }

    fun hideInputContainer(isVisible: Int) {
        fullContainer.visibility = isVisible
    }

    fun setContainerColor(color: Int) {
        val background = inputContainer.background as GradientDrawable
        background.setColor(color)
    }

    fun setToggleActionIcons(toggleIconNoAction: Int, toggleIconAction: Int) {
        setIcon(toggleIconAction)
        this.toggleIconNoAction = toggleIconNoAction
        this.toggleIconAction = toggleIconAction
    }

    fun toggleActionButton() {
        when (isChangeIcon) {
            true -> {
                isChangeIcon = false
                actionIcon.setImageDrawable(ContextCompat.getDrawable(context, toggleIconAction))
                setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
            false -> {
                isChangeIcon = true
                actionIcon.setImageDrawable(ContextCompat.getDrawable(context, toggleIconNoAction))
                setInputType(InputType.TYPE_CLASS_TEXT)
            }
        }
    }

    fun setIcon(imageAction: Int) {
        actionIcon.setImageDrawable(ContextCompat.getDrawable(context, imageAction))
    }

    fun setIconIntoInput(imageAction: Int) {
        actionIconIntoInput.setImageDrawable(ContextCompat.getDrawable(context, imageAction))
    }

    fun clickOnIcon(clickListener: OnClickListener) {
        actionIcon.setOnClickListener(clickListener)
    }

    fun clickOnIconIntoInput(clickListener: OnClickListener) {
        actionIconIntoInput.setOnClickListener(clickListener)
    }

    fun setInputTypeTextColor(color: Int) {
        inputType.setTextColor(color)
    }

    fun setInputType(type: Int) {
        inputType.inputType = type
    }

    fun setInputTypeHint(hintText: String) {
        inputType.hint = hintText
    }

    fun setInputTypeHintColor(color: Int) {
        inputType.setHintTextColor(color)
    }

    fun setInputTypeInfo(info: String) {
        inputTypeInfo.text = info
    }

    fun setInputTypeInfoColor(color: Int) {
        inputTypeInfo.setTextColor(color)
    }

    fun setInputRequiredIcon(imageInfo: Int) {
        inputRequired.setImageDrawable(ContextCompat.getDrawable(context, imageInfo))
    }

    fun setInputTitle(title: String) {
        inputTitle.text = title
    }

    fun setInputTitleColor(color: Int) {
        inputTitle.setTextColor(color)
    }

    fun hideInputTitle(isVisible: Int) {
        inputTitle.visibility = isVisible
    }

    fun setErrorTitle(title: String?) {
        errorTitle.text = title
    }

    fun setErrorTitleColor(color: Int) {
        errorTitle.setTextColor(color)
    }

    fun setListTypeData(listTypeData: List<String>) {
        data = listTypeData
    }

    fun iconToggleData(listTypeData: List<String>) {
        if (!isShowList) {
            isShowList = true
            val dataList = mutableListOf<String>()
            dataList.addAll(listTypeData)
            this.listTypeAdapter.addData(dataList)
        } else {
            isShowList = false
            this.listTypeAdapter.clearData()
        }
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<String>()
        if (text.length > 1) {
            for (item in data)
                if (item.lowercase().contains(text.lowercase())) {
                    isShowList = false
                    filteredList.add(item)
                }
        }
        iconToggleData(filteredList)
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        inflate(context, R.layout.main_input_type, this)
        actionIconIntoInput = findViewById(R.id.action_icon_into_input)
        inputContainer = findViewById(R.id.inout_container)
        fullContainer = findViewById(R.id.full_container)
        inputTypeInfo = findViewById(R.id.inout_type_info)
        inputRequired = findViewById(R.id.input_required)
        actionIcon = findViewById(R.id.action_icon)
        inputTitle = findViewById(R.id.input_title)
        errorTitle = findViewById(R.id.error_title)
        inputType = findViewById(R.id.input_type)
        dropList = findViewById(R.id.drop_list)
        listTypeAdapter = ListTypeAdapter(this)
        dropList.layoutManager = LinearLayoutManager(context)
        dropList.adapter = listTypeAdapter

        inputType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotBlank()) setErrorTitle("")
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun setDefaultItem(item: String?) {
        clickOnItem(item, false)
    }

    override fun clickOnItem(item: String?, isChooseItem: Boolean) {
        setInputText(item)
        isShowList = false
        if (doActionByViewTag != null && isChooseItem) doActionByViewTag!!.doActionByViewTag(viewTag)
        if (doAction != null && isChooseItem && isDoAction) {
            doAction!!.doAction()
            isShowList = false
        }
        this.listTypeAdapter.clearData()
    }

    fun addTextChangedListener(changedEditText: MainInputType, function: (firstView: MainInputType, secondView: MainInputType) -> String, firstView: MainInputType, secondView: MainInputType) {
        inputType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changedEditText.setInputText(function(firstView, secondView))
            }
        })
    }

}