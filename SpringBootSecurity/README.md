# What is the Application about? </br>
- This is a Spring-Boot application that uses Spring-Security</br>
- This application can be run from the <b>commandLine</b> or from the eclipse using embedded server at Port <b>8888</b> and can also be run at external webServer like <b>tomcat</b> at port <b>8080</b> </br>
- This project is created on a java version 11, so the version of java to run this project must be java 11 or more

# How to run the Application? </br>
Do the following once the compile is done and war is creted inside a target folder:</br>

<b>When running from a command line:</b> </br>
> java -jar SpringBootSecurity.war </br>

The application can now be browsed at http://localhost:8888/SpringBootSecurity/dispatcher </br>

<b>When running from eclipse with embedded tomcat:</b> </br>
> Right click to the <b>DispatcherServletInitializer.java</b></br>
> Run As -> Java Application</br>

The application can now be browsed at http://localhost:8888/SpringBootSecurity/dispatcher </br>

<b>When deploying to the external tomcat:</b> </br>
> copy the <b>SpringBootSecurity.war</b> from a target folder and put inside the <b>webapp</b> folder of tomcat server</br>
> Start the server by double clicking on a <b>startup.bat</b> inside a <b>bin</b> folder of tomcat</br>

The application can now be browsed at http://localhost:8080/SpringBootSecurity/dispatcher </br>
