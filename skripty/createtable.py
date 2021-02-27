#!/bin/python
"""
Tento skript konvertuje vstup na html tabulku
(kazdy radek vstupu je rozdelen podle regex na bunky jednoho radku html tabulky).

param1 - vstupni soubor
param2 - vystupni soubor
param3 - regex na split
"""

import sys
import re

pattern=r"\W+";
if len(sys.argv)>1:
           sys.stdin=open(sys.argv[1], "r")
if len(sys.argv)>2:
           sys.stdout=open(sys.argv[2], "w")
if len(sys.argv)>3:
           pattern=sys.argv[3]

#regulerni vyraz
p = re.compile(pattern)

print "<table>"
for line in sys.stdin:
           row = p.split(line)
           sys.stdout.write("<tr>")
           for cell in row:
                      sys.stdout.write("<td>"+cell+"</td>")
           sys.stdout.write("</tr>\n")
print "</table>"
