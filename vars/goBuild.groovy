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
                steps {
                    script {
                        // CAUTION: do _NOT_ run this code in 'master' meant for production!!!
                        if ( config.machine ?: 'master') {
                            stepgitPrep(config)
                            stepBuild(config)
                        }
                    }
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }
    }
}
