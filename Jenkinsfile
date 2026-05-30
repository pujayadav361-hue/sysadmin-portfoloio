pipeline {
    agent { label 'Jenkins' }

    environment {
        APP_SERVER   = '18.61.227.160'
        APP_USER     = 'ec2-user'
        JAR_NAME     = 'demo-0.0.1-SNAPSHOT.jar'
        buildNumber  = "${BUILD_NUMBER}"
        DOCKER_IMAGE = "pooja846/demoapp"
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

        stage('Build Docker Image') {
            steps {
                sh  "sudo docker build -t ${DOCKER_IMAGE}:${buildNumber} ."
                sh "sudo docker tag ${DOCKER_IMAGE}:${buildNumber} ${DOCKER_IMAGE}:latest"
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'pooja846', variable: 'Docker_hub_password')]) {
                    sh "sudo docker login -u pooja846 -p ${Docker_hub_password}"
                    sh "sudo docker push ${DOCKER_IMAGE}:${buildNumber}"
                    sh "sudo docker push ${DOCKER_IMAGE}:latest"
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
                        sudo docker pull ${DOCKER_IMAGE}:latest &&
                        sudo docker run -d --name demoapp-container -p 8081:8080 ${DOCKER_IMAGE}:latest
                    '
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

