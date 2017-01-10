package com.cj.awslambda.microservice;

import java.util.Map;
import java.util.Optional;

public class AWSEnvironment {
  private final Map<String, String> environment;

  public AWSEnvironment(Map<String, String> environment){
    this.environment=environment;
  }
  
  public Integer getMaximumAllocatedMemory(){return Optional.ofNullable(environment.get("AWS_LAMBDA_FUNCTION_MEMORY_SIZE")).map(Integer::parseInt).orElse(0);}
  public String getAccessKeyId(){return environment.get("AWS_ACCESS_KEY_ID");}
  public String getSecretAccessKey(){return environment.get("AWS_SECRET_ACCESS_KEY");}
  public String getAccessKey(){return environment.get("AWS_ACCESS_KEY");}
  public String getSessionToken(){return environment.get("AWS_SESSION_TOKEN");}
  public String getLambdaFunctionName(){return environment.get("AWS_LAMBDA_FUNCTION_NAME");}
  public String getRegion(){return environment.get("AWS_REGION");}
}
