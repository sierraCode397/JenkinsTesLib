def buildAndPushImage(String imageName) {
    echo "Building Docker image: ${imageName}"
    sh "docker build -t ${imageName} ."
    
    echo "Pushing Docker image: ${imageName}"
    sh "docker push ${imageName}"
}

return this
