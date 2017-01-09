package com.cj.awslambda.microservice;

import java.util.Optional;

import com.cj.awslambda.microservice.Request.RequestMethod;


public class RequestMatcher {
  final private Optional<String> path;
  final private Optional<RequestMethod> method;
  final RequestAndResponse request;
  
  public RequestMatcher(){
    this(Optional.empty(), Optional.empty(), (x)->Response.METHOD_NOT_SUPPORTED());
  }
  
  public RequestMatcher matchingPath(String path){
    return new RequestMatcher(Optional.of(path), method, request);
  }
  
  public RequestMatcher matchingMethod(RequestMethod method){
    return new RequestMatcher(path, Optional.of(method), request);
  }
  
  public RequestMatcher respondWith(RequestAndResponse requestAndResponse){
    return new RequestMatcher(path, method, requestAndResponse);
  }
  
  boolean matches(Request request) {
    boolean pathMatches = path.map(x->x.equals(request.proxyPath)).orElse(Boolean.TRUE);
    boolean methodMatches = method.map(x->x.equals(request.method)).orElse(Boolean.TRUE);
    return pathMatches && methodMatches;
  }
  
  private RequestMatcher(Optional<String> path, Optional<RequestMethod> method, RequestAndResponse requestAndResponse){
    this.path=path;
    this.method=method;
    this.request=requestAndResponse;
  }
  
  
}
