package com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: Images? = null,
    @SerialName("change_keys")
    val changeKeys: List<String> = emptyList(),
)

@Serializable
data class Images(
    @SerialName("poster_sizes")
    val posterSizes: List<String> = emptyList(),
    @SerialName("secure_base_url")
    val secureBaseUrl: String = "",
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String> = emptyList(),
    @SerialName("base_url")
    val baseUrl: String = "",
    @SerialName("logo_sizes")
    val logoSizes: List<String> = emptyList(),
    @SerialName("still_sizes")
    val stillSizes: List<String> = emptyList(),
    @SerialName("profile_sizes")
    val profileSizes: List<String> = emptyList(),
)

