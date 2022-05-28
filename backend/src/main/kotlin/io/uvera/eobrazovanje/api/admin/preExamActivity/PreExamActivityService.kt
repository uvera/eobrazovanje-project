package io.uvera.eobrazovanje.api.admin.preExamActivity

import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityCreateDTO
import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityResultCreateDTO
import io.uvera.eobrazovanje.api.admin.preExamActivity.dto.PreExamActivityViewDTO
import io.uvera.eobrazovanje.common.repository.*
import io.uvera.eobrazovanje.util.extensions.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PreExamActivityService(
    protected val repo: PreExamActivityRepository,
    protected val resultRepo: PreExamActivityResultRepository,
    protected val studentRepo: StudentRepository
    ) {
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

    @Transactional
    fun createResults(preExamActivities: List<PreExamActivityResultCreateDTO>) = resultRepo {
        preExamActivities.forEach {
            PreExamActivityResult(
                score = it.score,
                preExamActivity = repo { findByIdOrNull(it.preExamActivityId) ?: notFoundById<PreExamActivity>(it.preExamActivityId) },
                student = studentRepo { findByIdOrNull(it.studentId) ?: notFoundById<Student>(it.studentId) }
            ).save()
        }
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

    fun getStudentPreExamActivities(studentId: UUID, subjectExId: UUID): Any {
        val preExamActivities = repo.findAllPreExamActivitiesBySubject(subjectExId)
        val results = resultRepo.findAllByStudent(studentId, preExamActivities.map { it.id })
        return results.ifEmpty {
            preExamActivities
        }
    }

    fun getAllPreExamActivities(): Any = repo {
        return@repo findAllPreExamActivities()
    }
}
