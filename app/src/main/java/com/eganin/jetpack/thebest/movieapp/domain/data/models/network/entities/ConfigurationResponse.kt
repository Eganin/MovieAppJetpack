package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: Images? = null,
    @SerialName("change_keys")
    val changeKeys: List<String>? = null,
)

@Serializable
data class Images(
    @SerialName("poster_sizes")
    val posterSizes: List<String>? = null,
    @SerialName("secure_base_url")
    val secureBaseUrl: String? = null,
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String>? = null,
    @SerialName("base_url")
    val baseUrl: String? = null,
    @SerialName("logo_sizes")
    val logoSizes: List<String>? = null,
    @SerialName("still_sizes")
    val stillSizes: List<String>? = null,
    @SerialName("profile_sizes")
    val profileSizes: List<String>? = null,
)

