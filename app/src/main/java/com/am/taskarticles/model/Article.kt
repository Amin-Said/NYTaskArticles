package com.am.taskarticles.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Article(
    val abstract: String?,
    @SerializedName("adx_keywords")
    val adxKeywords: String?,
    @SerializedName("asset_id")
    val assetId: Long?,
    val byline: String?,
    val column: String?,
    @SerializedName("des_facet")
    val desFacet: List<String>?,
    @SerializedName("eta_id")
    val etaID: Int?,
    @SerializedName("geo_facet")
    val geoFacet: List<String>?,
    val id: Long?,
    val media: List<Media>?,
    @SerializedName("nytdsection")
    val nytdSection: String?,
    @SerializedName("org_facet")
    val orgFacet: List<String>?,
    @SerializedName("per_facet:")
    val perFacet: List<String>?,
    @SerializedName("published_date")
    val publishedDate: String?,
    val section: String?,
    val source: String?,
    val subsection: String?,
    val title: String?,
    val type: String?,
    val updated: String?,
    val uri: String?,
    val url: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createStringArrayList(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.createTypedArrayList(Media),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(abstract)
        parcel.writeString(adxKeywords)
        parcel.writeValue(assetId)
        parcel.writeString(byline)
        parcel.writeString(column)
        parcel.writeStringList(desFacet)
        parcel.writeValue(etaID)
        parcel.writeStringList(geoFacet)
        parcel.writeValue(id)
        parcel.writeTypedList(media)
        parcel.writeString(nytdSection)
        parcel.writeStringList(orgFacet)
        parcel.writeStringList(perFacet)
        parcel.writeString(publishedDate)
        parcel.writeString(section)
        parcel.writeString(source)
        parcel.writeString(subsection)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(updated)
        parcel.writeString(uri)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}