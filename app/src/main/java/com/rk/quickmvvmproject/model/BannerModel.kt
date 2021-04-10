package com.rk.quickmvvmproject.model

/**
 *
 *@Author 王--
 *创建时间 2021/4/10
 */
data class BannerModel(
    val `data`: List<BannerData>,
    val errorCode: Int,
    val errorMsg: String
)

data class BannerData(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)