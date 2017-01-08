package com.cj.awslambda.microservices;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.cj.awslambda.microservice.FakeContext;
import com.cj.awslambda.microservice.Request.RequestMethod;

public class FakeRequestFromAwsBuilder {
  
  /**
   *   public Request(Map<String, Object> rawRequestFromAWS, Context contextFromAws){
          method =  RequestMethod.valueOf(rawRequestFromAWS.get("httpMethod").toString());
          queryString = Optional.ofNullable(rawRequestFromAWS.get("queryStringParameters")).map(q->(Map<String, String>)q).orElse(new HashMap<>());
          fullPath = rawRequestFromAWS.get("path").toString();
          proxyPath = Optional.ofNullable(rawRequestFromAWS.get("pathParameters")).map(x->(Map<String, String>)x).flatMap(m->Optional.ofNullable(m.get("proxy"))).orElse("/"); 
          body =  Optional.ofNullable(rawRequestFromAWS.get("body")).map(Object::toString);
          context=contextFromAws;
  }
  
   * @return
   */
  
  private final RequestMethod method;
  private final Map<String, String> queryString;
  private final String fullPath;
  private final String proxyPath;
  private final Optional<String> body;
  private final Context context;
  
  public FakeRequestFromAwsBuilder(){
    this.method=RequestMethod.GET;
    this.queryString=new HashMap<>();
    this.fullPath="/dev/null";
    this.body=Optional.empty();
    this.context=new FakeContext();
    this.proxyPath="";
    
  }
  
  public FakeRequestFromAwsBuilder(RequestMethod method,Map<String, String> queryString,String fullPath,String proxyPath,Optional<String> body,Context context){
    this.method=method;
    this.queryString=queryString;
    this.fullPath=fullPath;
    this.body=body;
    this.context=context;
    this.proxyPath=proxyPath;
  }
  
  public Map<String, Object> build() {
    Map<String, Object> map = new TreeMap<>();
    Map<String, Object> pathParameters = new TreeMap<>();
    pathParameters.put("proxy",proxyPath);
    map.put("httpMethod", method);
    map.put("queryStringParameters", queryString);
    map.put("path", fullPath);
    map.put("pathParameters", pathParameters);
    if(body.isPresent()) map.put("body", body.get());
    return map;
  }

  public FakeRequestFromAwsBuilder withProxyPath(String proxyPath) {
    return new FakeRequestFromAwsBuilder(method, queryString, fullPath, proxyPath, body, context);
  }

  public FakeRequestFromAwsBuilder withBody(String body) {
    // TODO Auto-generated method stub
    return new FakeRequestFromAwsBuilder(method, queryString, fullPath, proxyPath, Optional.ofNullable(body), context);
  }

}
