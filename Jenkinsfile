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