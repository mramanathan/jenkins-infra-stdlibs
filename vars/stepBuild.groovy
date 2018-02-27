#!/usr/bin/groovy


def call(config) {
    if ( config.reponame == null ) {
            stepgitPrep()
    }
    node("ubuntu") {
        stage('gobuild') {
            dir("${WORKSPACE}/jenkins_pipeline_demo") { //Go code is in here...
                docker.image(config.environment).inside {
                    // Take note: shell step args is not enclosed in ' or "
                    sh config.buildScript
                    // https://jenkins.io/doc/pipeline/steps/docker-workflow/#code-withdockercontainer-code-run-build-steps-inside-a-docker-container
                    // all nested sh steps should run inside that container b'cos the workspace is mounted read-write into the container.
                    if ( fileExists('hello') ) {
                        println "Go build passed with Git commit " + "${GIT_COMMIT}"
                        currentBuild.result = "SUCCESS"
                    } else {
                        println "Go build failed with Git commit " + "${GIT_COMMIT}"
                        currentBuild.result = "FAILURE"
                    }
                }
            }
        }
    }
}
