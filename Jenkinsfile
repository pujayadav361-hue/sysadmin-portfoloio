pipeline {

    agent any
     
    
    environment {
        APP_SERVER = '18.61.227.160'
        APP_USER = 'ec2-user'
        JAR_NAME = 'demo-0.0.1-SNAPSHOT.jar'
        buildNumber = "${BUILD_NUMBER}"
    }
    stages {

        stage("Clone git") {
           steps {
               git branch: 'main', url: 'git@github.com:pujayadav361-hue/sysadmin-portfoloio.git'
            }
        }      
     }
            
        stage('build artifact') {
          steps {
          
             sh 'mvn clean package'
          }
        }
    
        stage('install docker') {
            steps {
                sh '''
                 sudo yum install docker -y
                 sudo systemctl enable docker
                 sudo systemctl start docker
                '''
            }
        }
         stage('Build docker image') {
          steps {
                sh 'docker build -t systemadmin-portfolio/demoapp:${buildnumber} .'
             }
          }  
          stage('Authenticate and push Image to Docker Hub') {
           steps() {
               withCredentials([string(credentialsId: 'pooja846', variable: 'Docker_hub_password')])
               {
                sh 'docker login -u pooja846 -p ${Docker_hub_password}'
                sh 'docker push systemadmin-portfolio/demoapp:${buildnumber}'
               }
           }
           stage("Deploy Application to target server") {
             steps()
             {
                
                 sh "ssh -o StrictHostKeyChecking=no ${APP_USER}@${APP_SERVER} docker run -d --name demoapp-container -p 8081:8080 systemadmin-portfolio/demoapp:${buildnumber}"
             }
          }
            
    post{
        always{
            echo "========always========"
        }
        success{
            echo "========pipeline executed successfully ========"
        }
        failure{
            echo "========pipeline execution failed========"
        }
    }
  }
}
