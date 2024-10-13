pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git branch: 'master', url: 'https://github.com/andreask-repo/bookstore-api-tests.git'
            }
        }

        stage('Build') {
        environment {
                JAVA_HOME = 'C:/Program Files/Java/jdk-17.0.3.1'
                M2_HOME = 'C:/Program Files/Apache Software Foundation/apache-maven-3.9.9/bin'
                PATH = "${env.M2_HOME}/bin:${env.PATH}"
            }
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
                    steps {

                        sh 'mvn test'
                    }
                }

        stage('Test Reports') {
            steps {
                // Publish JUnit reports
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Publish HTML Report') {
            steps {
                // Archive and publish ExtentReports (HTML)
                publishHTML (target: [
                    reportDir: 'target/extentReport',
                    reportFiles: 'extentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            // Cleanup after the build
            cleanWs()
        }
    }
}