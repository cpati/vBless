pipeline {
    agent any
    
    parameters {
        string(name: 'app', defaultValue: 'vBlessImg/vBless', description: 'docker app name')
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                proId=`ps -ef | grep -i vbless.jar | grep -v grep | awk \'{print $2}\'`;
                if [ -n "$proId" ]
                then
                kill $proId;
                fi
                java -jar /tmp/vBless/target/vBless.jar --spring.datasource.url=jdbc:mysql://easyfilemgrdb.c7fel01xnjwz.us-east-2.rds.amazonaws.com:3306/filemgr --spring.datasource.username=system --spring.datasource.password=managermysql &
                '''
            }
        }
    }
}
