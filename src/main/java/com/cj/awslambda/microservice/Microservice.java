package com.cj.awslambda.microservice;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

public class Microservice {
  public static Map<String, Object> microservice (Map<String, Object> apiGatewayData, Context context, RequestMatcher... requestMatchers){
    Request request = new Request(apiGatewayData, context);
    System.out.println("Time to test some matchers...");
    for(RequestMatcher requestMatcher:requestMatchers){
      if(requestMatcher.matches(request)){
        System.out.println("Found a match...  returning.");
        return requestMatcher.request.apply(request).toLambdaResponse();
      }
    }
    return Response.METHOD_NOT_SUPPORTED().toLambdaResponse();
  }
 
}
