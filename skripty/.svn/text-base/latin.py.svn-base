"""
Jednoucelovy skript na upravu latin testu.
"""

import re
import sys

for line in sys.stdin:

           #convert e.g. "dol&ecirc;re" -> "dolere (dol&ecirc;re)"
           line=re.sub(r"^((.*?)&ecirc;(.*))$", r"\2e\3 (\1)", line);
           
           line=re.sub(r"&nbsp;", "", line); #remove &nbsp;
           line=re.sub(r"\s+", r" ", line); #remove double whitespace
           line=re.sub(r"^\s*(.*?)\s*$", r"\1", line); #trim whitespace
           
           sys.stdout.write(line+"\n")

