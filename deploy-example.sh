#!/bin/bash

export USERNAME=""
export PASSWORD=""

##################################################################################################
# DO NOT EDIT UNDER THIS LINE
##################################################################################################

printf "Action?\n\n"
printf "1 - Deploy Project (SNAPSHOT)\n\n"

read USER_CHOICE

if [ $USER_CHOICE -eq "1" ]
  then
    echo "Deploying resources to: SNAPSHOTS"
    COMMAND="mvn --settings ./.m2/settings.xml clean package deploy"
    $COMMAND
fi

unset USERNAME
unset PASSWORD

echo Done...