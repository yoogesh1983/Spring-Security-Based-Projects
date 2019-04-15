# What is the Application about ? </br>
&nbsp;=> This is a Spring-Security application replacing legacy web.xml by Java configuration (Annotation) approach </br>

# How to run the application ? </br>
&nbsp;=> This application needs to be run at tomcat and may get 403 when run into wild-fly server </br>
&nbsp;=> Right click on a project => Maven => update-project</br>
&nbsp;=> Right click on a project => run as => Run on server => run </br>
&nbsp;=> You can now browse the application at <strong>http://localhost:8080/SpringSecurity-AnnotationApproach/</strong> </br>
&nbsp;=> In case of 404, make sure to change the JarfileName in a web-project setting which sometime may not update</br>
&nbsp;=> You may get 500 error saying the css code not found. If that happens, the project structure might be like below:</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              ![./docs/Project_View.PNG](./docs/Project_View.PNG) <br/>
              
&nbsp;=> Normally when you clone a fresh copy from Git, the structure might be like above where the resources folder is not in a class path. So that in this condition, do the following: </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Open project in eclipse => Right click into a resources folder => build path => use as a Source Folder</strong> </br>
&nbsp;=> The file structure will be like below now and it should solve the problem:</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              ![./docs/Project_View_After_Change.PNG](./docs/Project_View_After_Change.PNG) <br/>
              
&nbsp;=> You may need to clear browser cache if you get 405 Error</br>
