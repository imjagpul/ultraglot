import sys
import re

if len(sys.argv)>1:
           sys.stdin=open(sys.argv[1], "r")
if len(sys.argv)>2:
           sys.stdout=open(sys.argv[2], "w")


#co obsahuje radek, po kterem se zacne parsovat
start="<tr><td align=left>"

#co obsahuje radek, po kterem se skonci parsovani
end="<p><br><br><center><a href=../index.html><font face=verdana size=2><b>Main page</a>"

#for line in sys.stdin:
#           if line.find(start) is not -1:
#               break


p = re.compile(r"^(.+?)\s*\t\s*(.+?)$")

for line in sys.stdin:
           if line.find(end) is not -1:
               break
               

           #odstranit tagy
           line=re.sub(r"<[^>]+>", "", line)

           #splitnout podle slova pred dvojteckou

           trans=0
           
           #explode podle ;
           for x in re.split(r"([^;]+):", line):
               x=x.strip()
               if len(x) is 0:
                      continue
               
               if trans is 0:
                      trans=x

               else:
                      foreign=x

                      #vlastni se jeste rozdeluje podle : a ,
                      for t in re.split("[:,;/]", trans):
                                 t=t.strip()
                                 
                                 if len(t) is 0:
                                    continue

                                 for f in re.split("[;/]", foreign):
                                      f=f.strip()

                                      if len(f) is 0:
                                           continue

                                      print t + "\t" + f
                                      continue
                      trans=0
