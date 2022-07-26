package com.eganin.jetpack.thebest.movieapp.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: Images,
    @SerialName("change_keys")
    val changeKeys: List<String>
)
@Serializable
data class Images(
    @SerialName("poster_sizes")
    val posterSizes: List<String>,
    @SerialName("secure_base_url")
    val secureBaseUrl: String,
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerialName("base_url")
    val baseUrl: String,
    @SerialName("logo_sizes")
    val logoSizes: List<String>,
    @SerialName("still_sizes")
    val stillSizes: List<String>,
    @SerialName("profile_sizes")
    val profileSizes: List<String>
)

