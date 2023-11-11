package br.com.jarvis

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(value = "api.social.facebook")
data class FacebookProperties(
    @JsonProperty("secretAppKey")
    var secretAppKey: String = "",
    @JsonProperty("appId")
    var appId: String = "",
)