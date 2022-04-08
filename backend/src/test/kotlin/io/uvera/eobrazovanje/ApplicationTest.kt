package io.uvera.eobrazovanje

import io.uvera.eobrazovanje.common.repository.StudentRepository
import io.uvera.eobrazovanje.common.repository.TeacherRepository
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.common.repository.UserRepository
import io.uvera.eobrazovanje.security.configuration.CustomUserDetails
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.security.service.JwtAccessService
import io.uvera.eobrazovanje.util.loggerDelegate
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.annotation.PostConstruct

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebMvc
abstract class ApplicationTest {
    @Autowired
    lateinit var context: WebApplicationContext

    lateinit var mockMvc: MockMvc

    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var teacherRepository: TeacherRepository

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Autowired
    lateinit var subjectRepository: StudentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var restBuilder: RestTemplateBuilder

    @LocalServerPort
    var localPort: Int = 0

    @BeforeEach
    fun clearDB() {
        studentRepository.deleteAll()
        subjectRepository.deleteAll()
        userRepository.deleteAllByEmailNotIn(mutableListOf("admin"))
        teacherRepository.deleteAll()
    }

    @PostConstruct
    fun initRestTemplate() {
        if (!this::restTemplate.isInitialized) {
            val customTemplate = restBuilder.rootUri("http://localhost:$localPort").customizers(JWTCustomizer(::adminToken))
            customTemplate.requestFactory { HttpComponentsClientHttpRequestFactory() }
            customTemplate.errorHandler(object : DefaultResponseErrorHandler() {
                override fun hasError(response: ClientHttpResponse): Boolean {
                    val statusCode = response.statusCode
                    return statusCode.series() == HttpStatus.Series.SERVER_ERROR
                }
            })
            restTemplate = TestRestTemplate(customTemplate)
        }
    }

    @BeforeEach
    fun initMocks() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(springSecurity()).build()
    }

    @Autowired
    lateinit var jwtAccessService: JwtAccessService

    private val adminToken: String
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
}

class JWTHttpRequestInterceptor(private val token: String) : ClientHttpRequestInterceptor {
    private val logger by loggerDelegate()

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        request.headers.add("Authorization", "Bearer $token")

        logRequestDetails(request)
        return execution.execute(request, body)
    }

    private fun logRequestDetails(request: HttpRequest) {
        logger.info("Headers: {}", request.headers)
        logger.info("Request Method: {}", request.method)
        logger.info("Request URI: {}", request.uri)
    }
}

class JWTCustomizer(private val token: () -> String) : RestTemplateCustomizer {
    override fun customize(restTemplate: RestTemplate) {
        restTemplate.interceptors.add(JWTHttpRequestInterceptor(token()))
    }
}
