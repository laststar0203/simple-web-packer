NSTALL_LIST=""
LICENSE_KEY=""
ENGINEER_AUTH_SERVERIP=""


function run {

	local PROPERTY_FILE="./packer.properties"
	
	if [ ! -e ${PROPERTY_FILE} ];then
		echo Packer 설정 파일이 존해하지 않습니다!!
		return
	fi


	for TEXT in $(cat packer.properties);do
			local KEY="${TEXT%%=*}"
			local VALUE="${TEXT#*=}"
					
			case $KEY in
				"installList")
					INSTALL_LIST=$VALUE
				;;
				"licenseKey")
					LICENSE_KEY=$VALUE
				;;
				"engineer-auth-serverIP")
					ENGINEER_AUTH_SERVERIP=$VALUE
				;;
			esac
	done
	
	
	if [ -z ${INSTALL_LIST} ] || [ -z ${#LICENSE_KEY} ] || [ -z ${ENGINEER_AUTH_SERVERIP} ];then
		return
	fi
	
	java -jar -Dspring.profiles.active=default packer-0.0.1-SNAPSHOT.jar --packer-property.installList="${INSTALL_LIST}" --packer-property.licenseKey="${LICENSE_KEY}" --packer-property.engineer-auth-serverIP="${ENGINEER_AUTH_SERVERIP}"

}

run


