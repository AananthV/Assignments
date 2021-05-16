from fuzzy_logic import *
import matplotlib.pyplot as plt

membership_functions = {
    'trapmf': {
        'fuzzify': trapmf,
        'plot': plotTrapmf,
        'points': getTrapmfPlots
    },
    'trimf': {
        'fuzzify': trimf,
        'plot': plotTrimf,
        'points': getTrimfPlots
    }
}

fuzzy_inputs = {
    'v': {
        'name': 'Speed',
        'unit': 'km/h',
        'mfs': [
            {
                'name': 'Slow',
                'mf': 'trapmf',
                'points': [0, 0, 20, 40]
            },
            {
                'name': 'Medium',
                'mf': 'trapmf',
                'points': [20, 40, 50, 70]
            },
            {
                'name': 'Fast',
                'mf': 'trapmf',
                'points': [50, 70, 100, 100]
            }
        ]
    },
    's': {
        'name': 'Distance',
        'unit': 'm',
        'mfs': [
            {
                'name': 'Short',
                'mf': 'trapmf',
                'points': [0, 0, 20, 50]
            },
            {
                'name': 'Medium',
                'mf': 'trimf',
                'points': [20, 60, 90]
            },
            {
                'name': 'Long',
                'mf': 'trapmf',
                'points': [60, 80, 100, 100]
            }
        ],
    },
    'f': {
        'name': 'Friction Factor',
        'unit': ' ',
        'mfs': [ # Friction
            {
                'name': 'Low',
                'mf': 'trapmf',
                'points': [0, 0, .2, .4]
            },
            {
                'name': 'Medium',
                'mf': 'trimf',
                'points': [.2, .5, .8]
            },
            {
                'name': 'High',
                'mf': 'trapmf',
                'points': [.6, .8, 1, 1]
            }
        ]
    }
}

fuzzy_output = { # Brake Strength
    'name': 'Brake Strength',
    'unit': ' ',
    'min': 0,
    'max': 100,
    'mfs': [
        {
            'name': 'Low',
            'mf': 'trapmf',
            'points': [0, 0, 20,40]
        },
        {
            'name': 'Medium',
            'mf': 'trapmf',
            'points': [30, 45, 55, 70]
        },
        {
            'name': 'High',
            'mf': 'trapmf',
            'points': [60, 80, 100,100]
        },
    ]
}

def main():
    speed = float(input('Enter Speed (1 - 100): '))
    distance = float(input('Enter Distance (1 - 100): '))
    frictionFactor = float(input('Enter Friction factor (0 - 1): '))

    # speed = 60
    # distance = 30
    # frictionFactor = 0.3

    rules = evaluateRules(speed, distance, frictionFactor)
    # outputMfs = {'vs': getVSPlots(), 's': getSPlots(), 'rs': getRSPlots(), 'm': getMPlots(),
    #              'rl': getRLPlots(), 'l': getLPlots(), 'vl': getVLPlots()
    #              }

    outputMfs = getOutputPoints()
    aggregatedPoints = fisAggregation(rules, outputMfs)
    centroid = getCentroid(aggregatedPoints)

    plotMFs([speed, distance, frictionFactor], centroid)
    plotAggregation(aggregatedPoints, centroid)
    plt.show()

    print("Output " + fuzzy_output['name'] + " is " + str(centroid) + " " + fuzzy_output['unit'])

def fuzzify(x, mfs):
    return [membership_functions[mf['mf']]['fuzzify'](x, mf['points']) for mf in mfs]

def getOutputPoints():
    return [
        membership_functions[mf['mf']]['points'](
            fuzzy_output['min'],
            fuzzy_output['max'],
            mf['points']
        ) for mf in fuzzy_output['mfs']
    ]

