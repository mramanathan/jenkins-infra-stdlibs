class baseamiBuild extends amiBuild {
    def brancher(config) {
        node('ubuntu') {
            stage('Brancher') {
                if ( config.is_pr ) {
                    println "It is pull request branch"
                } else {
                    println "It is not a pull request branch"
                }
            }
        }
    }
}
