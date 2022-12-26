package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import com.netflix.graphql.types.errors.ErrorType

class NotFoundException(override val message: String) :
    DgsException(message = message, errorType = ErrorType.NOT_FOUND) {
        companion object {
            val USER_NOT_FOUND_EXCEPTION = DgsEntityNotFoundException("User not found.").toString()
        }
}