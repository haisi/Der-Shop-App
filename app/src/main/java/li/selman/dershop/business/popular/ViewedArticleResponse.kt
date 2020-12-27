package li.selman.dershop.business.popular

import com.squareup.moshi.Json

data class ViewedArticleResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "url") val url: String,
    @Json(name = "title") val title: String,
    @Json(name = "media") val media: List<Media>
) {

    data class Media(
        @Json(name = "caption") val caption: String,
        @Json(name = "type") val type: String, // Image
        @Json(name = "media-metadata") val metadata: List<MediaMetaData>
    ) {

        data class MediaMetaData(
            @Json(name = "url") val url: String,
            @Json(name = "format") val format: String, // Standard Thumbnail; mediumThreeByTwo210; mediumThreeByTwo440
            @Json(name = "height") val height: Int,
            @Json(name = "width") val width: Int
        )
    }
}
