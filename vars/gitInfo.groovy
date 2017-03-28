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
  echo "Gitlib :~> ${origin_name} is the remote for the branch, ${branch_name} and it's remote url is, "
  // just 'remote' name is not sufficient to have git push the tags...this is the error in such scenario
  // fatal: could not read Username for 'https://github.com': No such device or address
  def remote_url = sh(script: "git config remote.${origin_name}.url", returnStdout: true).trim()
  echo "Gitlib :~> ${remote_url}"

  // preparation for env for git operations...
  sh "git config user.email rus.cahimb@gmail.com"
  sh "git config user.name mramanathan"

  tagStatus = sh(script: "git tag -fa ${config.tagname} -m 'Git workflow library tag, ${config.tagname}'", returnStatus: true)
  println "Gitlib :~> Status of git tagging: "
  println "Gitlib :~> ${tagStatus}"

  if ( tagStatus == 0 ) { 
    echo "Gitlib :~> Pushing ${config.tagname} to ${origin_name}..."
    sh "git push --tags --progress ${remote_url} ${config.tagname}"
  } else {
    echo "Gitlib :~> Git tagging failed, nothing to push to the remote"
  }

}
