export GOPATH=$SOURCE
echo $GOPATH
cd $SOURCE/src
go build -o  monitor
mv monitor ../bin/
