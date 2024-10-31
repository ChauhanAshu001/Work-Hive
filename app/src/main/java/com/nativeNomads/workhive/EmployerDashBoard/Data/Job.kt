package com.nativeNomads.workhive.EmployerDashBoard.Data

data class Job(
    var jobId: String = "",
    val title: String = "",
    val role: String = "",
    val location: String = "",
    val companyName: String = "",
    val status: String = "",
    val userId: String = "",
    val salary: String = "",
    val experienceNeeded: String = "",
    val description: String = "",
    val jobType: String = "",
    val qualifications: String = "",
    val responsibilities: String = "",
    val benefits: String = "",
    val applicationDeadline: String = ""
)
