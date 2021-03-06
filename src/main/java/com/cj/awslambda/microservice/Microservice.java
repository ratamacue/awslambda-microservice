package com.cj.awslambda.microservice;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

@Deprecated
public class Microservice {
  public static Map<String, Object> microservice (Map<String, Object> apiGatewayData, Context context, AWSEnvironment environment, RequestMatcher... requestMatchers){
    Request request = new Request(apiGatewayData, context, environment);
    for(RequestMatcher requestMatcher:requestMatchers){
      if(requestMatcher.matches(request)){
        return requestMatcher.request.apply(request).toLambdaResponse();
      }
    }
    return Response.NOT_IMPLEMENTED().toLambdaResponse();
  }
 
}
