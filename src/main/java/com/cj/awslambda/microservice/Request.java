package com.cj.awslambda.microservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;

public class Request{
  public final RequestMethod method;
  public final Map<String, String> queryString;
  public final String fullPath;
  public final String proxyPath;
  public final Optional<String> body;
  public final Context context;
  public final AWSEnvironment awsEnvironment;

  public Request(Map<String, Object> rawRequestFromAWS, Context contextFromAws, AWSEnvironment awsEnvironment){
          //method =  Optional.ofNullable(RequestMethod.valueOf(rawRequestFromAWS.get("httpMethod")).toString()));
          method = Optional.ofNullable(rawRequestFromAWS.get("httpMethod")).map(Object::toString).map(RequestMethod::valueOf).orElse(RequestMethod.UNDEFINED);
          queryString = Optional.ofNullable(rawRequestFromAWS.get("queryStringParameters")).map(q->(Map<String, String>)q).orElse(new HashMap<>());
          fullPath = Optional.ofNullable(rawRequestFromAWS.get("path")).map(Object::toString).orElse("/");
          proxyPath = Optional.ofNullable(rawRequestFromAWS.get("pathParameters")).map(x->(Map<String, String>)x).flatMap(m->Optional.ofNullable(m.get("proxy"))).orElse("/"); 
          body =  Optional.ofNullable(rawRequestFromAWS.get("body")).map(Object::toString);
          context=contextFromAws;
          this.awsEnvironment=awsEnvironment;
  }
  
  private Request(RequestMethod method, Map<String, String> queryString, String fullPath, String proxyPath, Optional<String> body, Context context, AWSEnvironment awsEnvironment){
    this.method=method;
    this.queryString=queryString;
    this.fullPath=fullPath;
    this.proxyPath=proxyPath;
    this.body=body;
    this.context=context;
    this.awsEnvironment = awsEnvironment;
    
  }
  
  public static class Builder{
    private final Request request; 
    public Builder(){
      request = new Request(RequestMethod.GET, new HashMap<>(), "file://dev/null","", Optional.empty(), new FakeContext(), new AWSEnvironment(new HashMap<>()));
    }
    
    public Builder(Request request){
      this.request=request;
    }
    
    public Builder withMethod(RequestMethod method){
      return new Builder(new Request(method, request.queryString, request.fullPath, request.proxyPath, request.body, request.context, request.awsEnvironment));
    }
    
    public Builder withProxyPath(String proxyPath){
      return new Builder(new Request(request.method, request.queryString, request.fullPath, proxyPath, request.body, request.context, request.awsEnvironment));
    }
    
    public Builder withBody(Optional<String> body){
      return new Builder(new Request(request.method, request.queryString, request.fullPath, request.proxyPath, body, request.context, request.awsEnvironment));
    }
    
    public Request build(){
      return request;
    }
    
  }
  
  public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, UNDEFINED;
  }
  
}
