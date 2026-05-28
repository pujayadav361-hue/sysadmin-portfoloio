pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/pujayadav361-hue/sysadmin-portfoloio.git'

                // Run the build on a Unix agent. You must have Maven installed.
                sh 'mvn -Dmaven.test.failure.ignore=true clean package'

                // To run Maven on a Windows agent, use
                // bat 'mvn -Dmaven.test.failure.ignore=true clean package'
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/Report/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}

