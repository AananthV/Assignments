from arp_message import ARPMessage
from logger import Logger

class Network:

    def __init__(self):
        # Table which maps mac_address -> node
        self.routing_table = {}

        # Initialise logger
        self.logger = Logger(
            name="[Network]\n", 
            file="network.txt"
        )

    def add_node(self, node):
        # Check for duplicates
        if node.mac_address in self.routing_table:
            self.logger.log("Duplicate MAC Address: {}".format(node.mac_address))
        else:
            self.routing_table[node.mac_address] = node
            self.logger.log("Routing table updated. Routing table:\n", self.pretty_routing_table())

    def broadcast(self, message: ARPMessage):
        # Broadcast message to all nodes
        for node in self.routing_table.values():
            if node.mac_address == message.sha:
                continue
            
            node.message_queue.put(message)

    def unicast(self, message: ARPMessage):
        # Unicast message to target (mac address)
        if message.tha not in self.routing_table:
            self.logger.log("Target address {} not in routing table!".format(message.tha))
        else:
            self.routing_table[message.tha].message_queue.put(message)

    def pretty_routing_table(self):
        s = '| MAC Address \t\t| Node \t|\n'
        for mac, node in self.routing_table.items():
            s += '| {}\t| {}\t\t|\n'.format(mac, node.name)
        
        return s

