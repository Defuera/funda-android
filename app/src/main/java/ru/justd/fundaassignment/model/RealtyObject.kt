package ru.justd.fundaassignment.model

import com.google.gson.annotations.SerializedName

/**
 * Created by defuera on 01/02/2017.
 */
data class RealtyObject(
        @SerializedName("MakelaarId") val agentId: Int,
        @SerializedName("MakelaarNaam") val agentName: String
)