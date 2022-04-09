package io.uvera.eobrazovanje.common.repository

import io.uvera.eobrazovanje.util.extensions.JpaSpecificationRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject")
class Subject(
    @Column(name = "espb", nullable = false)
    var espb: Int,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "year", nullable = false)
    var year: Int,

    @ManyToOne(optional = true)
    @JoinColumn(name = "study_program_id", nullable = true)
    var studyProgram: StudyProgram? = null,

    @OneToMany(mappedBy = "subject", orphanRemoval = true)
    var subjectExecutions: MutableList<SubjectExecution> = mutableListOf(),

) : BaseEntity()

@Repository
interface SubjectRepository : JpaSpecificationRepository<Subject, UUID>
