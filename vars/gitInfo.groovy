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
  def branch_name = env.BRANCH_NAME
  def origin_name = sh '''
                       git rev-parse --abbrev-ref --symbolic-full-name ${branch_name}@{u} | awk -F'/' '{print $1}'
                    '''
  echo "${origin_name} is the remote for the branch, ${branch_name} and it's remote url is, "
  sh "git config remote.${origin_name}.url"
}
