package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ReleaseTrainService {

    fun getAll(): List<ReleaseTrain> = transaction {
        val query = Op.build { ReleaseTrains.deletedAt.isNull() }
        ReleaseTrainEntity.find(query).map(ReleaseTrainEntity::toReleaseTrain)
    }

    fun getById(id: Int): ReleaseTrain? = transaction {
        ReleaseTrainEntity.find {
            ReleaseTrains.id eq id
        }.firstOrNull()?.toReleaseTrain()
    }

    fun add(releaseTrain: ReleaseTrainInput): ReleaseTrain = transaction {
        ReleaseTrainEntity.new {

            info = releaseTrain.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toReleaseTrain()
    }

    fun update(releaseTrain: ReleaseTrainInput): ReleaseTrain = transaction {
        val notNullId = releaseTrain.id!!

        ReleaseTrainEntity[notNullId].info = releaseTrain.info

        ReleaseTrainEntity[notNullId].updatedAt = LocalDateTime.now()
        ReleaseTrainEntity[notNullId].updatedBy = ""
        ReleaseTrainEntity[notNullId].toReleaseTrain()
    }

    fun delete(id: Int) = transaction {
        ReleaseTrainEntity[id].deletedAt = LocalDateTime.now()
    }
}