package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "student")
class Student(
    @Column(name = "transcript_number", nullable = false, unique = true)
    var transcriptNumber: String,

    @Column(name = "identification_number", nullable = false, unique = true)
    var identificationNumber: String,

    @Column(name = "current_year", nullable = false)
    var currentYear: Int,

    @Column(name = "changed_password", nullable = false)
    var changedPassword: Boolean,

    @Column(name = "balance", nullable = false)
    var balance: BigDecimal,

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var payments: MutableList<Payments> = mutableListOf(),

    @OneToOne(optional = false, orphanRemoval = true, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var subjectEnrollments: MutableList<SubjectEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var studyProgramEnrollments: MutableList<StudyProgramEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var preExamActivityResults: MutableList<PreExamActivityResult> = mutableListOf(),

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var heldExamResults: MutableList<HeldExamResult> = mutableListOf(),
) : BaseEntity()

@Repository
interface StudentRepository : JpaSpecificationRepository<Student, UUID> {
    fun findByTranscriptNumber(value: String): Student?
}