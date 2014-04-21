#!/usr/bin/env python

import sys,os
from collections import defaultdict

def main():
    
    resultsDir = "workspace/"
    
    results = defaultdict(list)
    
    for file in os.listdir(resultsDir):
        if "gaResults" in file:
            fnParts = file.split("_")
            f = open(resultsDir+file,"r")
            fitness = float(f.readline().strip())
            ruleString = f.readline().strip()
            f.close()
            
            
            
            results[fnParts[0]+"_"+fnParts[1]+"_"+fnParts[2]].append((fitness,ruleString))
    
    freq = defaultdict(int)
    for k,v in results.items():
        print k
        bestToWorst = sorted(v, key=lambda x:x[0], reverse=True)
        for i,j in enumerate(bestToWorst):
            print "\t",i,j
        average = sum([i[0] for i in v])/float(len(v))
        print "\tAverage: ",average
        #freq = defaultdict(int)
        for i in v:
            nums = map(int,i[1].split(";"))
            for j in nums:
                freq[j]+=1

    f=open("test.csv","w")
    for i in range(max(freq)+1):
        f.write(str(i)+",")
    f.write("\n")
    for i in range(max(freq)+1):
        f.write(str(freq[i])+",")
    f.write("\n")
    f.close()
    
    return 

if __name__ == "__main__":
    main()