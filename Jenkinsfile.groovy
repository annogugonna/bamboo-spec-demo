pipeline{
    agent 'Agent1'
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
    }
}       
