package com.hackerrank.starter.options

enum class FiltersEnum {
    ALL, SALARY_LESS_AND_MORE,
    NAME_SALARY_LESS,
    NAME_SALARY_MORE,
    NAME_SALARY_LESS_AND_MORE,
    PART_NAME_SALARY_LESS,
    PART_NAME_SALARY_MORE,
    PART_NAME_SALARY_LESS_AND_MORE;

    var employeeName: String = ""
    var minSalary: Float = 0f
    var maxSalary: Float = 0f
}