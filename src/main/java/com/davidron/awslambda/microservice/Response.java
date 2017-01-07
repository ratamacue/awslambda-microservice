package com.davidron.awslambda.microservice;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.davidron.awslambda.microservice.Microservice.Request.RequestMethod;

public class Response{
  final Integer responseCode;
  final String body;
  final Headers headers;

  public Response(Integer code, String body, Headers... headers){
    this.responseCode=code;
    this.body=body;
    this.headers=merge(headers);
  }


  public static Response OK(String body, Headers... headers){ return new Response(200, body, headers); }
  public static Response METHOD_NOT_SUPPORTED(RequestMethod... allowedMethods){
    return new Response(405, null, header("Allow", Arrays.asList(allowedMethods).stream().map(RequestMethod::toString).collect(Collectors.joining(","))));
  };

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

