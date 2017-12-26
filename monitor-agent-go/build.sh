export SOURCE=`pwd`
echo $SOURCE
sh -x src/build.sh
sh -x daemon/src/build.sh
