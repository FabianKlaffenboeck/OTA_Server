package at.fklab.ota_server.development

import at.fklab.ota_server.models.FirmwareRelease
import at.fklab.ota_server.models.FirmwareReleaseInput
import at.fklab.ota_server.models.User

val sampleFirmwareReleases = listOf<FirmwareReleaseInput>(
    FirmwareReleaseInput(
        id = 1,
        version = "v0.0.1",
        buildHash = "f3ceee9e-7664-4dad-acad-1df5a2ebc126",
        pipeLineId = "883329",
        commitHash = "01bfa801dd5e41f281138f9e10823ff34a36ff84",
        info = "",
        releaseTrain = sampleReleaseTrains[0]
    ), FirmwareReleaseInput(
        id = 2,
        version = "v0.0.2",
        buildHash = "a38c0bf2-42c5-4c41-8bd3-739d1025fec5",
        pipeLineId = "8834456",
        commitHash = "6377e2f295dbcadfef401134bb9fdb3cdb725446",
        info = null,
        releaseTrain = sampleReleaseTrains[0]
    )
)