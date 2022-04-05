package io.uvera.eobrazovanje.common.service

import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class DigitGenerationService {

    companion object {
        val values = arrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'
        )
    }

    fun getRandomPassword(length: Int): String = SecureRandom().run {
        (1..length).map {
            values.getOrNull(nextInt(values.size)) ?: "x"
        }.joinToString("")
    }
}
