#!groovy

def call(name) {
    try {
        // Reads the value for the key named, 'sentinel'
        // Fetched value is stashed away for use in Jenkins Pipeline script that invokes this library
        wrap([$class: 'ConsulKVReadWrapper', reads: [[envKey: 'consul_key', key: 'sentinel']]]) {
            echo " =========== Reading key-value data from Consul ============ "
            sh "echo ${env.consul_key} > keyvalue.txt"
            echo "Key, 'sentinel' holds value : ${env.consul_key}"
            stash name: "consul-data"
        }
    } catch (Exception err) {
        echo "Consul key-value data not available"
        return
    }
}
