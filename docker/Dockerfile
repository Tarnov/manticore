FROM tomcat:8-jre8

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV CATALINA_OPTS "-agentlib:jdwp=transport=dt_socket,address=55555,suspend=n,server=y \
-Ddevenv -Dmanticore.config.path=/usr/local/tomcat/conf/manticore \
-Djava.net.preferIPv4Stack=true \
-Dcom.sun.net.ssl.checkRevocation=false"
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY tomcat /usr/local/tomcat
COPY maven/manticore.war /usr/local/tomcat/webapps/ROOT.war
