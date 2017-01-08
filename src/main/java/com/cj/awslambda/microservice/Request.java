package com.cj.awslambda.microservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Request{
  public final RequestMethod method;
  public final Map<String, String> queryString;
  public final String fullPath;
  public final String proxyPath;
  public final Optional<String> body;

  public Request(Map<String, Object> rawRequestFromAWS){
          method =  RequestMethod.valueOf(rawRequestFromAWS.get("httpMethod").toString());
          queryString = Optional.ofNullable(rawRequestFromAWS.get("queryStringParameters")).map(q->(Map<String, String>)q).orElse(new HashMap<>());
          fullPath = rawRequestFromAWS.get("path").toString();
          proxyPath = Optional.ofNullable(rawRequestFromAWS.get("pathParameters")).map(x->(Map<String, String>)x).flatMap(m->Optional.ofNullable(m.get("proxy"))).orElse("/"); 
          body =  Optional.ofNullable(rawRequestFromAWS.get("body")).map(Object::toString);
  }
  
  private Request(RequestMethod method, Map<String, String> queryString, String fullPath, String proxyPath, Optional<String> body){
    this.method=method;
    this.queryString=queryString;
    this.fullPath=fullPath;
    this.proxyPath=proxyPath;
    this.body=body;
  }
  
  public static class Builder{
    private final Request request; 
    public Builder(){
      request = new Request(RequestMethod.GET, new HashMap<>(), "file://dev/null","", Optional.empty());
    }
    
    public Builder(Request request){
      this.request=request;
    }
    
    public Builder withMethod(RequestMethod method){
      return new Builder(new Request(method, request.queryString, request.fullPath, request.proxyPath, request.body));
    }
    
    public Builder withProxyPath(String proxyPath){
      return new Builder(new Request(request.method, request.queryString, request.fullPath, proxyPath, request.body));
    }
    
    public Builder withBody(Optional<String> body){
      return new Builder(new Request(request.method, request.queryString, request.fullPath, request.proxyPath, body));
    }
    
    public Request build(){
      return request;
    }
    
  }
  
  public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
  }
  
}
