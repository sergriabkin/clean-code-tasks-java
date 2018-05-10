node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a Gerrit repository
        def changeBranch = "change-${GERRIT_CHANGE_NUMBER}-${GERRIT_PATCHSET_NUMBER}"
        checkout([$class                           : 'GitSCM', branches: [[name: "${changeBranch}"]],
                  doGenerateSubmoduleConfigurations: false, extensions: [],
                  submoduleCfg                     : [],
                  userRemoteConfigs                : [[refspec: "${GERRIT_REFSPEC}:${changeBranch}",
                                                       url    : "${BUILD_PROJECT_URL}"]]])      
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'maven3'
   }
   stage('Build') {
	  // Run the maven build
	 sh "'${mvnHome}/bin/mvn' clean package"
	 sh "'${mvnHome}/bin/mvn' sonar:sonar -Dsonar.host.url=${SONAR_URL} "
	 sh "'${mvnHome}/bin/mvn' sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.analysis.mode=preview -Dsonar.report.export.path=sonar-report.json"
	 sonarToGerrit  category: 'Code-Analysis',
		issuesScore: '-1',  noIssuesScore: '+1',
		postScore: true,
		severity:"MINOR",
		sonarURL: "${SONAR_EXT_URL}",
		subJobConfigs: [[projectPath: '', sonarReportPath: 'target/sonar/sonar-report.json']]

   }
}