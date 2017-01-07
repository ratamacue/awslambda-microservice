package com.cj.awslambda.microservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import com.amazonaws.services.lambda.runtime.Context;

public class Microservice {
  public static BiFunction<Map<String, Object>, Context, Map<String, Object>> containing (final RequestMatcher... requestMatchers){
    return new BiFunction<Map<String, Object>, Context, java.util.Map<String, Object>>(){
      public Map<String, Object> apply(Map<String, Object> apiGatewayData, Context context) {
        Request request = new Request(apiGatewayData);
        for(RequestMatcher requestMatcher:requestMatchers){
          if(requestMatcher.matches(request)){
            return requestMatcher.request.apply(request, context).toLambdaResponse();
          }
        }
        return Response.METHOD_NOT_SUPPORTED().toLambdaResponse();
      }
    };  
  }
  

  
  public static interface RequestAndResponse extends BiFunction<Request, Context, Response>{};
 
}
