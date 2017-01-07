package com.davidron.awslambda.microservice;

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
  
  public static class Request{
    final RequestMethod method;
    final Map<String, String> queryString;
    final String fullPath;
    final String proxyPath;
    final Optional<String> body;

    public Request(Map<String, Object> rawRequestFromAWS){
            method =  RequestMethod.valueOf(rawRequestFromAWS.get("httpMethod").toString());
            queryString = Optional.ofNullable(rawRequestFromAWS.get("queryStringParameters")).map(q->(Map<String, String>)q).orElse(new HashMap<>());
            fullPath = rawRequestFromAWS.get("path").toString();
            proxyPath = Optional.ofNullable(rawRequestFromAWS.get("pathParameters")).map(x->(Map<String, String>)x).flatMap(m->Optional.ofNullable(m.get("proxy"))).orElse("/"); 
            body =  Optional.ofNullable(rawRequestFromAWS.get("body")).map(Object::toString);
    }
    
    public enum RequestMethod {
      GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
    }
  }
  
  public static interface RequestAndResponse extends BiFunction<Request, Context, Response>{};
 
}
