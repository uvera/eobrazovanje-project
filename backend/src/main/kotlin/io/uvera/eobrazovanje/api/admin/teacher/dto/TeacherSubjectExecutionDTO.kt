package io.uvera.eobrazovanje.api.admin.teacher.dto

import org.springframework.validation.annotation.Validated
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class TeacherSubjectExecutionDTO (
    @field:NotNull
    var subjectExecutionIds: List<UUID>,

    @field:NotBlank
    var year: Int,

    @field:NotBlank
    var teacherId: UUID
)