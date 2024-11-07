package com.nativeNomads.workhive.EmployerDashBoard.Data

data class Application(
    val applicationId: String = "",
    val name: String = "",
    val status: String = "Applied",
    val userId: String = "",
    val qualification: String = "",
    val skills: String = "",
    val experience: String = "",
    val companyName: String = ""
)
