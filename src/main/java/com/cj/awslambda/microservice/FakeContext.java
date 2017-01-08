package com.cj.awslambda.microservice;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Client;
import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class FakeContext implements Context {
  public String requestId = "requestId";
  public String logGroup = "logGroup";
  public String logStreamName="logStreamName";
  public String awsFunctionName="aws-function-name";
  public CognitoIdentity cognitoIdentity = new CognitoIdentity(){
    @Override public String getIdentityId() {return "";}
    @Override public String getIdentityPoolId() {return "";}
  };
  public ClientContext clientContext = new ClientContext(){
    @Override public Client getClient() {return null;}
    @Override public Map<String, String> getCustom() {return null;}
    @Override public Map<String, String> getEnvironment() {return null;}
  };
  public int remainingTimeInMillis = 9999;
  public int memoryLimitInMegabytes = 32;
  public LambdaLogger lambdaLogger = new LambdaLogger(){
    public StringBuffer logs = new StringBuffer();
    @Override public void log(String log) {logs.append(log);}
  };
  
  @Override public String getAwsRequestId() {return requestId;}
  @Override public String getLogGroupName() {return logGroup;}
  @Override public String getLogStreamName() {return logStreamName;}
  @Override public String getFunctionName() {return awsFunctionName;}
  @Override public CognitoIdentity getIdentity() {return cognitoIdentity;}
  @Override public ClientContext getClientContext() {return clientContext;}
  @Override public int getRemainingTimeInMillis() {return remainingTimeInMillis;}
  @Override public int getMemoryLimitInMB() {return memoryLimitInMegabytes;}
  @Override public LambdaLogger getLogger() {return lambdaLogger;}
}
