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
                // Run the Maven build and tests
                sh 'mvn clean test'
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