#!/usr/bin/env groovy


def call(body) {
    // The call(bo
    // method with the same name as the file.
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent none

        options {
            timestamps()
            skipDefaultCheckout()
        }

        stages {
            stage('Prep') {
                agent {
                    label 'ubuntu'
                }

                steps {
                    script {
                        stepBuild(config)
                    }
                }
            }
        }
        post{
            always {
                node('ubuntu') {
                    cleanWs()
                }
            }
        }
    }
}
