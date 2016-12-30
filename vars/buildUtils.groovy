#!groovy

def call(name) {
    node('linux') {
        checkout scm
        def url = "https://github.com/jenkinsci/${name}-plugin.git"
        echo " == URL with repo name == "
        echo "${url}"
    }
}
