package io.uvera.eobrazovanje.constraint

import io.uvera.eobrazovanje.common.repository.StudentRepository
import org.springframework.stereotype.Component
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [StudentTranscriptNumberExistsValidator::class])
annotation class StudentTranscriptNumberExists(
    val message: String = "Student by this transcript number already exists",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = [],

)

@Component
class StudentTranscriptNumberExistsValidator(
    private val studentRepository: StudentRepository,
) : ConstraintValidator<StudentTranscriptNumberExists, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return studentRepository.findByTranscriptNumber(value ?: "") == null
    }
}
