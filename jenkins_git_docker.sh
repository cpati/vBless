app=$1
file=`echo $1|awk -F '/' '{print $2}'`
if [ ! -d $file ]
then
    echo https://github.com/cpati/$file.git "cloned"
    git clone https://github.com/cpati/$file.git
else
    echo https://github.com/cpati/$file.git "pulled"
    git pull https://github.com/cpati/$file.git
fi

mvn install dockerfile:build
echo "app build completed"
echo docker ps|grep $app|awk '{print $1}';
dockerProcess=`docker ps|grep $app|awk '{print $1}'`;
if [ -n "$dockerProcess" ]
then
docker stop $dockerProcess	
echo "docker process "$dockerProcess" stopped"
fi

docker run -p 5050:8080 -t $app &>/dev/null &
echo "docker run of "$app" completed"

