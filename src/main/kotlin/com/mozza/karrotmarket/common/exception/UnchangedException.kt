package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.dgs.exceptions.DgsBadRequestException
import com.netflix.graphql.types.errors.ErrorType

open class UnchangedException(override val message: String) :
    DgsException(message = message, errorType = ErrorType.BAD_REQUEST) {
        companion object {
            val PASSWORD_UNCHANGED_EXCEPTION = DgsBadRequestException("Password must be changed").toString()
            val NAME_UNCHANGED_EXCEPTION = DgsBadRequestException("Name must be changed").toString()
            val PHONE_UNCHANGED_EXCEPTION = DgsBadRequestException("Phone must be changed").toString()
        }
}