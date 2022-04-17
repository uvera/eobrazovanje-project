package io.uvera.eobrazovanje

import io.uvera.eobrazovanje.api.user.auth.dto.AuthenticationRequestDTO
import io.uvera.eobrazovanje.api.user.auth.dto.RefreshRequestDTO
import io.uvera.eobrazovanje.api.user.auth.dto.TokenResponseDTO
import io.uvera.eobrazovanje.api.user.auth.dto.WhoAmIDTO
import io.uvera.eobrazovanje.common.repository.User
import io.uvera.eobrazovanje.security.configuration.RoleEnum
import io.uvera.eobrazovanje.util.extensions.invoke
import io.uvera.eobrazovanje.util.extensions.save
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

class AuthTest : ApplicationTest() {
    @AfterEach
    fun afterEach() = userRepository.deleteAll()

    @Test
    fun `test login and refresh`() = userRepository {
        User(
            firstName = "marko",
            lastName = "marko",
            email = "marko@gmail.com",
            password = "{noop}test",
            role = RoleEnum.TEACHER,
        ).save()

        val (body, res) = restTemplate.postForEntity<TokenResponseDTO>(
            "/api/auth/login",
            AuthenticationRequestDTO(
                email = "marko@gmail.com",
                password = "test"
            )
        ).resolve()
        assert(res.statusCode == HttpStatus.OK)
        assert(body.accessToken.isNotEmpty())
        assert(body.refreshToken.isNotBlank())

        val (bodyRef, resRef) = restTemplate.postForEntity<TokenResponseDTO>(
            "/api/auth/refresh",
            RefreshRequestDTO(
                token = body.refreshToken
            )
        ).resolve()
        assert(resRef.statusCode == HttpStatus.OK)
        assert(bodyRef.accessToken.isNotEmpty())
        assert(bodyRef.refreshToken.isNotBlank())
    }

    @Test
    fun `test whoAmI`() = userRepository {
        val newRestTemplate = restTemplateWithUser("marko@gmail.com", RoleEnum.TEACHER)
        val (getBody, getRes) = newRestTemplate.getForEntity<WhoAmIDTO>(
            "/api/auth/whoami"
        ).resolve()
        println(getBody)
        assert(getRes.statusCode == HttpStatus.OK)
        assert(getBody.email == "marko@gmail.com")
        assert(getBody.role == RoleEnum.TEACHER.toString())
    }
}
