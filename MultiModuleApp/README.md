# What Does the application do ? </br> 
=> This is a Maven Multi-Module Application which Basically teaches Us: </br>
         &nbsp;&nbsp;&nbsp;&nbsp; 1) How to create Maven MultiModule Application</br>
         &nbsp;&nbsp;&nbsp;&nbsp; 2) How to create transactionId in every Request (Using Thread-Local) and passes that in a different modules </br>
         &nbsp; &nbsp;&nbsp;&nbsp;3) How to save transactionId in a Spring-Security RequestContextHolder </br>
         &nbsp;&nbsp;&nbsp;&nbsp; 4) How to create Spring WebServices using Spring Rest-Template </br>
         &nbsp;&nbsp;&nbsp;&nbsp; 5) How to create a Log-file override Logger</br>
         &nbsp;&nbsp;&nbsp;&nbsp; 6) How to use JPA and traditional Hibernate </br>
         &nbsp;&nbsp;&nbsp;&nbsp; 7) How to use Filter and Interceptor </br>
         &nbsp;&nbsp;&nbsp;&nbsp; 8) How to create H2 database that looks like Physical database</br>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9) This project is inspired from <a href="https://github.com/naturalprogrammer/spring-lemon.git">Spring-Lemon</a> and may help to understand Spring-Lemon on its Study path </br>
                                                                                                                                
          
# How to Start and Run an Application ?                                                                                          </br>
=> Following steps need to do to run the application:                                                                           </br>
          &nbsp;&nbsp;&nbsp;&nbsp;1) Right Click to "MultiModuleApp-parent" => Maven => Update-project                                                  </br>
          &nbsp;&nbsp;&nbsp;&nbsp;2) Right Click to "MultiModuleApp-parent" => Run As => Maven build... => clean install => Run                         </br>
          &nbsp;&nbsp;&nbsp;&nbsp;3) Right CLick to "SpringBootInitialSetup.java" at storefront module => Run as => Java Application                    </br>
          &nbsp;&nbsp;&nbsp;&nbsp;4) You can now see the Running app at below URL: </br>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://localhost:8888/VehicleTrackingSystem/dispatcher  </br> 
          &nbsp;&nbsp;&nbsp;&nbsp;5) Use below Login Credentials to login:                                                             
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dba@gmail.com                    
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cou****** 1 </br>
                                                                                      </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;admin@gmail.com  </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cou****** 1   </br>
                                                                                     </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;syoogesh@gmail.com  </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cou****** 1 </br>										
&nbsp;&nbsp;&nbsp;&nbsp;6) Use below credentials to go to Database :                                                                          </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Database URL ===> http://localhost:8888/VehicleTrackingSystem/dispatcher/h2-console                     </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Driver Class =====>  org.h2.Driver                                                                      </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JDBC URL =====> jdbc:h2:file:./Database                                                                 </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Username ====> sa                                                                                       </br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password ===> (leave it Blank)                                                                          </br>
                        

# What happen when you Run the application ? </br>   
=> Need to pass "envTarget", "persistenceTarget" i.e.let's say you pass [envTarget=dev]   and [persistenceTarget=h2] 

BELOW WILL BE LOADED BEFORE THE SERVER FULLY STARTUP : </br>

&nbsp;1) ContextEvent.java : </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=> The purpose of this class is to set the current active profile into Spring "Environment" </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=> For this, it look for the value of "envTarget" in System property. if found, then it look for "env-{envTarget}.properties </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;file at  resources folder. By the way if it don't find the value of "envTarget" at system property, then by default it takes</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"local". and hence in that case it looks for "env-local.properties" file at Resources folder. There, it search for the key</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"spring.profiles.active" and get the value of that and set that into a Active profile of spring environment. i.e. In our</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;case it sets the profile "local" as active profile in an spring environment.</br>

BELOW WILL BE LOADED DURING THE SERVER STARTUP : </br>

