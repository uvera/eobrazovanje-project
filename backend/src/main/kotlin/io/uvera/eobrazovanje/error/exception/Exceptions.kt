package io.uvera.eobrazovanje.error.exception
import org.springframework.core.NestedRuntimeException

// base
open class BadRequestException(message: String) : NestedRuntimeException(message)
open class NotFoundException(message: String) : NestedRuntimeException(message)

// base entity
class EntityNotFoundException(message: String) : NotFoundException(message)
class EntityStateException(message: String) : BadRequestException(message)

// user entity
class UserNotFoundException(message: String) : NotFoundException(message)
class UserStateException(message: String) : BadRequestException(message)

// dto
class DTOStateException(message: String) : BadRequestException(message)
