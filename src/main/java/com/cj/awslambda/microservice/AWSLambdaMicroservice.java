package com.cj.awslambda.microservice;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public interface AWSLambdaMicroservice extends RequestHandler<Map<String, Object>, Map<String, Object>>{
  Response lambda(Request request);
  
  @Override
  default public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
    return lambda(new Request(request, context, new AWSEnvironment(System.getenv()))).toLambdaResponse();
  }
  
}
