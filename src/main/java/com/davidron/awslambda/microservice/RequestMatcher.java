package com.davidron.awslambda.microservice;

import java.util.Map;
import java.util.Optional;

import com.davidron.awslambda.microservice.Microservice.Request;
import com.davidron.awslambda.microservice.Microservice.Request.RequestMethod;
import com.davidron.awslambda.microservice.Microservice.RequestAndResponse;


public class RequestMatcher {
  final private Optional<String> path;
  final private Optional<RequestMethod> method;
  final private RequestAndResponse response;
  
  public RequestMatcher(){
    this(Optional.empty(), Optional.empty(), (x,y)->Response.METHOD_NOT_SUPPORTED());
  }
  
  private RequestMatcher(Optional<String> path, Optional<RequestMethod> method, RequestAndResponse response){
    this.path=path;
    this.method=method;
    this.response=response;
  }
  
  public RequestMatcher withPath(String path){
    return new RequestMatcher(Optional.of(path), method, response);
  }
  
  public RequestMatcher withHttpMethod(RequestMethod method){
    return new RequestMatcher(path, Optional.of(method), response);
  }
  
  public boolean matches(Request request) {
    boolean pathMatches = path.map(x->x.equals(request.proxyPath)).orElse(Boolean.TRUE);
    boolean methodMatches = method.map(x->x.equals(request.method)).orElse(Boolean.TRUE);
    return pathMatches && methodMatches;
  }

  public Map<String, Object> request(Request request) {
    return null;
  }
  
}
