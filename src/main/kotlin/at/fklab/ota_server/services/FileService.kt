package at.fklab.ota_server.services

import at.fklab.ota_server.models.FirmwareRelease
import java.io.File
import java.io.InputStream

class FileService(val pathToFile: String) {


    private fun generateFileName(firmwareRelease: FirmwareRelease): String {
        return "${firmwareRelease.version}${firmwareRelease.buildHash}${firmwareRelease.pipeLineId}${firmwareRelease.commitHash}"
    }

    fun get(firmwareRelease: FirmwareRelease): File? {
        val file = File(pathToFile, generateFileName(firmwareRelease))
        if (!file.exists()) return null
        return file
    }

    fun save(fileData: InputStream, firmwareRelease: FirmwareRelease) {
        val file = File(pathToFile, generateFileName(firmwareRelease))
        file.writeBytes(fileData.readBytes())
    }
}