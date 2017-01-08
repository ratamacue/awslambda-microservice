package com.cj.awslambda.microservice;

@FunctionalInterface
public interface RequestAndResponse {
  public Response apply(Request request);
}
