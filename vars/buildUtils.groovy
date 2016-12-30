#!groovy

def call(name) {
    node('linux') {
        checkout scm
        echo " == URL with repo name == "
        def url = "https://github.com/mramanathan/${name}.git"
        echo "${url}"
    }
}
