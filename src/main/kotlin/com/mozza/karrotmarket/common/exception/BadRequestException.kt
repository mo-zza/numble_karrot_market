package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.dgs.exceptions.DgsBadRequestException
import com.netflix.graphql.types.errors.ErrorType

class BadRequestException(override val message: String, private val params: String?) :
    DgsException(message = "$params $message", errorType = ErrorType.BAD_REQUEST) {
}