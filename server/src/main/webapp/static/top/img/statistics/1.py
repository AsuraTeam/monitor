#!/bin/python
#

import os
d = os.listdir("./")
for i in d:
  print "insert into monitor_top_images(name) values('%s');"%(i)
