// vars/dockerUtils.groovy
def buildAndPushImage(String fullImageTag, String localAlias) {
    // Hardcoded credentials
    def dockerUser = 'isaacluisjuan107'
    def dockerPass = 'Maverick$@1'  // The password as it is, no escaping needed here

    echo "Logging in to Docker Hub as ${dockerUser}..."
    sh """
      echo '${dockerPass}' | docker login -u '${dockerUser}' --password-stdin
    """  // Notice single quotes around ${dockerPass}

    echo "Tagging local image ${localAlias}:v1.0 as ${fullImageTag} and also tagging as latest"
    sh "docker tag ${localAlias}:v1.0 ${fullImageTag}"
    sh "docker push ${fullImageTag}"

    def latestTag = fullImageTag.replaceAll(/:v1\.0$/, ':latest')
    sh "docker tag ${localAlias}:v1.0 ${latestTag}"
    sh "docker push ${latestTag}"

    sh "docker logout"
}

def stopAndRemoveContainers(String deployPort) {
    echo "Stopping and removing any running containers on port ${deployPort}..."
    sh """
      containers=\$(docker ps -q --filter "publish=${deployPort}")
      if [ ! -z "\$containers" ]; then
        docker stop \$containers
        docker rm \$containers
      else
        echo "No running containers found for port ${deployPort}"
      fi
    """
}
return this


