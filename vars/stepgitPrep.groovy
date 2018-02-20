#!/usr/bin/groovy

def call(config) {
    node("${config.machine}") {
        stage('Prep') {
            dir("${WORKSPACE}")
                sh "git clone https://github.com/mramanathan/jenkins_pipeline_demo.git --branch stdlib_dockergolang"
                env.GIT_COMMIT = sh(returnStdout: true,
                                    script: "cd jenkins_pipeline_demo && git show --oneline | head -1 | cut -d' ' -f1").trim()
            }
        }
    }
}
