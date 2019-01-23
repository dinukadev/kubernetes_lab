# Import base image
#include base image URL
FROM paolodenti/jessie-jdk8

# Create log file directory and set permission
RUN groupadd -r jlms && useradd -r --create-home -g jlms jlms
RUN if [ ! -d /var/log/ ];then mkdir /var/log/;fi
RUN chown -R jlms:jlms /var/log

# Move project artifact
ADD target/justiceleaguemodule-*.jar /home/jlms/

RUN touch /etc/ld.so.conf.d/java.conf
RUN echo /opt/jdk1.8.0_121/lib/amd64/jli > /etc/ld.so.conf.d/java.conf
RUN ldconfig

RUN setcap CAP_NET_BIND_SERVICE=+eip /opt/jdk1.8.0_121/bin/java
# $JAVA_HOME/bin/java

USER jlms

# Launch application server
ENTRYPOINT exec /opt/jdk1.8.0_121/bin/java $XMS $XMX -jar -Dspring.profiles.active=$ENVIRONMENT  /home/jlms/justiceleaguemodule-*.jar
