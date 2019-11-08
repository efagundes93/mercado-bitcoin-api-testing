pipeline {
    agent none
    environment {
    
        MAVEN_ARTIFACT_VERSION = ""
        MAVEN_ARTIFACT_ID = ""
        MAVEN_ARTIFACT_PACKING = ""
        MAVEN_POM_FILE = "pom.xml"
        MAVEN_TARGET_DIR = "target"
        TESTS_PACKAGE_NAME = "backend-tests"

    }
    stages {
        stage ('Continuous Integration') {
            agent {
                node {
                    label 'jenkins-slave-build'
                }
            }
            steps {
                script {
                    configFileProvider([configFile(fileId: 'maven-global-settings-credential',
                            targetLocation: '.',
                            variable: 'MAVEN_SETTINGS')]) {
                        echo "run maven"
                        sh name: 'Run Maven compile and test',
                                script: "mvn test --global-settings $MAVEN_SETTINGS -U"
                    }
                }
            }
            post {
                failure {
                    emailext (
                            subject: "Esteira $CI_PROJECT_NAME - $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION",
                            body: "Build $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION falhou! Log disponível em ${env.BUILD_URL}",
                            to: "$MAIL_TO_CI_FAILURE")
                }
            }
        }
        stage ('Release Candidate') {
            agent {
                node {
                    label 'jenkins-slave-build'
                }
            }
            environment {
                GITUSER = credentials('git-credential')
            }
            steps {
                script {
                    configFileProvider([configFile(fileId: 'maven-global-settings-credential',
                            targetLocation: '.',
                            variable: 'MAVEN_SETTINGS')]) {

                        sh name: 'Checkout Git develop branch',
                                script: 'git checkout -b develop remotes/origin/develop'

                        pom = readMavenPom file: "$MAVEN_POM_FILE", name: 'Read Maven POM file'

                        MAVEN_ARTIFACT_VERSION = "${pom.version}"
                        MAVEN_ARTIFACT_ID = "${pom.artifactId}"
                        MAVEN_ARTIFACT_PACKING = "${pom.packaging}"

                        GIT_URL = sh(returnStdout: true, script: 'git config remote.origin.url | cut -d "@" -f 2 | sed "s/^/@/" | tr -d \'\\n\'')

                        CI_PROJECT_NAME = sh (returnStdout: true, script: "echo ${env.JOB_NAME} | cut -d \"/\" -f 1 | tr -d \'\\n\'")
                        CI_TECNOLOGY_NAME = sh (returnStdout: true, script: "echo ${env.JOB_NAME} | cut -d \"/\" -f 3 | tr -d \'\\n\'")

                        sh name: "Run Maven package to ${pom.version}",
                                script: "mvn package --global-settings $MAVEN_SETTINGS -U"

                        sh name: 'Set remote origin url',
                                script: "git config remote.origin.url https://'${GITUSER_USR}:${GITUSER_PSW}'${BITBUCKET_URL}"

                        sh 'git config --local user.name "Dev Ops"'

                        echo "Create local Git tag for ${pom.version}"
                        sh name: "", script: "git tag -a ${pom.version} -m \"versão ${pom.version} gerada\""

                        echo "Push local tag to Bitbucket"
                        sh name: '', script: "git push origin ${pom.version}"

                        def version = pom.version.toString().split("\\.")
                        version[-1] = version[-1].toInteger()+1
                        pom.version = version.join('.')
                        echo "Incremented POM project version to ${pom.version}"

                        writeMavenPom model: pom, file: "$MAVEN_POM_FILE", name: 'Write Maven POM file'

                        echo "Add POM file to Git"
                        sh name: '', script: "git add $MAVEN_POM_FILE"

                        echo "Commit POM file to Git"
                        sh name: '', script: 'git commit -m "Versão do POM incrementada"'

                        echo 'Push local changes to Bitbucket'
                        sh name: '', script: "git push https://'${GITUSER_USR}:${GITUSER_PSW}'${BITBUCKET_URL}"
                        sh name: '', script: "git push origin develop"

                        sh name: 'Config Jfrog cli',
                                script: "/usr/bin/jfrog rt c --url $ARTIFACTORY_URL --user ${ARTIFACTORY_CREDENTIAL_USR} --password '${ARTIFACTORY_CREDENTIAL_PSW}' '$ARTIFACTORY_SERVER_ID'"

                        sh name: 'Upload to Artifactory staging project',
                                script: "/usr/bin/jfrog rt u --url $ARTIFACTORY_URL --user ${ARTIFACTORY_CREDENTIAL_USR} --password '${ARTIFACTORY_CREDENTIAL_PSW}' --server-id '$ARTIFACTORY_SERVER_ID' $ARTIFACTORY_SOURCE_PATH $ARTIFACTORY_TARGET_PATH_CI"
                    }
                }
            }
            post {
                failure {
                    emailext (
                            subject: "Esteira $CI_PROJECT_NAME - $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION",
                            body: "Build $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION falhou! Log disponível em ${env.BUILD_URL}",
                            to: "$MAIL_TO_CI_FAILURE")
                }
            }
        }
        stage ('Deploy DES') {
            agent {
                node {
                    label 'master||jenkins-slave-deploy'
                }
            }
            options {
                skipDefaultCheckout true
            }
            steps {
                build job: "$CI_PROJECT_NAME/deploy/des/$CI_TECNOLOGY_NAME/$CI_PROJECT_NAME-deploy-des-$CI_TECNOLOGY_NAME", parameters: [
                        string(name: "JAVA_EXTRA_VARS", value: "$JAVA_EXTRA_VARS_DES"),
                        string(name: "APP_EXTRA_VARS", value: "$DEFAULT_APP_EXTRA_VARS_DES"),
                        string(name: "DEPLOY_APP_NAME", value: "$MAVEN_ARTIFACT_ID")
                ]
            }
            post {
                failure {
                    emailext (
                            subject: "Esteira $CI_PROJECT_NAME - $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION",
                            body: "Deploy $MAVEN_ARTIFACT_ID - $MAVEN_ARTIFACT_VERSION em DES falhou! Log disponível em ${env.BUILD_URL}",
                            to: "$MAIL_TO_CI_FAILURE")
                }
            }
        }
    }
}
