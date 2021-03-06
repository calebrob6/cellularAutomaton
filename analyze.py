#!/usr/bin/env python

import sys, os, time

import numpy as np
 
def main():
    start = time.time()
    
    
    
    baseDir = sys.argv[1]
    outFile = sys.argv[2]

    f=open(outFile,"w")
    
    for file in os.listdir(baseDir):
        
        if "results" in file:
            test = np.genfromtxt(baseDir+file,int, delimiter = ",",usecols=(3,),invalid_raise=False)
            test = [i if i!=-1 else 10000 for i in test]
            f.write("%s,%f,%f\n" %(file.split(".")[0],np.mean(test),np.std(test)))
                        
    f.close()
    print("Time taken %f" %(time.time()-start))

if __name__ == "__main__":
    main()