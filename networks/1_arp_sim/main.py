import yaml
import time
from threading import Event

from network import Network
from node import Node

# Set up stop event
stop_event = Event()

# Load configuration file
with open('./config.yaml') as c:
    config = yaml.load(c, Loader=yaml.FullLoader)

# Initialize network
network = Network()

nodes = []
# Initialize nodes
for node_config in config['nodes']:
    node = Node(network, node_config, stop_event)
    nodes.append(node)

# Start node receiver threads
for node in nodes:
    node.start_receiver()

# Start node sender threads
for node in nodes:
    node.start_sender()

# Join node sender threads
for node in nodes:
    node.stop_sender()

# Join node receiver threads
for node in nodes:
    node.stop_receiver()

# Wait for 1s
time.sleep(1)

# Send out stop signal to stop program
stop_event.set()

print("Execution completed. Check logs in ./logs/ folder.")

print("Final ARP Tables are:\n")
for node in nodes:
    print(node.logger.name, end="")
    print(node.pretty_arp_table())

