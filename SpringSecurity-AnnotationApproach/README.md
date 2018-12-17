# What is the Application about ? </br>
&nbsp;=> This is a Spring-Security application replacing legacy web.xml by Java configuration (Annotation) approach </br>

# How to run the application ? </br>
&nbsp;=> This application needs to be run at tomcat and may get 403 when run into wild-fly server </br>
&nbsp;=> Right click on a project => Maven => update-project
&nbsp;=> Right click on a project => run as => Run on server => run </br>
&nbsp;=> You can now browse the application at http://localhost:8080/SpringSecurity-AnnotationApproach/ </br>
&nbsp;=> In case of 404, make sure to change the JarfileName in a web-project setting which sometime may not update</br>
&nbsp;=> Also you may get 500 error while reading message_US folder during sign-up.In this schenario create new source folder named "dat"
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and put current dat folders files inside the newly created folder (and delete the existing dat folder). This should solve 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the problem.
