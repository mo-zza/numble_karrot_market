package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.types.errors.ErrorType
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.ResultPath


abstract class DgsException(
    override val message: String,
    override val cause: Exception? = null,
    val errorType: ErrorType = ErrorType.UNKNOWN
) : RuntimeException(message, cause) {
    companion object {
        const val EXTENSION_CLASS_KEY = "class"
    }

    fun toGraphQlError(path: ResultPath? = null): TypedGraphQLError {
        return TypedGraphQLError
            .newBuilder()
            .apply {
                if (path != null) {
                    path(path)
                }
            }
            .errorType(errorType)
            .message(message)
            .extensions(mapOf(EXTENSION_CLASS_KEY to this::class.java.name))
            .build()
    }
}