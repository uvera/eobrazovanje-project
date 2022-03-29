package io.uvera.eobrazovanje.error.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import io.uvera.eobrazovanje.util.extensions.compact
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.support.WebExchangeBindException
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.servlet.http.HttpServletRequest


/**
 * Class representing error returned from this API.
 *
 * This class is used for serializing into json format representing error DTO.
 *
 * @property errors Collection representing multiple errors that occurred during request parsing.
 * @property firstError Property representing first error from [errors].
 * @property timestamp Property representing timestamp when error occurred.
 */
class ApiError(
    val timestamp: Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
    val path: String,
    val status: Int,
    val error: String,
    val message: String,
    @ArraySchema(schema = Schema(implementation = ObjectErrorCompact::class, nullable = false))
    val errors: Collection<ObjectErrorCompact>,
    @Schema(implementation = ObjectErrorCompact::class, nullable = true)
    val firstError: ObjectErrorCompact? = errors.firstOrNull(),
) {
    /**
     * [Companion] object of [ApiError] holding helper methods.
     */
    companion object {
        /**
         * Convert a [Exception] to instance of [ApiError].
         *
         * @return ApiError instance.
         */
        fun fromException(exception: Exception, request: HttpServletRequest, status: HttpStatus) = ApiError(
            path = request.requestURI,
            status = status.value(),
            error = status.reasonPhrase,
            message = exception.localizedMessage,
            errors = listOf(
                ObjectErrorCompact(
                    defaultMessage = exception.localizedMessage ?: "Unknown error",
                    code = exception::class.simpleName ?: "UnknownException"
                )
            )
        )

        /**
         * Convert a [BindingResult] to instance of [ApiError].
         *
         * @return ApiError instance.
         */
        fun fromBindException(exception: WebExchangeBindException, request: HttpServletRequest, status: HttpStatus) =
            ApiError(
                path = request.requestURI,
                status = status.value(),
                error = status.reasonPhrase,
                message = exception.localizedMessage,
                errors = exception.bindingResult.allErrors.compact
            )
    }
}
