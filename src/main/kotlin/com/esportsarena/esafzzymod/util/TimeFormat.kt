package com.esportsarena.esafzzymod.util

object TimeFormat {

    fun timerFormat(totalMillis: Long, roundMillis: Boolean): String {
        val millis = totalMillis % 1000
        val seconds = (totalMillis / 1000) % 60
        val minutes = (totalMillis / (1000 * 60)) % 60
        val hours = (totalMillis / (1000 * 60 * 60)) % 24

        return if (hours > 0) {
            String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, if (roundMillis) millis / 100 else millis / 10)
        } else {
            String.format("%02d:%02d.%d", minutes, seconds, if (roundMillis) millis / 100 else millis / 10)
        }
    }

}