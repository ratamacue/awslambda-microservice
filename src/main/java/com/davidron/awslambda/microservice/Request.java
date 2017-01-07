package com.davidron.awslambda.microservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Request{
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
