pipeline{
    agent {
        label 'Agent1'
    }    
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
        stage('Archive Artifacts'){
            steps {
                archiveArtifacts artifacts: '/home/jenkins-agent/workspace/xmc-checking.txt.tgz', onlyIfSuccessful: true
                cleanWs cleanWhenAborted: false, cleanWhenFailure: false, cleanWhenNotBuilt: false, cleanWhenUnstable: false
            }
        }
    }
}       
