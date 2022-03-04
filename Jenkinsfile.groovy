pipeline{
    agent any  
    stages{
        stage('SCM'){
            steps{
                echo 'SCM Checkout'
                git branch: 'main', url: 'https://github.com/annogugonna/ONCAST.git'
            }
        }
        stage('Fetch Build Repository'){
            steps{
                git branch: 'main', url: 'https://github.com/annogugonna/DEV.git'
            }
        }
        stage('Run Playbook'){
            steps{
                ansiblePlaybook credentialsId: 'keys', disableHostKeyChecking: true, installation: 'Ansible', inventory: 'inventory.txt', playbook: 'oncast-controller.yml', tags: 'oncast'
            }
        }
        stage('Archive Artifacts'){
            steps {
                archiveArtifacts artifacts: 'xmc-checking.txt.tgz', onlyIfSuccessful: true
            }
        }
    }
}       
