package org.yourcompany

class DockerHelper {
    static def loginToDocker(String user, String pass) {
        sh "echo ${pass} | docker login -u ${user} --password-stdin"
    }
}
