### consulKVRead Library

#### Pre-requisites

- Install consul key value builder plugin that's available [here] (https://plugins.jenkins.io/consul-kv-builder)
- Active Consul agent(s), follow the instructions [here] (https://www.consul.io/intro/getting-started/join.html) to setup a basic Consul cluster
- Setup Jenkins' configuration with Consul's Host URL, Test URI
- From the Consul UI, create test key named, "sentinel" with value "canary"
 * If you choose to create different key-value pair, adjust this Groovy library accordingly

#### Usage
- Consul can be used to store critical piece of info about each build
- Jenkins can retrieve important properties from Consul to be used as input for builds
- Plugin does not offer service discovery capabilities but this can be overcome or achieved via external scripts.
