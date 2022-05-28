package io.uvera.eobrazovanje.common.repository

import com.fasterxml.jackson.annotation.JsonCreator
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherEnrollmentViewDTO
import io.uvera.eobrazovanje.api.admin.teacher.dto.TeacherResponseDTO
import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "teacher")
@NamedEntityGraph(
    name = "teacher-graph",
    attributeNodes = [
        NamedAttributeNode("subjectProfessorEnrollments"),
    ]
)
class Teacher(
    @Column(name = "teaching_since", nullable = false) var teachingSince: LocalDate,

    @Column(name = "teacher_type", nullable = false) @Enumerated(EnumType.STRING)
    var teacherType: TeacherType,

    @OneToOne(
        optional = false,
        orphanRemoval = true,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE]
    ) @JoinColumn(
        name = "user_id",
        nullable = false,
    ) var user: User,
    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var subjectProfessorEnrollments: MutableList<SubjectProfessorEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var heldExams: MutableList<HeldExam> = mutableListOf(),

    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var announcements: MutableList<Announcement> = mutableListOf(),
) : BaseEntity()

enum class TeacherType {
    PROFESSOR, ASSISTANT;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromString(value: String) = valueOf(value)
    }
}

@Repository
interface TeacherRepository : JpaSpecificationRepository<Teacher, UUID> {
    @Query("select t from Teacher t where t.id = :id")
    fun findByIdAsDto(id: UUID): TeacherResponseDTO?

    @Query("select t from Teacher t where t.user.email = :email")
    fun findByUserEmailAsDto(email: String): TeacherResponseDTO?

    @Query("select t from Teacher t where t.user.email = :email")
    fun findByUserEmail(email: String): Teacher?

    @Query("select t from Teacher t")
    fun findAllAsDto(pageable: Pageable): Page<TeacherResponseDTO>

    @Query("select t from Teacher t")
    fun findAllTeachers(): List<TeacherResponseDTO>

    @Query("select t from Teacher t where t.user.id = :id")
    fun findByTeacherUser(id: UUID): Teacher?

    @Query("select t from Teacher t where t.id = :id")
    @org.springframework.data.jpa.repository.EntityGraph("teacher-graph")
    fun findTeacherByIdWithExecutions(page: Pageable, id: UUID): Page<TeacherEnrollmentViewDTO>

    @Query("select t from Teacher t where t.id = :id")
    @org.springframework.data.jpa.repository.EntityGraph("teacher-graph")
    fun findTeacherByIdWithExecutions(id: UUID): List<TeacherEnrollmentViewDTO>
}
