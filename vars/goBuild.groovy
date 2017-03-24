#!/usr/bin/env groovy

def call(body) {
  // The call(body) method in any file in workflowLibs.git/vars is exposed as a
  // method with the same name as the file.
  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  node('docker') {
    stage('Build and Record') {
      docker.image(config.environment).inside {
        // Take note: shell step args is not enclosed in ' or "
        sh config.buildScript
        // https://jenkins.io/doc/pipeline/steps/docker-workflow/#code-withdockercontainer-code-run-build-steps-inside-a-docker-container
        // all nested sh steps should run inside that container b'cos the workspace is mounted read-write into the container.
        if ( fileExists('hello') ) {
          // writeFile file: "buildresult.txt", text: "PASS"
          println "Go build passed !"
          currentBuild.result = "SUCCESS"
        } else {
          println "Go build failed !"
          currentBuild.result = "FAILURE"
        }
      }
    }
  }
}