&nbsp;1) ServletConfig.java : </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=> This class loads the Dispatcher servlet </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=> Because of this servletConfig.java, two classes i.e. ContextConfig.java and WebConfig.java are executed now </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1) ContextConfig.java : </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- The purpose of this class is to load all key and values to application context </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - it looks for "env-${envTarget:local}.properties" i.e. env-local.properties (By the way, if envTarget is not set, </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;then it takes default as local) and load that properties file to container </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- it also looks for ContextConfig.xml and loads into the container </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- At last, because of this, it following will load into spring container: </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- spring.profiles.active=local </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <context:annotation-config />  (which is helful for AOP)</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- @EnableAspectJAutoProxy (which is helpful for AOP) </br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2) WebConfig.java :  </br> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- This scans all the web related i.e. Spring mvc related packages from common module and this module and </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;prepare them for spring mvc Module </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Because of this, following will be loaded into a spring container: </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- org.springframework.web.servlet.ViewResolver </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- org.springframework.web.servlet.i18n.SessionLocaleResolver </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- org.springframework.boot.web.servlet.FilterRegistrationBean (here we register our filter </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
"RequestInfoFilter.java" for log) </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- org.springframework.web.servlet.config.annotation.InterceptorRegistry (here we register our interceptor </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
"AuthenticationStateBeanHandlerInterceptor.java") </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- com.yoogesh.common.web.LemonUtils </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- com.yoogesh.common.web.LemonProperties ... and more </br>
			                    
                       
2) SecurityConfig.java
============================
- This is for Spring security. This scans "com.yoogesh.security" and enable security rules

                                                        
3) ServiceConfig.java
=====================
- The purpose of this class is to use @ComponentScan annotation into all services packages
- Here we have also configured our own Message converter and customize the rest accept parameter
                                                                                                                                                                                   
                                                               
4) PersistenceConfig.java
================================
- The purpose of this class is to load persistence related properties from required properties file and also scan repository package for annotation
- it looks for "persistence-${persistenceTarget:h2}.properties" i.e. persistence-h2-properties (By the way, if persistenceTarget is not set then by default it takes h2 value as persistence
  target and loads into container or environment
- It scans the "com.yoogesh.persistence" using @Component scan 
- It scans the "com.yoogesh.persistence.dao.SpringData" using @EnableJpaRepositories  (This is spring data)                                                         
- It annotate the "com.yoogesh.persistence.dao.Hibernate.ProfileRepositoryOldWay.java" using @Repository  (This is traditional hibernate)                                                         
                                                           
                                                               
                                                                                                                                                                                                                                                               
AFTER ABOVE STEPS COMPLETED, FOLLOWING THING WILL HAPPEN:
========================================================

1) as soon as the Application context is initialized, context refershed event is called.Since "SecuritySetup.java" has implemented ContextRefreshedEvent,
   now its onApplicationEvent() method is called [IF THE PROFILE IS DEPLOYED] and because of this following things will happen:
   
               => "BeforeSetupEvent" is called. This event is created by us actually.
               => Since "SpringVerificationsBeforeSetupListener.java" has implemented "BeforeSetupEvent", eventually onApplicationEvent() is called that
                  will make sure the following beans are created on spring container:
                                              => PrivilegeController.class
                                              => RoleController.class
                                              => UserController.class
                                              => AuthenticationController.class
                                              
                                              
               - it calls createPrivileges() method to create privileges
               - it calls createRoles() method to create roles
               - it calls createPrincipals() method to create login users
               
               
               
               
 WHAT WILL HAPPEN WHEN REQUEST COMES?
 ====================================
 - RequestEvent.java is called which creates a transactionId in a TrackingLogger ThreadLocal in a [Guid :: randomNumber] format
 - Request now goes to RequestInfoFilter.java which creates 'AuthenticationRequestBean' object  and sets into a request attribute using request wrapper. This 'AuthenticationRequestBean' contains
   transactionId(just created on a TrackingLogger ThreadLocal by RequestEvent.java), clientIpAddress and BrowserAgent.Hence you have now the TransactionId,
   browserAgent and ClientIpAddress available into that request whenever you want
 - When making a rest call, we are passing this 'AuthenticationRequestBean' into a header
 - Note: When any classes are instantiated like 'MyUserDetailsService', RequestEvent.java is not called.Instead it directly goes into RequestInfoFilter.java and in that case it call the 
   TrackingLogger.getTransactionId which inturn create a transactionId into a TrackingThreadLocal and assigned into a 'AuthenticationRequestBean'. In this case since RequestEvent.java is not
   called, the transactionId format will be [randomNumber] but not [Guid :: randomNumber]
 - Now before rendering the response of this request to the view, an interceptor 'AuthenticationStateBeanHandlerInterceptor' is called and name, role, profile-id and isAuthencated paramater are
   reset into a session at 'getAuthenticationStateBean' i.e. request.getSession.setAttribute('authentication-state-Bean', authenticationStateBean)

          
          
          
          
          
          
