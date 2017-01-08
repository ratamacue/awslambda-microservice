package com.cj.awslambda.microservice;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.cj.awslambda.microservice.Request.RequestMethod;

public class Response{
  final public Integer responseCode;
  final public Optional<String> body;
  final public Headers headers;

  public Response(Integer code, Headers... headers){
    this.responseCode=code;
    this.body=Optional.empty();

    this.headers=merge(headers);
  }

  public Response(Integer code, String body, Headers... headers){
    this.responseCode=code;
    this.body=Optional.ofNullable(body);
    this.headers=merge(headers);
  }
  
  public Map<String, Object> toLambdaResponse(){
        TreeMap<String, Object> lambdaResponse = new TreeMap<>();
        lambdaResponse.put("statusCode", this.responseCode);
        lambdaResponse.put("headers", this.headers);
        lambdaResponse.put("body", this.body.orElse(""));  
        return lambdaResponse;
        
        
  }

  public static Response OK(String body, Headers... headers){ return new Response(200, body, headers); }
  public static Response METHOD_NOT_SUPPORTED(RequestMethod... allowedMethods){
    return new Response(405, header("Allow", Arrays.asList(allowedMethods).stream().map(RequestMethod::toString).collect(Collectors.joining(","))));
  };
  public static Response AUTHENTICATION_FAILED(){return new Response(401);}
  public static Response NOT_IMPLEMENTED(){return new Response(501);}

  public static Headers header(String key, String value){
    Headers header = new Headers();
    header.put(key, value);
    return header;
  }

  private static Headers merge(Headers[] headers){
    Headers newHeaders = new Headers();
    for(Headers next:headers){newHeaders.putAll(next);}
    return newHeaders;
  }


  @SuppressWarnings("serial")
  public static class Headers extends TreeMap<String, String>{}

}

