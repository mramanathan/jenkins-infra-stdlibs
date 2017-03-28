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
  // why isn't env.BRANCH_NAME working!!! ?
  def branch_name = sh(returnStdout: true, script: "git rev-parse --abbrev-ref HEAD").trim()
  def origin_name = sh(returnStdout: true, script: "git remote").trim()
  // def origin_name = sh '''
  //                     git rev-parse --abbrev-ref --symbolic-full-name ${branch_name}@{u} | awk -F'/' '{print $1}'
  //                  '''
  echo "${origin_name} is the remote for the branch, ${branch_name} and it's remote url is, "
  sh "git config remote.${origin_name}.url"

  // preparation for env for git operations...
  sh "git config user.email rus.cahimb@gmail.com"
  sh "git config user.name mramanathan"

  tagStatus = sh(script: "git tag -fa ${config.tagname} -m 'Git workflow library tag, ${config.tagname}'", returnStatus: true)
  println "~> Status of git tagging: "
  println "${tagStatus}"

  if ( tagStatus == 0 ) { 
    echo "Pushing ${config.tagname} to ${origin_name}..."
    sh "git push --tags --progress ${origin_name} ${config.tagname}"
  } else {
    echo "Git tagging failed, nothing to push to the remote"
  }

}
