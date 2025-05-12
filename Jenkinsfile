pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub')
        DOCKER_IMAGE = 'glossimute/teedy'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Build') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/glossimute/Teedy.git']]
                )
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Building image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Upload image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Run containers') {
            steps {
                script {
                    // 停止并删除旧容器（防止端口冲突）
            def ports = [8082, 8083, 8084]
            for (p in ports) {
                def name = "teedy-container-${p}"
                sh "docker stop ${name} || true"
                sh "docker rm ${name} || true"
            }

            // 启动新的三个容器
            for (p in ports) {
                def name = "teedy-container-${p}"
                sh """
                    docker run -d --name ${name} -p ${p}:8080 ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}
                """
            }

            // 查看容器状态
            sh 'docker ps --filter "name=teedy-container"'
                }
            }
        }
    }
}
