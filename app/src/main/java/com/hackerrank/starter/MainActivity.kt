/*
 ******** Guidelines ********

 Click: Run > Build & Run
 Refer to README.md for additional information
 */
package com.hackerrank.starter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.hackerrank.starter.adapter.EmployeeListAdapter
import com.hackerrank.starter.databinding.ActivityMainBinding
import com.hackerrank.starter.options.FilterAlertDialog
import com.hackerrank.starter.options.FiltersEnum
import com.hackerrank.starter.room.Employee
import java.util.*

/*
******** Guidelines ********

Click: Run > Build & Run
Refer to README.md for additional information
*/

class MainActivity : AppCompatActivity(), EventListener {
    private var binding: ActivityMainBinding? = null
    lateinit var adapter: EmployeeListAdapter
    lateinit var filterAlertDialog: FilterAlertDialog
    lateinit var context: Context
    lateinit var eventListener: EventListener
    var fullEmployeeArrayList: MutableList<Employee> = mutableListOf()
    var filterEmployeeArrayList: MutableList<Employee> = mutableListOf()
    var currentSort = SortEnum.MAX_TO_MIN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.toolbar)
        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        context = this
        eventListener = this
        adapter = EmployeeListAdapter(mutableListOf(), eventListener)
        binding?.employeeRv?.adapter = adapter
        binding?.addEmployeeBtn?.setOnClickListener(View.OnClickListener {
            checkExistEmployee(
                binding?.employeeNameEt?.text.toString(),
                binding?.employeeSalaryEt?.text.toString()
            )
            binding?.employeeNameEt?.setText("")
            binding?.employeeSalaryEt?.setText("")
            hideKeyboard()
        })
        binding?.clearData?.setOnClickListener { deleteAll() }
        binding?.options?.setOnClickListener {
            filterAlertDialog = FilterAlertDialog(context, eventListener)
            filterAlertDialog.show()
        }
        initData()
    }

    fun deleteAll() {
        fullEmployeeArrayList.clear()
        filterEmployeeArrayList = fullEmployeeArrayList;
        updateScreen(filterEmployeeArrayList)
    }

    fun initData() {
        binding?.addEmployeeBtn?.isEnabled = false
        binding?.clearData?.isEnabled = false
        val newEmployeeOne: Employee = createNewEmployee("Jhon Loyd", 112.4f)
        insertEmployee(newEmployeeOne)
        val newEmployeeTwo: Employee = createNewEmployee("Bibi Bob", 8852.43f)
        insertEmployee(newEmployeeTwo)
        binding?.addEmployeeBtn?.isEnabled = true
        binding?.clearData?.isEnabled = true
        binding?.filterTv?.text = resources.getString(R.string.filter_all)
    }

    fun insertEmployee(employee: Employee) {
        binding?.addEmployeeBtn?.isEnabled = false
        fullEmployeeArrayList.add(employee)
        filterEmployeeArrayList = fullEmployeeArrayList
        when (currentSort) {
            SortEnum.MIN_TO_MAX -> sortSalaryMinToMax()
            SortEnum.MAX_TO_MIN -> sortSalaryMaxToMin()
        }
    }

    private fun checkExistEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        if (fullEmployeeArrayList.none { employee -> employee.name == employeeName && employee.salary == employeeSalary }) {
            addEmployee(name, salary)
        } else {
            createExistEmployeeDialog()
        }
    }

    private fun addEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        val newEmployee: Employee = createNewEmployee(employeeName, employeeSalary)
        insertEmployee(newEmployee)
    }

    fun updateScreen(employeeList: List<Employee>) {
        adapter.updateEmployeeListItems(employeeList)
        adapter.notifyDataSetChanged()
        binding?.addEmployeeBtn?.isEnabled = true
    }

    fun getAll() {
        filterEmployeeArrayList = fullEmployeeArrayList
        updateScreen(filterEmployeeArrayList)
    }

    fun deleteEmployeeById(id: Int) {
        fullEmployeeArrayList.removeAt(id)
        filterEmployeeArrayList = fullEmployeeArrayList
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeSalaryLessThanAndMoreThan(minSalary: Float, maxSalary: Float) {

        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.salary>=minSalary && worker.salary<=maxSalary
        } as MutableList<Employee>
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeEqualNameLessThan(name: String, maxSalary: Float) {
        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.equals(name)&& worker.salary<=maxSalary
        } as MutableList<Employee>
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeEqualNameMoreThan(name: String, minSalary: Float) {

        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.equals(name)&& worker.salary>=minSalary
        } as MutableList<Employee>
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeEqualNameLessThanAndMoreThan(name: String, minSalary: Float, maxSalary: Float) {
        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.equals(name)&& worker.salary>=minSalary&& worker.salary<=maxSalary
        } as MutableList<Employee>
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeContainsPartNameLessThan(partName: String, maxSalary: Float) {

        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.contains(partName)&&  worker.salary<=maxSalary
        } as MutableList<Employee>
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeContainsPartNameMoreThan(partName: String, minSalary: Float) {

        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.contains(partName)&&  worker.salary>=minSalary
        } as MutableList<Employee>

        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeContainsPartNameLessThanAndMoreThan(
        partName: String,
        minSalary: Float,
        maxSalary: Float
    ) {

        filterEmployeeArrayList = ArrayList()
        filterEmployeeArrayList  = fullEmployeeArrayList.filter { worker->
            worker.name.contains(partName)&&  worker.salary>=minSalary && worker.salary<=maxSalary
        } as MutableList<Employee>

        updateScreen(filterEmployeeArrayList)
    }

    private fun getSalary(salary: String): Float {
        return if (salary.isEmpty()) {
            0f
        } else {
            salary.toFloat()
        }
    }

    private fun getName(name: String): String {
        return if (name.isEmpty()) {
            "Unnamed Employee"
        } else {
            name
        }
    }

    private fun createNewEmployee(name: String, salary: Float): Employee {
        return Employee(name, salary)
    }

    override fun deleteEmployee(id: Int) {
        deleteEmployeeById(id)
    }

    override fun chooseOption(filtersEnum: FiltersEnum?) {
        when (filtersEnum) {
            FiltersEnum.ALL -> {
                binding?.filterTv?.text = resources.getString(R.string.filter_all)
                getAll()
            }
            FiltersEnum.NAME_SALARY_MORE -> {
                val name = filtersEnum.employeeName
                val minSalary = filtersEnum.minSalary
                binding?.filterTv?.text =
                    resources.getString(R.string.filter_name_more_than, name, minSalary)
                getEmployeeEqualNameMoreThan(name, minSalary)
            }
            FiltersEnum.NAME_SALARY_LESS -> {
                val name = filtersEnum.employeeName
                val maxSalary = filtersEnum.maxSalary
                binding?.filterTv?.text =
                    resources.getString(R.string.filter_name_less_than, name, maxSalary)
                getEmployeeEqualNameLessThan(name, maxSalary)
            }
            FiltersEnum.NAME_SALARY_LESS_AND_MORE -> {
                val name = filtersEnum.employeeName
                val minSalary = filtersEnum.minSalary
                val maxSalary = filtersEnum.maxSalary
                binding?.filterTv?.text =
                    resources.getString(
                        R.string.filter_name_less_than_and_more_than,
                        name,
                        minSalary,
                        maxSalary
                    )
                getEmployeeEqualNameLessThanAndMoreThan(name,minSalary,maxSalary)
            }
            FiltersEnum.PART_NAME_SALARY_LESS -> {
                val partName = filtersEnum.employeeName
                val maxSalary = filtersEnum.maxSalary
                binding?.filterTv?.text =
                    resources.getString(R.string.filter_part_name_less_than, partName, maxSalary)
                getEmployeeContainsPartNameLessThan(partName,maxSalary)
            }
            FiltersEnum.PART_NAME_SALARY_MORE -> {
                val partName = filtersEnum.employeeName
                val minSalary = filtersEnum.minSalary
                binding?.filterTv?.text =
                    resources.getString(R.string.filter_part_name_more_than, partName, minSalary)
                getEmployeeContainsPartNameMoreThan(partName,minSalary)
            }
            FiltersEnum.PART_NAME_SALARY_LESS_AND_MORE -> {
                val partName = filtersEnum.employeeName
                val minSalary = filtersEnum.minSalary
                val maxSalary = filtersEnum.maxSalary
                binding?.filterTv?.text =
                    resources.getString(
                        R.string.filter_part_name_less_than_and_more_than,
                        partName,
                        minSalary,
                        maxSalary
                    )
                getEmployeeContainsPartNameLessThanAndMoreThan(partName,minSalary,maxSalary)
            }

            else -> {}
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun createExistEmployeeDialog() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.exist_dialog_title))
            .setMessage(resources.getString(R.string.dialog_message))
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which -> dialog.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        changeSortType(menuItem)
        return super.onOptionsItemSelected(menuItem)
    }

    private fun changeSortType(menuItem: MenuItem) {
        when (currentSort) {
            SortEnum.MIN_TO_MAX -> {
                currentSort = SortEnum.MAX_TO_MIN
                menuItem.setIcon(currentSort.resId)
                sortSalaryMaxToMin()
            }
            SortEnum.MAX_TO_MIN -> {
                currentSort = SortEnum.MIN_TO_MAX
                menuItem.setIcon(currentSort.resId)
                sortSalaryMinToMax()
            }
        }
    }

    fun sortSalaryMinToMax() {
        //Write code and add filters here
        filterEmployeeArrayList = ArrayList()
        updateScreen(filterEmployeeArrayList)
    }

    fun sortSalaryMaxToMin() {
        //Write code and add filters here
        filterEmployeeArrayList = ArrayList()
        updateScreen(filterEmployeeArrayList)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}