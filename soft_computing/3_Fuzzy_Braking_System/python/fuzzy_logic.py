import matplotlib
import matplotlib.pyplot as plt
import matplotlib.patches as patches

def trimf(x, points):
    pointA = points[0]
    pointB = points[1]
    pointC = points[2]
    slopeAB = getSlope(pointA, 0, pointB, 1)
    slopeBC = getSlope(pointB, 1, pointC, 0)
    result = 0
    if x >= pointA and x <= pointB:
        result = slopeAB * x + getYIntercept(pointA, 0, pointB, 1)
    elif x >= pointB and x <= pointC:
        result = slopeBC * x + getYIntercept(pointB, 1, pointC, 0)
    # print(result,"TriResult")
    # plt.plot([pointA,pointB],[0,1], linewidth = 3)
    # plt.ylim(0,1) 
    # plt.xlabel('Property')
    # plt.ylabel('Membership Function')
    # plt.title('MAMDANI') 
    # plt.plot(points)
    
    return result


def trapmf(x, points):
    pointA = points[0]
    pointB = points[1]
    pointC = points[2]
    pointD = points[3]
    slopeAB = getSlope(pointA, 0, pointB, 1)
    slopeCD = getSlope(pointC, 1, pointD, 0)
    yInterceptAB = getYIntercept(pointA, 0, pointB, 1)
    yInterceptCD = getYIntercept(pointC, 1, pointD, 0)
    result = 0
    if x > pointA and x < pointB:
        result = slopeAB * x + yInterceptAB
    elif x >= pointB and x <= pointC:
        result = 1
    elif x > pointC and x < pointD:
        result = slopeCD * x + yInterceptCD
    
    # fig = plt.figure()
    # ax = fig.add_subplot()
    # ax.set_ylim([0, 10])
    # # Trapez
    # ax.add_patch(patches.Polygon([[pointA,0],[pointB,1],[pointC,1],[pointD,0]], fill=False,color='green'))
    # plt.show()
    # print(result,"TrapResult")
        
    return result


def getSlope(x1, y1, x2, y2):
    #Avoid zero division error of vertical line for shouldered trapmf
    try:
        slope = (y2 - y1) / (x2 - x1)
    except ZeroDivisionError:
        slope = 0
    return slope


def getYIntercept(x1, y1, x2, y2):
    m = getSlope(x1, y1, x2, y2)
    if y1 < y2:
        y = y2
        x = x2
    else:
        y = y1
        x = x1
    return y - m * x


def getTrimfPlots(start, end, points):
    plots = [0] * (abs(start) + abs(end))
    pointA = points[0]
    pointB = points[1]
    pointC = points[2]
    slopeAB = getSlope(pointA, 0, pointB, 1)
    slopeBC = getSlope(pointB, 1, pointC, 0)
    yInterceptAB = getYIntercept(pointA, 0, pointB, 1)
    yInterceptBC = getYIntercept(pointB, 1, pointC, 0)
    for i in range(pointA, pointB):
        plots[i] = slopeAB * i + yInterceptAB
    for i in range(pointB, pointC):
        plots[i] = slopeBC * i + yInterceptBC
    #print(plots,"TriPlots")
    
    return plots

def getTrapmfPlots(start, end, points, shoulder=None):
    plots = [0] * (abs(start) + abs(end))
    pointA = points[0]
    pointB = points[1]
    pointC = points[2]
    pointD = points[3]
    left = 0
    right = 0
    slopeAB = getSlope(pointA, 0, pointB, 1)
    slopeCD = getSlope(pointC, 1, pointD, 0)
    yInterceptAB = getYIntercept(pointA, 0, pointB, 1)
    yInterceptCD = getYIntercept(pointC, 1, pointD, 0)

    # if shoulder == "left":
    #     for i in range(start, pointA):
    #         plots[i] = 1
    # elif shoulder == "right":
    #     for i in range(pointD, end):
    #         plots[i] = 1
    for i in range(pointA, pointB):
        plots[i] = slopeAB * i + yInterceptAB
    for i in range(pointB, pointC):
        plots[i] = 1
    for i in range(pointC, pointD):
        plots[i] = slopeCD * i + yInterceptCD
    #print(plots,"TrapPlots")
    return plots

def getCentroid(aggregatedPlots):
    n = len(aggregatedPlots)
    xAxis = list(range(n))
    centroidNum = 0
    centroidDenum = 0
    for i in range(n):
        centroidNum += xAxis[i] * aggregatedPlots[i]
        centroidDenum += aggregatedPlots[i]
    return centroidNum / centroidDenum

def plotTrapmf(axis, points, label):
    axis.plot(points, [0, 1, 1, 0])
    axis.fill_between(points, [0, 1, 1, 0], label=label, alpha=0.5)

def plotTrimf(axis, points, label):
    axis.plot(points, [0, 1, 0])
    axis.fill_between(points, [0, 1, 0], label=label, alpha=0.5)

def plotX(axis, x, label):
    axis.plot([x, x], [-1, 2], label=label, linestyle='dashed')
