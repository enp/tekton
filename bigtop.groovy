#!/usr/bin/env groovy

if (args.length == 1) {
  def bom = new ConfigSlurper().parse(new URL("file:${args[0]}"))
  def pipeline = new groovy.yaml.YamlBuilder()
  pipeline {
    apiVersion 'tekton.dev/v1beta1'
    kind 'Pipeline'
    metadata {
      name bom.bigtop.project.name.toLowerCase()
    }
    spec {
      tasks(bom.bigtop.components.values()) { component ->
        name component.name
        taskRef {
          name 'example'
        }
      }
    }    
  }
  println pipeline.toString()
}
