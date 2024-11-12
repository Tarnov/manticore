package com.panbet.http.request.executor;


import com.panbet.http.request.executor.request.EntityEnclosingRequest;
import com.panbet.http.request.executor.request.Request;
import com.panbet.http.request.executor.response.EntityEnclosingResponseHandler;


/**
 * This interface declares methods for executing
 * http requests and handling http responses.
 */
public interface HttpRequestExecutor extends AutoCloseable {
    /**
     * This method executes request which contains NO body.
     *
     * @param request         - details of http request.
     * @param responseHandler - response handler which is used to process
     *                        http response and map it to java object.
     * @param <R>             - type of java object, to which http response is mapped.
     * @return - object of type {@link R}, which is created from http response by responseHandler.
     * @throws RequestExecutorException - if request wasn't performed or if response handling failed
     */
    <R> R executeRequest(Request request, EntityEnclosingResponseHandler<R> responseHandler)
            throws RequestExecutorException;

    /**
     * This method executes request which contains a body within itself.
     *
     * @param request         - details of http request.
     * @param responseHandler - response handler which is used to process
     *                        http response and map it to java object.
     * @param <R>             - type of java object, to which http response is mapped.
     * @return - object of type {@link R}, which is created from http response by responseHandler.
     * @throws RequestExecutorException - if request wasn't performed or if response handling failed
     */
    <R> R executeRequest(EntityEnclosingRequest request, EntityEnclosingResponseHandler<R> responseHandler)
            throws RequestExecutorException;
}
