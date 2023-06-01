package com.testinopenapp.data.model.dashboard

import java.time.LocalDate

data class Data(
    val overall_url_chart: Map<String, Int>,
    val recent_links: List<TopLink>,
    val top_links: List<TopLink>
)