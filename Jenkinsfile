pipeline {
  agent any
    environment {
        MAIN_BRANCH = 'main'
        UAT_BRANCH = 'UAT'
        PRD_BRANCH_PREFIX = 'Release_3.'
        GIT_URL = 'https://github.com/yalbordy/S2DAOExam.git'
        AWS_REGION = 'ap-northeast-1'
        S3_BUCKET = 'tcs-file-share'
        S3_FOLDER = 'devops/test'
    }
    
    tools {
        maven 'maven3' 
    }
  stages {
    stage('Build Development Release') {
        when {
            anyOf {
                branch UAT_BRANCH
                branch MAIN_BRANCH
            }
        }
        steps {
            script {
                sh 'mvn clean package -Dmaven.test.skip=true'

                dir("${PRD_BRANCH_PREFIX}prd") {
                  git branch: "${MAIN_BRANCH}",
                      credentialsId: 'github-repo-jenkins',
                      url: "${GIT_URL}"

                  withCredentials([gitUsernamePassword(credentialsId: 'github-repo-jenkins', gitToolName:'Default')]) {
                    def check = sh(script: "git branch --list ${PRD_BRANCH_PREFIX}prd", returnStdout: true)
                    println check
                    if (check == null) {
                      sh "git checkout -b ${PRD_BRANCH_PREFIX}prd ${MAIN_BRANCH}"
                      // Push the new branch to remote repository
                      sh "git push --set-upstream origin ${PRD_BRANCH_PREFIX}prd"
                    }
                  }
                }
            }
        }
    }
    stage('Build Production Release') {
        when {
            expression {
                return env.BRANCH_NAME.startsWith(env.PRD_BRANCH_PREFIX)
            }
        }
        steps {
            script {
                deleteDir()
                git branch: "${UAT_BRANCH}",
                    credentialsId: 'github-repo-jenkins',
                    url: "${GIT_URL}"
                withCredentials([gitUsernamePassword(credentialsId: 'github-repo-jenkins', gitToolName:'Default')]) {

                // Determine the latest fix branch
                def maxReleaseBranch = getMaxReleaseBranch()
                // Increment the latest fix branch
                def newFixBranch = incrementFixBranch(maxReleaseBranch)
                println newFixBranch
                // Create a new branch from main
                sh "git checkout -b ${newFixBranch} ${UAT_BRANCH}"
                
                // Push the new branch to remote repository
                sh "git push --set-upstream origin ${newFixBranch}"
                
                // Build the production release
                sh 'mvn clean package -Dmaven.test.skip=true'

            }}
        }
    }
    // stage('Scan') {
    //   steps {
    //     script {
    //       sh "export PATH=$JAVA_HOME/bin:$PATH"
    //       def scannerHome = tool 'sonar'
    //       withSonarQubeEnv('Hello-World') {
    //         sh "${scannerHome}/bin/sonar-scanner \
    //         -Dsonar.projectKey=Hello-World  \
    //         -Dsonar.projectName=\"Hello-World\" \
    //         -Dsonar.language=java  \
    //         -Dsonar.java.binaries=**/target/classes  \
    //         -Dsonar.sources=core/src,web/src"
    //       }
    //     }
    //   }
    // }
    stage('Upload') {
      steps {
        script{
          def tmpfolder = "s3upload"
          sh "rm -Rf ${tmpfolder} && mkdir -p ${tmpfolder}"
          if ("${MAIN_BRANCH}" == "${BRANCH_NAME}") {
            sh "cp -f web/target/*.war ${tmpfolder}/pd3dev1.war && cp -f core/target/*.jar ${tmpfolder}/pd3dev1.jar"
            //sh "cp -f web/target/*.war ${tmpfolder}/pd3dev2.war && cp -f core/target/*.jar ${tmpfolder}/pd3dev2.jar"
          } else if ("${PRD_BRANCH_PREFIX}prd" == "${BRANCH_NAME}") {
            sh "cp -f web/target/*.war ${tmpfolder}/p3prd.war && cp -f core/target/*.jar ${tmpfolder}/p3prd.jar"
          } else {
            sh "cp -f web/target/*.war ${tmpfolder}/ph3stgtr1.war && cp -f core/target/*.jar ${tmpfolder}/ph3stgtr1.jar"
            //sh "cp -f web/target/*.war ${tmpfolder}/ph3stgtr2.war && cp -f core/target/*.jar ${tmpfolder}/ph3stgtr2.jar"
          }
          withAWS(region:"${AWS_REGION}",credentials:'aws-credentials') {
            def identity=awsIdentity();
            s3Upload(bucket:"${S3_BUCKET}", path:"${S3_FOLDER}", workingDir:"${tmpfolder}", includePathPattern:'**/*')
          }
        }
      }
    }
    stage('Deploy') {
        when {
            anyOf {
                branch UAT_BRANCH
                branch MAIN_BRANCH
            }
        }
      steps {
        script{
          deleteDir()
              git branch: "${MAIN_BRANCH}",
                  credentialsId: 'github-repo-jenkins',
                  url: "${GIT_URL}"
              withCredentials([gitUsernamePassword(credentialsId: 'github-repo-jenkins', gitToolName:'Default')]) {
                  sh 'mvn clean package -Dmaven.test.skip=true'
                  deploy adapters: [tomcat9(credentialsId: 'tomcat-manager', path: '', url: 'http://tomcat:8080')], contextPath: null, war: '**/*.war'
            }
        }
      }
    }
  }

}

def getMaxReleaseBranch() {
    def branches = sh(script: 'git branch -a', returnStdout: true).trim().tokenize("\n")
    def ret = 0
    for(branch in branches){
      if (branch.trim().startsWith("remotes/origin/${PRD_BRANCH_PREFIX}")) {
        def numericPart = branch.substring("remotes/origin/${PRD_BRANCH_PREFIX}".length()+1).tokenize('.')[0]
        if (numericPart == "prd") {
          // do nothing
        } else {
          numericPart = numericPart as int
          if (numericPart > ret) {
        	  ret = numericPart
          }
        }
      }
  	}
    return ret
}

def incrementFixBranch(branchNumber) {
    return "${env.PRD_BRANCH_PREFIX}${(branchNumber as int) + 1}.x"
}
