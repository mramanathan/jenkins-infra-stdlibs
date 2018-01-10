def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    if ( config.get('is_pr') == null ) {
        config.is_pr = env.BRANCH_NAME != null && env.BRANCH_NAME != "master"
    }

    try {
        run(config)
    } catch (Exception rethrow) {
        throw rethrow
    }
}

def run(config) {
    process(config)
}