def plotMFs(inputs, output):
    figure, axes = plt.subplots(nrows=2, ncols=2)

    axes = axes.flatten()

    for i, fuzzy_input in enumerate(fuzzy_inputs.values()):
        for mf in fuzzy_input['mfs']:
            membership_functions[mf['mf']]['plot'](
                axes[i], 
                mf['points'],
                mf['name']
            )
        plotX(axes[i], inputs[i], "Input " + fuzzy_input['name'])
        axes[i].set_xlabel(fuzzy_input['unit'])
        axes[i].set_ylim([0.01, 1.1])
        axes[i].legend()
        axes[i].set_title("Input: " + fuzzy_input['name'])

    for mf in fuzzy_output['mfs']:
        membership_functions[mf['mf']]['plot'](
            axes[-1], 
            mf['points'],
            mf['name']
        )
    plotX(axes[-1], output, "Output " + fuzzy_output['name'])
    axes[-1].set_xlabel(fuzzy_output['unit'])
    axes[-1].set_ylim([0.01, 1.1])
    axes[-1].legend()
    axes[-1].set_title("Output: " + fuzzy_output['name'])

def plotAggregation(aggregatedPoints, centroid):
    figure, axis = plt.subplots(nrows=1, ncols=1)
    axis.plot([i + 1 for i in range(100)], aggregatedPoints)
    axis.fill_between([i + 1 for i in range(100)], aggregatedPoints, alpha=0.3)
    axis.set_title("Aggregated Output MFs")
    axis.set_ylim((-0.1, 1.1))
    axis.set_xlabel("Braking Strength")
    plotX(axis, centroid, "Centroid")
    axis.legend()

def fisAggregation(rules, outputMfs):
    aggregatePlots = [0] * 100
    for rule in range(len(rules)):
        for i in range(100):
            for j in range(len(outputMfs)):
                aggregatePlots[i] = max(aggregatePlots[i], min(rules[rule][j], outputMfs[j][i]))
    
    return aggregatePlots


def evaluateRules(speed, distance, frictionFactor):
    """
        rowSize = 27 ; rules
        colSize = 7  ; membership functions of output variable "braking strength"
    """
    rules = [[0] * 3 for i in range(27)]
    """
        Definitions
        Input "v": speed [0 - 100] kmph
            vs - slow
            vm - medium
            vf - fast
        
        Input "s": distance [0 - 100] m
            ss - short
            sm - medium
            sl - long

        Input "f": friction factor [0 - 1]
            fl - low
            fm - medium
            fh - high
    """

    vs, vm, vf = fuzzify(speed, fuzzy_inputs['v']['mfs'])
    ss, sm, sl = fuzzify(distance, fuzzy_inputs['s']['mfs'])
    fl, fm, fh = fuzzify(frictionFactor, fuzzy_inputs['f']['mfs'])

    """
        Braking Strength Required
        MembershipOutputIndex:
            S - 0
            M - 1
            L - 2
            
            
            #VS - 0
            #S - 1
            #RS - 2
            #...
            #VL - 6
        For all "n" with output VS, store it in column 0, and for S in column 1 ...
        Based on the principles of s = ut+(1/2)at^2
    """

    # rules[ruleIndex][membershipOutputIndex]
    rules[0][0] = min(min(fh, sl), vs) 
    rules[1][0] = min(min(fm, sl), vs)
    rules[2][0] = min(min(fl, sl), vs)
    rules[3][0] = min(min(fh, sm), vs)
    rules[4][0] = min(min(fm, sm), vs)
    rules[5][0] = min(min(fl, sm), vs)
    rules[6][0] = min(min(fh, ss), vs)
    rules[7][0] = min(min(fm, ss), vs)
    rules[8][1] = min(min(fl, ss), vs)
    rules[9][0] = min(min(fh, sl), vm)
    rules[10][0] = min(min(fm, sl), vm)
    rules[11][0] = min(min(fl, sl), vm)
    rules[12][0] = min(min(fh, sm), vm)
    rules[13][0] = min(min(fm, sm), vm)
    rules[14][1] = min(min(fl, sm), vm)
    rules[15][0] = min(min(fh, ss), vm)
    rules[16][1] = min(min(fm, ss), vm)
    rules[17][2] = min(min(fl, ss), vm)
    rules[18][1] = min(min(fh, ss), vf)
    rules[19][2] = min(min(fm, ss), vf)
    rules[20][2] = min(min(fl, ss), vf)
    rules[21][0] = min(min(fh, sm), vf)
    rules[22][1] = min(min(fm, sm), vf)
    rules[23][2] = min(min(fl, sm), vf)
    rules[24][0] = min(min(fh, sl), vf)
    rules[25][1] = min(min(fm, sl), vf)
    rules[26][1] = min(min(fl, sl), vf)

    return rules

if __name__ == '__main__':
    main()
