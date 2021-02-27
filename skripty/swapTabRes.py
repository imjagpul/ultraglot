#!/bin/python
"""
Tento skript, konvertuje soubor ve tvaru
vyraz1\tvyraz2[\t.*]
vyraz3\tvyraz4[\t.*]
vyraz5\tvyraz6[\t.*]

na soubor ve tvaru

vyraz2\tvyraz1
vyraz4\tvyraz3
vyraz6\tvyraz5

(Slouzi k prehozeni smeru slovniku v res. Je nutno jeste seradit treba v PsPad.)

"""

import sys
import re

if len(sys.argv)>1:
           sys.stdin=open(sys.argv[1], "r")
if len(sys.argv)>2:
           sys.stdout=open(sys.argv[2], "w")


#regulerni vyraz
p = re.compile(r"^(.+?)\t(.+?)(\t.*)?$")

for line in sys.stdin:
           m = p.match(line)
           if m is None:
                      sys.stdout.write(line)
           else:
                      print m.group(2) + "\t" + m.group(1)
           
           

