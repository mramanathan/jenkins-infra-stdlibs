#!/usr/bin/groovy

def call() {
    node("ubuntu") {
        stage('Git-Prep') {
            dir("${WORKSPACE}") {
                deleteDir()
                sh "git clone https://github.com/mramanathan/jenkins_pipeline_demo.git --branch stdlib_dockergolang"
            }
        }
    }
}
