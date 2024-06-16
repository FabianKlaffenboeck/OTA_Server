package at.fklab.ota_server.development

import at.fklab.ota_server.models.Device

val sampleDevices = listOf<Device>(
    Device(id = 1, accessToken = sampleAccessTokens[2], info = ""),
    Device(id = 2, accessToken = sampleAccessTokens[3], info = ""),
)