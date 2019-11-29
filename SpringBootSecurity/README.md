# What is the Application about? </br>
- This is a Spring-Boot application that uses Spring-Security</br>
- This application can be run from the <b>commandLine</b> or from the eclipse using embedded server at Port <b>8888</b> and can also be run at external webServer like <b>tomcat</b> at port <b>8080</b> </br>

# How to run the Application once the compile is done and war is creted inside a target folder? </br>
When running from a command line: </br>
> npm install </br>
> npm run dev </br>
> if first time, close the server by using ctrl+C and do "npm run dev" again(Since css will not update first time. Bug!!) </br>

The application can now be browsed at http://localhost:3000/ </br>

if not run successfully, You may need to do the following installation</br>

> npm install phantomjs-prebuilt@2.1.16 --ignore-scripts </br>
> npm install npm-run-all --save-dev </br>
> npm install -g nodemon </br>

# How to clone a project (Using VisualStudio Code) </br>
> Ctrl+Shift+p </br>
> git clone </br>
> Enter a URL </br>
> select a folder you want to save the the project into your local</br>
> Close the project from Visual code and again open the particular project inside the folder i.e. REACTContact_SSR </br>

Run a following command to commit git from visualcode (Otherwise it will not show the username password modal popup) </br>
> git config --global user.email "yoogesh1983@gmail.com" </br>
> git config --global user.name "Yoogesh"</br>

