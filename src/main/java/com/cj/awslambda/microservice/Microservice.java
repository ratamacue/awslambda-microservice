package com.cj.awslambda.microservice;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

@Deprecated
public class Microservice {
  public static Map<String, Object> microservice (Map<String, Object> apiGatewayData, Context context, RequestMatcher... requestMatchers){
    Request request = new Request(apiGatewayData, context);
    for(RequestMatcher requestMatcher:requestMatchers){
      if(requestMatcher.matches(request)){
        return requestMatcher.request.apply(request).toLambdaResponse();
      }
    }
    return Response.METHOD_NOT_SUPPORTED().toLambdaResponse();
  }
 
}
