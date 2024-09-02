package com.example.ordermenu.domain.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.firebase.Timestamp
import org.junit.jupiter.api.Test

class FormattersKtTest {
    @Test
    fun `calculate minutes ago for ten minutes ago works correctly`() {
        val pastTime = System.currentTimeMillis() - 10 * 60 * 1000
        val pastTimestamp = Timestamp(pastTime / 1000, 0)
        val result = getMinutesAgo(pastTimestamp)
        assertThat(result).isEqualTo("10 min ago")
    }
    @Test
    fun `calculate minutes ago for 1 hour 5 minutes ago works correctly`() {
        val pastTime = System.currentTimeMillis() - 65 * 60 * 1000
        val pastTimestamp = Timestamp(pastTime / 1000, 0)
        val result = getMinutesAgo(pastTimestamp)
        assertThat(result).isEqualTo("1 hr, 5 min ago")
    }
}