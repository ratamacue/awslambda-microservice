package com.cj.awslambda.microservice;

import com.amazonaws.services.lambda.runtime.Context;

@FunctionalInterface
public interface RequestAndResponse {
  public Response apply(Request request, Context context);
}
