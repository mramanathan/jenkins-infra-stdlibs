#!/usr/bin/groovy

def call() {
    node("ubuntu") {
        stage('Git-Prep') {
            dir("${WORKSPACE}") {
                deleteDir()
                /*
                sh "if [ -e .git]; then rm -rf .git; fi"
                sh "if [ -e jenkins_pipeline_demo]; then rm -rf jenkins_pipeline_demo; fi"
                */
                sh "git clone https://github.com/mramanathan/jenkins_pipeline_demo.git --branch stdlib_dockergolang"
                env.GIT_COMMIT = sh(returnStdout: true,
                                    script: "cd jenkins_pipeline_demo && git show --oneline | head -1 | cut -d' ' -f1").trim()
            }
        }
    }
}
