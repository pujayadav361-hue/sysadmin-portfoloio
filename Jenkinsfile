pipeline {

    agent any

    environment {
        APP_SERVER = '18.60.39.70'
        APP_USER = 'ec2-user'
        JAR_NAME = 'demo-0.0.1-SNAPSHOT.jar'
    }

    stages {

        stage('Clone Code') {
            steps {
                git branch: 'main',
                url: 'git@github.com:pujayadav361-hue/sysadmin-portfoloio.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Verify Build') {
            steps {
                sh 'ls -lrt target/'
            }
        }

        stage('Deploy To Target Server') {
            steps {
                sh """
                scp -o StrictHostKeyChecking=no target/${JAR_NAME} ${APP_USER}@${APP_SERVER}:/home/${APP_USER}/
                """
            }
        }

        stage('Restart Application') {
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${APP_USER}@${APP_SERVER} '

                pkill -f "${JAR_NAME}" || true

                nohup java -jar /home/${APP_USER}/${JAR_NAME} > app.log 2>&1 &

                sleep 10

                ps -ef | grep java
                '
                """
            }
        }

    }

    post {

        success {
            echo 'Application deployed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }

    }
}
