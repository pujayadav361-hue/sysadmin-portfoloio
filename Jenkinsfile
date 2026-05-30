pipeline {
  
 agent {

        label 'Jenkins'
    }
       
 

    environment {
        APP_SERVER = '18.61.227.160'
        APP_USER   = 'ec2-user'
        JAR_NAME   = 'demo-0.0.1-SNAPSHOT.jar'
        buildNumber = "${BUILD_NUMBER}"
    }

    stages {
        stage("Clone git") {
            steps {
                git branch: 'main', url: 'git@github.com:pujayadav361-hue/sysadmin-portfoloio.git'
            }
        }

        stage('Build Artifact') {
            steps {
                sh 'mvn clean package'
            }
        }
      
        stage('Create docker image and push to docker hub') {
             steps {
                 withCredentials([string(credentialsId: 'pooja846', variable: 'Docker_hub_password')]) {
                 sh 'sudo docker build -t systemadmin-portfolio/demoapp:${buildNumber} .'
             }
        }
}
        
        stage('Push docker image') {
            steps {
                withCredentials([string(credentialsId: 'pooja846', variable: 'Docker_hub_password')]) {
                    sh 'sudo docker login -u pooja846 -p ${Docker_hub_password}'
                    sh 'sudo docker tag systemadmin-portfolio/demoapp:${buildNumber} pooja846/demoapp:${buildNumber}'
                    sh 'sudo docker push pooja846/demoapp:${buildNumber}'
                 }
             }
        }
        
        stage("Deploy to App Server") {
            steps {
                sh """
                    ssh -o StrictHostKeyChecking=no ${APP_USER}@${APP_SERVER} '
                        sudo yum install -y docker &&
                        sudo systemctl enable docker &&
                        sudo systemctl start docker &&
                        sudo docker rm -f demoapp-container || true && 
                        sudo docker ps -a 
                    '
                """
            }
        }


  
       stage("Pull docker image from server and start container") {
            steps { 
                  withCredentials([string(credentialsId: 'pooja846', variable: 'Docker_hub_password')]) {
                         sh 'sudo docker pull pooja846/demoapp:${buildNumer}' 
                         sh 'sudo docker run -d --name demoapp-container -p 8081:8080 pooja846/demoapp:${buildNumer}'
                  """
             }
      }
 }


    post {
        always {
            echo "========always========"
        }
        success {
            echo "========pipeline executed successfully ========"
        }
        failure {
            echo "========pipeline execution failed========"
        }
    }
}

