export GOPATH=$SOURCE/daemon/
echo $GOPATH
cd $GOPATH/src
go build -o monitor.daemon
mv monitor.daemon ../../bin/ 
