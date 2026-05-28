pipeline {
    agent any

    environment {
        APP_NAME = "demo-0.0.1-SNAPSHOT.jar"
        TARGET_SERVER = "18.60.39.70"
        TARGET_PATH = "/home/ec2-user/"
    }

    stages {

        stage('Clone Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/pujayadav361-hue/sysadmin-portfoloio.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy to Target Server') {
            steps {
                sh '''
                scp target/$APP_NAME ec2-user@$TARGET_SERVER:$TARGET_PATH

                ssh ec2-user@$TARGET_SERVER "
                    pkill java || true
                    nohup java -jar $TARGET_PATH/$APP_NAME > app.log 2>&1 &
                "
                '''
            }
        }
    }
}
