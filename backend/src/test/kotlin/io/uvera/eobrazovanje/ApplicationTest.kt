package io.uvera.eobrazovanje

import io.uvera.eobrazovanje.common.repository.StudentRepository
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.common.repository.UserRepository
import io.uvera.eobrazovanje.security.configuration.CustomUserDetails
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.security.service.JwtAccessService
import liquibase.pro.packaged.T
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebMvc
abstract class ApplicationTest {
    @Autowired
    lateinit var context: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Autowired
    lateinit var subjectRepository: StudentRepository

    @BeforeEach
    fun clearDB() {
        studentRepository.deleteAll()
        subjectRepository.deleteAll()
        userRepository.deleteAll()
    }

    @BeforeEach
    fun initMocks() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(springSecurity()).build()
    }

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtAccessService: JwtAccessService

    val adminToken: String
        get() = run {
            val user = userRepository.findByEmail("admin") ?: userRepository.save(
                User(
                    firstName = "admin",
                    lastName = "admin",
                    email = "admin",
                    password = "{noop}admin",
                    roles = mutableListOf(RoleEnum.ADMIN),
                )
            )
            jwtAccessService.generateToken(CustomUserDetails(user))
        }

    companion object {
        @Container
        val container = PostgreSQLContainer("postgres:13").apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("postgres")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }

    fun <T : Any?> entityWithAuth(body: T) = HttpEntity<T>(
        body,
        HttpHeaders().also {
            it.add("Authorization", "Bearer $adminToken")
        }
    )
}
