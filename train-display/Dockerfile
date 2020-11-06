FROM jboss/wildfly
ADD target/train-display-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ENV JPDA_ADDRESS="8001"
ENV JPDA_TRANSPORT="dt_socket"
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]