package ru.justd.fundaassignment.model


import com.google.gson.annotations.SerializedName

/**
 * Created by defuera on 01/02/2017.
 */
data class ApiResponse<out T>(
        @SerializedName("Paging") val pagingInfo: PagingInfo,
        @SerializedName("TotaalAantalObjecten") val objectsCount: Int,
        @SerializedName("Objects") val objects: List<T>

) {

    data class PagingInfo(
            @SerializedName("AantalPaginas") val lastPage: Int,
            @SerializedName("HuidigePagina") val currentPage: Int,
            @SerializedName("VolgendeUrl") val nextUrl: String,
            @SerializedName("VorigeUrl") val prevType: String
    )
}