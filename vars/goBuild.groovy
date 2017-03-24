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
    stage('Preparation...') {
      deleteDir()
    }
    stage('SCM') {
      checkout scm
    }
    stage('Go Build') {
      docker.image(config.environment).inside {
        sh config.buildScript
      }
    }
    stage('Record Result') {
      if (fileExists('hello')) {
        writeFile file: "buildresult.txt", text: "PASS"
      } else {
        writeFile file: "buildresult.txt", text: "FAIL"
      }
    }
  }
}
