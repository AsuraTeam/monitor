package hostUtil

import (
	"runtime"
	"strconv"
)

func GetCpuNumber()string{
	c := runtime.NumCPU()
	return strconv.Itoa(c)
}

