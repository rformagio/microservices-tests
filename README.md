# microservices-tests
## Unit tests sample for microservices

I had a lot of questions about test implementation so I created this sample application to document some 
important points that I learned from researching about the topic.

From *StackOverFlow* :
> Unit tests are designed to test a component in isolation from other components and unit tests have also 
> a requirement: being as fast as possible in terms of execution time as these tests may be executed each
> day dozen times on the developer machines.


#### Difference between @Mock and @InjectMocks annotations
 As in *HowToDoInJava* (https://howtodoinjava.com/mockito/mockito-mock-injectmocks/) :
> * @Mock – creates mocks
> * @InjectMocks – creates objects and inject mocked dependencies
> 
> A mock object is an interface to hide a dependency with cannot be tested in test environment e.g. database, 
> network locations etc. A method invoked using mocked reference does not execute method body defined in class 
> file, rather the method behavior is configured using when-thenReturn methods combinations.
> 
> In a junit test, we create objects for the class which needs to be tested and it's methods to be invoked. We
> create mocks for the dependencies which will not be present in test environment and objects are dependent on 
> it to complete the method call.
 
#### Difference between @Mock and @MockBean
 
 When you don't need any dependencies from SpringBoot container, you can use @Mock.
 @MockBean can be used to add mocks to a Spring context.
 
 Spring Boot documentation:
 
 > Often @WebMvcTest will be limited to a single controller and used in combination with 
 > @MockBean to provide mock implementations for required collaborators.
 

 #### SpringRunner vs SpringJUnit4ClassRunner
 
 SpringRunner is just an alias for the SpringJUnit4ClassRunner. SpringRunner is available on spring-test 4.3.
 It extends SpringJUnit4ClassRunner

#### MockitoJUnitRunner

It doesn't load the complete Spring context. It's faster  than SpringRunner. It's used for simple test of single java class.
By using this MockitoJUnitRunner enable to use @Mock annotation. If you don't use MockitoJUnitRunner, you have to use Mockito.mock() method.

*Rodrigo Formagio*
