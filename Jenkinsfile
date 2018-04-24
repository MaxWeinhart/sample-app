pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        withMaven(jdk: '1.8', maven: '3.5.2') {
          sh '''#/bin/bash

mvn package'''
        }

      }
    }
    stage('finished message') {
      steps {
        echo 'Finished!'
      }
    }
    stage('packer'){
        environment {
            PACKER_HOME = tool name: 'packer-1.1.3', type: 'biz.neustar.jenkins.plugins.packer.PackerInstallation'

            PACKER_IMAGE_PREFIX="mweinhar"

            PACKER_SUBSCRIPTION_ID = credentials('a51e1de6-e333-435b-97aa-0fb352f8a7a5')
            PACKER_CLIENT_ID = credentials('938a024c-3c38-4dc6-a535-ec6950612208')
            PACKER_CLIENT_SECRET = credentials('8785ad8b-b033-4b46-adc7-ba63ff2b78a0')

            PACKER_LOCATION="westeurope"

            PACKER_TENANT_ID = credentials('424beb43-e108-4465-aa39-589546aa1b6f')
            PACKER_OBJECT_ID = credentials('6546917f-de67-40e8-8bd3-f85d786bfd86')
        }
        steps {
            sh "${PACKER_HOME}/packer -v"
            sh "${PACKER_HOME}/packer validate packer/packerLinuxImage.json"
            sh "${PACKER_HOME}/packer build packer/packerLinuxImage.json"
        }
    }
  }
}