import time

from threading import Thread
from queue import Queue, Empty

'''
Class representing a Node in the network. Consists of two threads to enable parallel processing

1. Receiver Thread - To handle incoming messages (ARP Requests and Responses).
2. Sender Thread - To send ARP queries.
'''
class Node:

    def __init__(self, network, name, ip_address, mac_address, queries):        
        # Initialise parameters
        self.name = name
        self.ip_address = ip_address
        self.mac_address = mac_address
        self.queries = queries
        self.network = network

        # Initialise message queue
        self.message_queue = Queue()

        # Table which maps ip_address -> mac_address
        self.arp_table = {}

        # Initialize sender thread
        self.sender_worker = Thread(target=self.sender)

        # Initialize receiver thread
        self.receiver_worker = Thread(target=self.receiver)

        # Add node to network
        self.network.add_node(self)     

    def start_receiver(self):
        # Start sender and receiver threads
        self.receiver_worker.start()
    
    def start_sender(self):
        self.sender_worker.start()

    def stop_receiver(self):
        self.message_queue.join()

    def stop_sender(self):
        self.sender_worker.join()

    def add_arp_entry(self, ip_address, mac_address):
        # Check if entry already exists
        if ip_address in self.arp_table:
            self.logger.log("Duplicate ARP Response")
        else:
            self.arp_table[ip_address] = mac_address
            self.logger.log("ARP Table Updated. ARP Table:\n", self.pretty_arp_table())

    def send_arp_request(self, target_ip_address):
        pass

    def send_arp_response(self, target_ip_address, target_mac_address):
        pass

    def handle_arp_request(self, request):
        pass

    def handle_arp_response(self, response):
        pass

    def sender(self):
        for query in self.queries:
            # Delay
            time.sleep(1)

            self.send_arp_request(query)

    def receiver(self):
        while not self.stop_event.is_set():
            try:
                arp_message = self.message_queue.get(block=True, timeout=1)
            except Empty:
                continue

            self.logger.log("Received ARP Message:\n", arp_message)
            
            if arp_message.opcode == 1:
                self.handle_arp_request(arp_message)
            elif arp_message.opcode == 2:
                self.handle_arp_response(arp_message)
            else:
                print("Message invalid\n")

            self.message_queue.task_done()
