package com.mozza.karrotmarket.common.exception

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

@Component
class CustomDataFetchingExceptionHandler : DataFetcherExceptionHandler {

    @Deprecated("Deprecated in GraphQL Java", replaceWith = ReplaceWith("handleException(handlerParameters)"))
    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        return doHandleException(handlerParameters)
    }

    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        return CompletableFuture.completedFuture(doHandleException(handlerParameters))
    }

    private fun doHandleException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        val exception = unwrapCompletionException(handlerParameters.exception)
        logger.error(
            "Exception while executing data fetcher for ${handlerParameters.path}: ${exception.message}",
            exception
        )

        val graphqlError = when (exception) {
            is DgsException -> exception.toGraphQlError(handlerParameters.path)
            else -> when {
                springSecurityAvailable && isSpringSecurityAccessException(exception) -> TypedGraphQLError.newPermissionDeniedBuilder()
                else -> TypedGraphQLError.newInternalErrorBuilder()
            }.message("%s: %s", exception::class.java.name, exception.message)
                .path(handlerParameters.path)
                .build()
        }

        return DataFetcherExceptionHandlerResult.newResult()
            .error(graphqlError)
            .build()
    }

    private fun unwrapCompletionException(e: Throwable): Throwable {
        return if (e is CompletionException && e.cause != null) e.cause!! else e
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(DefaultDataFetcherExceptionHandler::class.java)

        private val springSecurityAvailable: Boolean by lazy {
            ClassUtils.isPresent(
                "org.springframework.security.access.AccessDeniedException",
                DefaultDataFetcherExceptionHandler::class.java.classLoader
            )
        }

        private fun isSpringSecurityAccessException(exception: Throwable?): Boolean {
            try {
                return exception is AccessDeniedException
            } catch (e: Throwable) {
                logger.trace("Unable to verify if {} is a Spring Security's AccessDeniedException.", exception, e)
            }
            return false
        }
    }
}