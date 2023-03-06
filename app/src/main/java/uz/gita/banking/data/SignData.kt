package uz.gita.banking.data

import uz.gita.banking.data.`var`.SignType
import java.io.Serializable

data class SignData(
    val signType: SignType,
    val token: String,
    val phone: String
): Serializable
