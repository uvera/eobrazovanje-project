package io.uvera.eobrazovanje.common.repository

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "teacher")
class Teacher(
    @Column(name = "teaching_since", nullable = false) var teachingSince: LocalDate,

    @Column(name = "teacher_type", nullable = false) @Enumerated(EnumType.STRING)
    var teacherType: TeacherType,

    @OneToOne(optional = false, orphanRemoval = true) @JoinColumn(
        name = "user_id",
        nullable = false
    ) var user: User,
    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var subjectProfessorEnrollments: MutableList<SubjectProfessorEnrollment> = mutableListOf(),

    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var heldExams: MutableList<HeldExam> = mutableListOf(),

    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    var announcements: MutableList<Announcement> = mutableListOf(),
) : BaseEntity()

enum class TeacherType {
    PROFESSOR, ASSISTANT
}
