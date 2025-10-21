ARG VARIANT=21-bookworm
FROM mcr.microsoft.com/devcontainers/java:1-${VARIANT}

ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""

RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# [Choice] Node.js version: none, lts/*, 16, 14, 12, 10
ARG NODE_VERSION="none"
RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# Install required packages
RUN apt-get update \
    && apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release sudo

# Install additional dependencies required by Apache Tika (document parser for Ingestor)
RUN apt-get update \
    && apt-get install -y tesseract-ocr libxtst6 libxi6

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1

ARG USERNAME=vscode
ENV CATALINA_HOME=/opt/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

RUN mkdir ${CATALINA_HOME} && chown ${USERNAME}:${USERNAME} ${CATALINA_HOME}

# Add the user to the sudo group and configure NOPASSWD for sudo
RUN echo "${USERNAME} ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/${USERNAME} && \
    chmod 0440 /etc/sudoers.d/${USERNAME}

USER $USERNAME

# Set environment variables for Tomcat

# Download and extract Tomcat 10
WORKDIR /tmp
RUN wget https://dlcdn.apache.org/tomee/tomee-10.1.2/apache-tomee-10.1.2-plus.tar.gz -O tomcat.tar.gz \
    && tar xvfz tomcat.tar.gz \
    && mv apache-tomee-plus-10.1.2/* $CATALINA_HOME/ \
    && rm -rf apache-tomee-plus-10.1.2 \
    && rm tomcat.tar.gz
RUN wget https://jdbc.postgresql.org/download/postgresql-42.7.3.jar -P $CATALINA_HOME/lib/

COPY tomee/tomee.xml ${CATALINA_HOME}/conf/tomee.xml
COPY tomee/tomcat-users.xml ${CATALINA_HOME}/conf/tomcat-users.xml

RUN mkdir -p /home/${USERNAME}/.rsp/redhat-community-server-connector/servers
COPY tomee/TomEE_10 /home/${USERNAME}/.rsp/redhat-community-server-connector/servers/TomEE_10
