# What is the Application about ? </br>
&nbsp;=> This is a Spring-Security application replacing legacy web.xml by Java configuration (Annotation) approach </br>

# How to run the application ? </br>
&nbsp;=> This application needs to be run at tomcat and may get 403 when run into wild-fly server </br>
&nbsp;=> Right click on a project => Maven => update-project</br>
&nbsp;=> Right click on a project => run as => Run on server => run </br>
&nbsp;=> You can now browse the application at http://localhost:8080/SpringSecurity-AnnotationApproach/ </br>
&nbsp;=> In case of 404, make sure to change the JarfileName in a web-project setting which sometime may not update</br>
&nbsp;=> You may get 500 error saying the css code not found.If that happens, just make sure the project structure is like below:</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![./docs/ServicePipeline.svg](./docs/Project_View.PNG)
