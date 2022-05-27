package io.uvera.eobrazovanje.api.admin.preExamActivity

import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityCreateDTO
import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityViewDTO
import io.uvera.eobrazovanje.common.repository.PreExamActivity
import io.uvera.eobrazovanje.common.repository.PreExamActivityRepository
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.notFoundById
import io.uvera.eobrazovanje.util.extensions.save
import io.uvera.eobrazovanje.util.extensions.update
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PreExamActivityService(protected val repo: PreExamActivityRepository) {
    @Transactional(propagation = Propagation.NEVER)
    fun createPreExamActivity(acDTO: PreExamActivityCreateDTO): PreExamActivityViewDTO = repo {
        getPreExamActivity(preExamDTOToEntity(acDTO).save().id)
    }

    @Transactional(propagation = Propagation.NEVER)
    fun updatePreExamActivity(id: UUID, dto: PreExamActivityCreateDTO): PreExamActivityViewDTO = repo {
        val dbObj = findByIdOrNull(id) ?: notFoundById<PreExamActivity>(id)
        dbObj.update {
            name = dto.name
            points = dto.points
        }
        return@repo getPreExamActivity(id)
    }

    fun deletePreExamActivity(id: UUID) = repo {
        if (!existsById(id)) notFoundById<PreExamActivity>(id)
        return@repo deleteById(id)
    }

    fun getPreExamActivity(preExamID: UUID): PreExamActivityViewDTO =
        repo.findByIdAsDto(preExamID) ?: notFoundById<PreExamActivity>(preExamID)

    fun preExamDTOToEntity(dto: PreExamActivityCreateDTO): PreExamActivity {
        return PreExamActivity(
            name = dto.name,
            points = dto.points
        )
    }

    fun getAllPreExamActivitiesPaged(page: Int, records: Int): Any = repo {
        val req = PageRequest.of(page - 1, records)
        return@repo findAllAsDto(req)
    }

    fun getAllPreExamActivities(): Any = repo {
        return@repo findAllPreExamActivities()
    }
}
