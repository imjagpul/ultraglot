
"""
Skript na upravu testu z progamu Moje slovicka z english-online.cz do
TESTv1 formatu.
"""

import re
import sys

if(len(sys.argv)>4):
           inname=sys.argv[3]
           outname=sys.argv[4]
else:
           inname="lesson"
           outname="lekce"

#nutno zmenit nazev, aby vystupni soubor byl jiny, nez vstupni
out=open(re.sub(inname, outname, sys.argv[1]), "w+");
#out=open("aha", "w+");


if(len(sys.argv)>2):
           nadpis=sys.argv[2];
else:
           nadpis="Headway - intermediate"

out.write("TESTv1"+nadpis+" - "+sys.argv[1]+"\n")

for line in sys.stdin:

           line=re.sub(r"^([^|]+)\|([^|]+).*?$", "\\2\n\\1", line);

           line=re.sub(r"\+", ", ", line); #plusy za carky
           #line=re.sub(r"\s+", r" ", line); #remove double whitespace
           line=re.sub(r"^\s*(.*?)\s*$", r"\1", line); #trim whitespace

           out.write(line+"\n")

