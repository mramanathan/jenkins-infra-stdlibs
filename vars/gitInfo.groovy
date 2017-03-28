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
  def origin_name = sh '''
                       git rev-parse --abbrev-ref --symbolic-full-name env.BRANCH_NAME@{u} | sed -F'\/' '{print $1}'
                    '''
  sh "git config remote.${origin_name}.url"
}
