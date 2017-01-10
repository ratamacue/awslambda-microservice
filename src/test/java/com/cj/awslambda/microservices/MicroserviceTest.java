package com.cj.awslambda.microservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cj.awslambda.microservice.AWSEnvironment;
import com.cj.awslambda.microservice.FakeContext;
import com.cj.awslambda.microservice.Microservice;
import com.cj.awslambda.microservice.RequestAndResponse;
import com.cj.awslambda.microservice.RequestMatcher;
import com.cj.awslambda.microservice.Response;

public class MicroserviceTest {
  Boolean matched;  //Not thread safe, but also not implicitly final.  drOn must be drunk.
  AWSEnvironment fakeEnvironment = new AWSEnvironment(new HashMap<>());
  
  @Test
  public void testMatchingPath(){
    //GIVEN
    matched=false;
    Map<String, Object> request = new FakeRequestFromAwsBuilder().withProxyPath("test").build();
    RequestAndResponse service = r->{matched=true;return Response.OK("valid response");};
    RequestMatcher matcher = new RequestMatcher().matchingPath("test").respondWith(service);
    
    //WHEN
    Map<String, Object> response = Microservice.microservice(request, new FakeContext(), fakeEnvironment, matcher);
    
    assertTrue(matched);
    assertEquals(response.get("body"), "valid response");
  }
  
  @Test
  public void testNoMatchingPath(){
    //GIVEN
    matched=false;
    Map<String, Object> request = new FakeRequestFromAwsBuilder().withProxyPath("NOPE").build();
    RequestAndResponse service = r->{matched=true;return Response.OK("");};
    RequestMatcher matcher = new RequestMatcher().matchingPath("test").respondWith(service);
    
    //WHEN
    Map<String, Object> response = Microservice.microservice(request, new FakeContext(), fakeEnvironment, matcher);
    
    assertFalse(matched);
  }
  
  @Test
  public void testThatServicesCanReadTheRequest(){
    //GIVEN
    matched=false;
    Map<String, Object> request = new FakeRequestFromAwsBuilder().withProxyPath("test").withBody("This is a body").build();
    RequestAndResponse service = r->{
      matched=true;
      assertEquals(r.body.get(), "This is a body");
      return Response.OK("");
    };
    RequestMatcher matcher = new RequestMatcher().matchingPath("test").respondWith(service);
    
    //WHEN
    Map<String, Object> response = Microservice.microservice(request, new FakeContext(), fakeEnvironment, matcher);
    
    assertTrue(matched);
  }
  
  
  //interface AWSLambda extends BiFunction<Map<String, Object>, Context, Map<String, Object>>{}

}
