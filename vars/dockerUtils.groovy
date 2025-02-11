// vars/dockerUtils.groovy
def buildAndPushImage(String fullImageTag, String dockerUser, String dockerPass, String localAlias) {
    echo "Logging in to Docker Hub as ${dockerUser}..."
    sh '''
      echo "${dockerPass}" | docker login -u "${dockerUser}" --password-stdin
    '''

    echo "Tagging local image ${localAlias}:v1.0 as ${fullImageTag} and also tagging as latest"
    // Tag and push the image with version tag.
    sh "docker tag ${localAlias}:v1.0 ${fullImageTag}"
    sh "docker push ${fullImageTag}"

    // Tag and push the image with the "latest" tag.
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
