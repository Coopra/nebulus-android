package com.coopra.data

data class DashboardActivityEnvelope(
        val collection: Collection<DashboardActivity>,
        val next_href: String,
        val future_href: String
)