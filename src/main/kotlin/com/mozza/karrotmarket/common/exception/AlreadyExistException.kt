package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.dgs.exceptions.DgsBadRequestException
import com.netflix.graphql.types.errors.ErrorType

class AlreadyExistException(override val message: String) :
    DgsException(message = message, errorType = ErrorType.BAD_REQUEST) {
    companion object {
        val USER_ALREADY_EXIST_EXCEPTION = DgsBadRequestException("User already exits").toString()
        val EMAIL_ALREADY_EXIST_EXCEPTION = DgsBadRequestException("Email already exits").toString()
    }
}