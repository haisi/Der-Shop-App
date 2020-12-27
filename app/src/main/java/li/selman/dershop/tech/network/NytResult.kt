package li.selman.dershop.tech.network

import com.squareup.moshi.Json

data class NytResult<Result>(
    @Json(name = "status") val status: String,
    @Json(name = "num_results") val numberOfResults: Int,
    @Json(name = "results") val results: List<Result>
)
