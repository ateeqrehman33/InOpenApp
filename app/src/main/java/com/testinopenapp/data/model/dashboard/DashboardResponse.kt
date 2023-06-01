package com.testinopenapp.data.model.dashboard

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DashboardResponse(
    val applied_campaign: Int, // 0
    val `data`: Data,
    val extra_income: Double, // 42.42
    val links_created_today: Int, // 0
    val message: String, // success
    val startTime: String,
    val status: Boolean, // true
    val statusCode: Int, // 200
    val support_whatsapp_number: String, // 8297368106
    val today_clicks: Int, // 0
    val top_location: String,
    val top_source: String,
    val total_clicks: Int, // 808
    val total_links: Int // 178
)