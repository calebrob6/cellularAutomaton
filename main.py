#!/usr/bin/env python

import time
import numpy as np
import rules
import sys,os
import random
import cv2

class Rules():

    def __init__(self):
        
        self.configurations = ((0,0,0,0),(1,0,0,0),(0,1,0,0),(1,1,0,0),(0,0,1,0),(1,0,1,0),(0,1,1,0),(1,1,1,0),(0,0,0,1),(1,0,0,1),(0,1,0,1),(1,1,0,1),(0,0,1,1),(1,0,1,1),(0,1,1,1),(1,1,1,1))
        self.configIdxToRule = dict()
        self.configRuleToIdx = dict()
        self.rules = []

        for i in range(len(self.configurations)):
            self.configIdxToRule[i] = self.configurations[i]
            self.configRuleToIdx[self.configurations[i]] = i
        
    def setRules(self, ruleString):
        self.rules = []
        rules = map(int,ruleString.split(";"))
        for rule in rules:
            self.rules.append(rule)

    def reverseRules(self):

        newRules = [0 for i in self.rules]

        for i in range(len(self.rules)):
            newRules[self.rules[i]] = i

        self.rules = newRules

def main():

    start = time.time()

    width = 200
    height = 200

    currentFrame = 0
    currentOffset = 0
    frameAbs = 0

    stopFlickering = False
    backwards = False
    
    board = np.zeros((height*width))
    
    rules = Rules()
    rules.setRules("15;14;13;3;11;5;6;1;7;9;10;2;12;4;8;0")


    randomNum = 100
    for i in range(randomNum):
        x = random.randrange(0,width)
        y = random.randrange(0,height)
        board[((width * ((y)%height)) + ((x)%width))] = 255

    #cv2.imshow("test",board)
    #if cv2.waitKey(1) & 0xFF == ord('q'):
    #    pass

    while currentFrame<100:
        print currentFrame
        delta = time.time()

        for i in range(0,height,2):
            for j in range(0,width,2):
                x = j+currentOffset
                y = i+currentOffset

                a1 = board[((width * ((y)%height)) + ((x)%width))] == 255
                a2 = board[((width * ((y)%height)) + ((x+1)%width))] == 255
                a3 = board[((width * ((y+1)%height)) + ((x)%width))] == 255
                a4 = board[((width * ((y+1)%height)) + ((x+1)%width))] == 255

                '''
                cellIndex = rules.configRuleToIdx[(a1,a2,a3,a4)]

                transitionIndex = rules.rules[cellIndex]
                transitionConfig = rules.configIdxToRule[transitionIndex]

                board[y%height,x%width] = 255;
                board[y%height,(x+1)%width] = 255;
                board[(y+1)%height,x%width] = 255;
                board[(y+1)%height,(x+1)%width] = 255;
                '''
        #end board update
        print time.time() - delta
                
        if stopFlickering:
            if abs(currentFrame%2)==1:
                #cv2.imshow("test",board)
                #if cv2.waitKey(1) & 0xFF == ord('q'):
                #    break
                pass
        else:
            #cv2.imshow("test",board)
            #if cv2.waitKey(1) & 0xFF == ord('q'):
            #    break
            pass


        if backwards:
            currentFrame-=1
        else:
            currentFrame+=1
        frameAbs+=1

        currentOffset = not currentOffset
        print time.time() - delta
        
    print("Time taken: %f seconds" % (time.time() - start))


if __name__ == "__main__":
    main()
