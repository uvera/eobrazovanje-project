package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.api.admin.student.dto.StudentViewDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "student")
@NamedEntityGraph(
    name = "student-graph",
    attributeNodes = [
        NamedAttributeNode("preExamActivityResults"),
    ]
)
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

    @ManyToOne(optional = true, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "study_program_id", nullable = true)
    var studyProgram: StudyProgram? = null,

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var preExamActivityResults: MutableList<PreExamActivityResult> = mutableListOf(),

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var heldExamResults: MutableList<HeldExamResult> = mutableListOf(),

    @OneToMany(mappedBy = "student", orphanRemoval = true)
    var examEnrollments: MutableList<ExamEnrollment> = mutableListOf(),
) : BaseEntity()

@Repository
interface StudentRepository : JpaSpecificationRepository<Student, UUID> {
    fun findByUserId(id: UUID): Student?

    @Query("select s from Student s where s.user.email = :email")
    fun findByUserEmailOrNull(email: String): Student?

    @Query("select s from Student s where s.user.email = :email")
    fun findByUserEmailAsDto(email: String): StudentViewDTO?

    fun findByTranscriptNumber(value: String): Student?

    @Query("select t from Student t where t.id = :id")
    fun findByIdAsDto(id: UUID): StudentViewDTO?

    @Query("select s from Student s")
    fun findAllAsDto(pageable: Pageable): Page<StudentViewDTO>

    @Query("select s from Student s")
    fun findAllStudents(): List<StudentViewDTO>

    @Query("select s from Student s where s.studyProgram is null")
    fun findAllWhereNoStudyProgram(): List<StudentViewDTO>
}
