package com.hackerrank.starter

import androidx.test.core.app.ActivityScenario
import com.google.common.truth.Truth.assertThat
import com.hackerrank.starter.options.FiltersEnum
import com.hackerrank.starter.room.Employee
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    private val EMPLOYEE_ONE = Employee("Jhon Loyd", 112.4f)
    private val EMPLOYEE_TWO = Employee("Bibi Bob", 8852.43f)
    private val EMPLOYEE_THREE = Employee("Test_Test", 0f)
    private val EMPLOYEE_FOUR = Employee("Iki Jhon-Natan", 884f)
    private val EMPLOYEE_FIVE = Employee("Jhon Loyd", 225f)
    private val EMPLOYEE_SIX = Employee("Jhon Loyd", 350f)

    @Test
    fun getEmployeeEqualNameMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.NAME_SALARY_MORE
                    filtersEnum.employeeName = "Jhon Loyd"
                    filtersEnum.minSalary = 300f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 1 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_SIX)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeEqualNameLessThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.NAME_SALARY_LESS
                    filtersEnum.employeeName = "Jhon Loyd"
                    filtersEnum.maxSalary = 300f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 2 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FIVE)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeEqualNameLessThanAndMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.NAME_SALARY_LESS_AND_MORE
                    filtersEnum.employeeName = "Jhon Loyd"
                    filtersEnum.maxSalary = 300f
                    filtersEnum.minSalary = 200f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 1 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FIVE)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeContainsPartNameLessThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.PART_NAME_SALARY_LESS
                    filtersEnum.employeeName = "Jhon"
                    filtersEnum.maxSalary = 300f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 2 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FIVE)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeContainsPartNameMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.PART_NAME_SALARY_MORE
                    filtersEnum.employeeName = "Jhon"
                    filtersEnum.minSalary = 300f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 2 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FOUR) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_SIX)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeContainsPartNameLessThanAndMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    val filtersEnum = FiltersEnum.PART_NAME_SALARY_LESS_AND_MORE
                    filtersEnum.employeeName = "Jhon"
                    filtersEnum.minSalary = 300f
                    filtersEnum.maxSalary = 800f
                    activity.chooseOption(filtersEnum)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 1 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_SIX)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun getEmployeeSalaryLessThanAndMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    activity.getEmployeeSalaryLessThanAndMoreThan(100f, 300f)
                    val filtersEnum = FiltersEnum.SALARY_LESS_AND_MORE
                    filtersEnum.minSalary = 100f
                    filtersEnum.maxSalary = 300f
                    assertThat(
                        activity.filterEmployeeArrayList.size == 2 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FIVE)
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun sortMinToMax() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    activity.currentSort = SortEnum.MIN_TO_MAX
                    activity.sortSalaryMinToMax()
                    assertThat(
                        activity.filterEmployeeArrayList.size == 6 &&
                                activity.filterEmployeeArrayList[0] == EMPLOYEE_THREE &&
                                activity.filterEmployeeArrayList[1] == EMPLOYEE_ONE &&
                                activity.filterEmployeeArrayList[2] == EMPLOYEE_FIVE &&
                                activity.filterEmployeeArrayList[3] == EMPLOYEE_SIX &&
                                activity.filterEmployeeArrayList[4] == EMPLOYEE_FOUR &&
                                activity.filterEmployeeArrayList[5] == EMPLOYEE_TWO
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun sortMaxToMin() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.insertEmployee(EMPLOYEE_FIVE)
                    activity.insertEmployee(EMPLOYEE_SIX)
                    activity.currentSort = SortEnum.MAX_TO_MIN
                    activity.sortSalaryMaxToMin()
                    assertThat(
                        activity.filterEmployeeArrayList.size == 6 &&
                                activity.filterEmployeeArrayList[5] == EMPLOYEE_THREE &&
                                activity.filterEmployeeArrayList[4] == EMPLOYEE_ONE &&
                                activity.filterEmployeeArrayList[3] == EMPLOYEE_FIVE &&
                                activity.filterEmployeeArrayList[2] == EMPLOYEE_SIX &&
                                activity.filterEmployeeArrayList[1] == EMPLOYEE_FOUR &&
                                activity.filterEmployeeArrayList[0] == EMPLOYEE_TWO
                    ).isTrue()
                }
            }
        } catch (e: java.lang.Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

}