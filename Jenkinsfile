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
        stage('terraform') {
          environment {
            TERRAFORM_HOME = tool name: 'terraform-0.11.3'

            ARM_SUBSCRIPTION_ID = credentials('a51e1de6-e333-435b-97aa-0fb352f8a7a5')
            ARM_CLIENT_ID = credentials('938a024c-3c38-4dc6-a535-ec6950612208')
            ARM_CLIENT_SECRET = credentials('8785ad8b-b033-4b46-adc7-ba63ff2b78a0')

            ARM_TENANT_ID = credentials('424beb43-e108-4465-aa39-589546aa1b6f')

            ARM_ENVIRONMENT = "public"

            TF_VAR_user = credentials('0f7e1be8-c14c-4c7b-b4e5-e8ca4aeaadbb')
            TF_VAR_password = credentials('052d4621-f190-4b26-8922-a12041afb77e')

            TF_VAR_build_id = "${env.BUILD_ID}"

            TF_VAR_azurerm_resource_group_name = credentials('115e964f-fe0f-4bff-bf51-906dbb9c5a61')
            TF_VAR_azurerm_storage_account_name = credentials('2d6b68d3-224f-40e6-90bc-08986310e0ae')
            TF_VAR_azurerm_container_name = credentials('608523f7-2a13-4207-91a7-2a0bc252ae53')
            TF_VAR_azurerm_key = credentials('6ba358ff-35a4-41b2-91ac-406dd70e9d4f')

            TF_VAR_resource_azurerm_resource_group_name = credentials('33f23708-4533-43b5-9bb6-b41f05247bb6')
          }
          steps {
            wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'xterm']) {
              dir('terraform') {
                script {
                  sh "${TERRAFORM_HOME}/terraform init -input=false"
                  def TF_APPLY_STATUS = sh (script: "${TERRAFORM_HOME}/terraform plan -out=tfplan -detailed-exitcode -input=false", returnStatus: true)
                  if ( TF_APPLY_STATUS == 2 ) {
                    sh "${TERRAFORM_HOME}/terraform apply -input=false -auto-approve tfplan"
                  }
              }
              }
            }
          }
        }
  }
}