package godaemon

import (
"flag"
"fmt"
"os"
"os/exec"
)

var godaemon = flag.Bool("d", false, "run app as a daemon with -d=true or -d true.")

func init() {
	if !flag.Parsed() {
		flag.Parse()
	}

	if *godaemon {
		cmd := exec.Command(os.Args[0], flag.Args()[1:]...)
		cmd.Start()
		fmt.Printf("%s [PID] %d running...\n", os.Args[0], cmd.Process.Pid)
		*godaemon = false
		os.Exit(0)
	}
}
