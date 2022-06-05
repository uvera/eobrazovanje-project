package io.uvera.eobrazovanje.api.shared.announcement.dto

import org.springframework.validation.annotation.Validated
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
class AnnouncementCreateDTO (
    @field:NotBlank
    var postText: String,

    @field:NotNull
    val subjectExecutionId: UUID
)