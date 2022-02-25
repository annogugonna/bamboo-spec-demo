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
                ansiblePlaybook credentialsId: 'keys', disableHostKeyChecking: true, installation: 'Ansible', inventory: 'inventory.txt', playbook: 'Build.yml', tags: 'oncast'
            }
        }
        stage('Tar image'){
            steps{
                sh "docker save hugoboss192/hariapp:dockertag --output cmx-image.tar"
            }
        }
        stage('Archive Artifacts'){
            steps {
                archiveArtifacts artifacts: 'cmx-image.tar', onlyIfSuccessful: true
                cleanWs cleanWhenAborted: false, cleanWhenFailure: false, cleanWhenNotBuilt: false, cleanWhenUnstable: false
            }
        }
    }
}       
