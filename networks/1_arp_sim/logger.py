from threading import Thread
from queue import Queue

class Logger:

    def __init__(self, name, file):
        self.name = name

        self.file = './logs/{}'.format(file)
        # Clear existing 
        open(self.file, 'w').close()

    def log(self, *values):
        s = self.name
        for v in values:
            s += str(v)
        s += '\n'

        print(s)

        with open(self.file, 'a') as file:
            file.write(s)
