#!/usr/bin/env groovy

def call(body) {
  // The call(body) method in any file in workflowLibs.git/vars is exposed as a
  // method with the same name as the file.
  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  sh "cd ${config.wsdir}"
  sh "git config remote.origin.url"
}
