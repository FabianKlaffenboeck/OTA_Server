package at.fklab.ota_server.development

import at.fklab.ota_server.models.FirmwareRelease
import at.fklab.ota_server.models.FirmwareReleaseInput
import at.fklab.ota_server.models.User

val sampleFirmwareReleases = listOf<FirmwareReleaseInput>(
    FirmwareReleaseInput(id = 1, releaseTrain = sampleReleaseTrains[0], info = ""),
    FirmwareReleaseInput(id = 2, releaseTrain = sampleReleaseTrains[0], info = ""),
    FirmwareReleaseInput(id = 3, releaseTrain = sampleReleaseTrains[1], info = ""),
    FirmwareReleaseInput(id = 4, releaseTrain = sampleReleaseTrains[1], info = ""),
    FirmwareReleaseInput(id = 5, releaseTrain = sampleReleaseTrains[2], info = "")
)