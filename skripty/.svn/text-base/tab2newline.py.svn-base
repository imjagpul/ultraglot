#!/bin/python
"""
Tento skript, konvertuje test ve tvaru
otazka1 - odpoved1
otazka2 - odpoved3
otazka3 - odpoved4
na TESTv1 tvar.
"""

import sys
import re

if len(sys.argv)>1:
           sys.stdin=open(sys.argv[1], "r")
if len(sys.argv)>2:
           sys.stdout=open(sys.argv[2], "w")


#regulerni vyraz
p = re.compile(r"^(.+?)\s*\t\s*(.+?)$")

for line in sys.stdin:
           m = p.match(line)
           if m is None:
                      sys.stdout.write(line)
           else:
                      print m.group(1) + "\n" + m.group(2) + "\n"
           
           

