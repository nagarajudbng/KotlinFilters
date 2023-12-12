package com.hackerrank.starter.options

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import com.hackerrank.starter.EventListener
import com.hackerrank.starter.R


class FilterAlertDialog(context: Context?, listener: EventListener) :
    AlertDialog.Builder(context) {
    lateinit var nameET: EditText
    lateinit var minSalaryET: EditText
    lateinit var maxSalaryET: EditText
    lateinit var checkBox1: CheckBox
    lateinit var checkBox2: CheckBox
    lateinit var checkBox3: CheckBox
    lateinit var checkBox4: CheckBox
    lateinit var checkBox5: CheckBox
    lateinit var checkBox6: CheckBox
    lateinit var optionChooseListener: EventListener
    private var name = ""
    private var minSalary = 0f
    private var maxSalary = 0f
    private fun initView(listener: EventListener) {
        val builder = AlertDialog.Builder(context)
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_options, null)
        builder.setView(dialogView)
        optionChooseListener = listener

        nameET = dialogView.findViewById(R.id.name_ed)
        minSalaryET = dialogView.findViewById(R.id.min_salary_ed)
        maxSalaryET = dialogView.findViewById(R.id.max_salary_ed)
        checkBox1 = dialogView.findViewById(R.id.chb_1)
        checkBox2 = dialogView.findViewById(R.id.chb_2)
        checkBox3 = dialogView.findViewById(R.id.chb_3)
        checkBox4 = dialogView.findViewById(R.id.chb_4)
        checkBox5 = dialogView.findViewById(R.id.chb_5)
        checkBox6 = dialogView.findViewById(R.id.chb_6)

        checkBox1.isChecked = true
        updateText()
        builder.setPositiveButton(context.getText(android.R.string.yes)) { dialog, which -> }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { v: View? ->
            var filtersEnum = FiltersEnum.ALL
            if (checkBox1.isChecked) filtersEnum = FiltersEnum.ALL
            if (checkBox6.isChecked && !checkBox2.isChecked && !checkBox3.isChecked
            ) filtersEnum = FiltersEnum.SALARY_LESS_AND_MORE
            if (checkBox2.isChecked &&
                checkBox4.isChecked
            ) filtersEnum = FiltersEnum.NAME_SALARY_LESS
            if (checkBox2.isChecked &&
                checkBox5.isChecked
            ) filtersEnum = FiltersEnum.NAME_SALARY_MORE
            if (checkBox2.isChecked &&
                checkBox6.isChecked
            ) filtersEnum = FiltersEnum.NAME_SALARY_LESS_AND_MORE
            if (checkBox3.isChecked &&
                checkBox4.isChecked
            ) filtersEnum = FiltersEnum.PART_NAME_SALARY_LESS
            if (checkBox3.isChecked &&
                checkBox5.isChecked
            ) filtersEnum = FiltersEnum.PART_NAME_SALARY_MORE
            if (checkBox3.isChecked &&
                checkBox6.isChecked
            ) filtersEnum = FiltersEnum.PART_NAME_SALARY_LESS_AND_MORE
            filtersEnum.employeeName = name
            filtersEnum.minSalary = minSalary
            filtersEnum.maxSalary = maxSalary
            if ((checkBox2.isChecked || checkBox3.isChecked) && (checkBox4.isChecked || checkBox5.isChecked || checkBox6.isChecked) || checkBox1.isChecked) {
                optionChooseListener.chooseOption(filtersEnum)
                alertDialog.dismiss()
            }
        }
    }

    private fun initEditTextListeners() {
        nameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                name = if (s.toString().isEmpty()) {
                    ""
                } else {
                    s.toString()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        minSalaryET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                minSalary = if (s.toString().isEmpty()) {
                    0f
                } else {
                    s.toString().toFloat()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        maxSalaryET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                maxSalary = if (s.toString().isEmpty()) {
                    0f
                } else {
                    s.toString().toFloat()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun updateText() {
        updateName()
        updateMinSalary()
        updateMaxSalary()
    }

    private fun updateName() {
        checkBox2.text = context.getString(R.string.chb_2, name)
        checkBox3.text = context.getString(R.string.chb_3, name)
    }

    private fun updateMinSalary() {
        checkBox4.text = context.getString(R.string.chb_4, maxSalary.toString())
        checkBox6.text =
            context.getString(R.string.chb_6, minSalary.toString(), maxSalary.toString())
    }

    private fun updateMaxSalary() {
        checkBox5.text = context.getString(R.string.chb_5, minSalary.toString())
        checkBox6.text =
            context.getString(R.string.chb_6, minSalary.toString(), maxSalary.toString())
    }

    private fun initCheckBoxListeners() {
        checkBox1.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox2.isChecked = false
                checkBox3.isChecked = false
                checkBox4.isChecked = false
                checkBox5.isChecked = false
                checkBox6.isChecked = false
            }
        }
        checkBox2.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox3.isChecked = false
            }
        }
        checkBox3.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox2.isChecked = false
            }
        }
        checkBox4.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox5.isChecked = false
                checkBox6.isChecked = false
            }
        }
        checkBox5.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox4.isChecked = false
                checkBox6.isChecked = false
            }
        }
        checkBox6.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox4.isChecked = false
                checkBox5.isChecked = false
            }
        }
    }

    init {
        initView(listener)
        initEditTextListeners()
        initCheckBoxListeners()
    }
}