@REM  这个bat的作用： 生产系统注册信息。


@REM set MochaSearchCp="%WAS_HOME%\installedApps\%WAS_NODE%\MochaSearch.ear\MochaSearch.war\WEB-INF\lib\log4j-1.2.4.jar";"%WAS_HOME%\installedApps\%WAS_NODE%\MochaSearch.ear\MochaSearch.war\WEB-INF\lib\MochaSearchLib.jar";"%WAS_HOME%\installedApps\%WAS_NODE%\MochaSearch.ear\MochaSearch.war\WEB-INF\lib\dom4j-full.jar";;"%WAS_HOME%\installedApps\%WAS_NODE%\MochaSearch.ear\MochaSearch.war\WEB-INF\lib\xalan.jar";
set tomcatcp="G:\jakarta-tomcat-5.0.30";
set runpath=".;%tomcatcp%\webapps\store\WEB-INF\lib\dom4j-1.6.1.jar;%tomcatcp%\webapps\store\WEB-INF\lib\log4j-1.2.9.jar;%tomcatcp%\webapps\store\WEB-INF\lib\jaxen-1.1-beta-7.jar;";

"java" -classpath %runpath% com.frame.util.security.AuthorizationService

pause