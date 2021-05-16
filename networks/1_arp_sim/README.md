## Address Resolution Protocol (ARP) Simulator

A program which simulates the Address Resolution Protocol (ARP) by using python threads.

Programming Assignment done by **V. Aananth (106118103)** for the  Internetworking Protocols course (CSPC 32).

### Requirements

1. Python 3.6.5+
2. Pip3

### Usage

1. Install dependencies 

	```
	pip3 install -r requirements.txt
	```
	
2. Adjust settings by modifying `config.yml`

	```
	nodes: # List of nodes
		# Each node contains the following information
		- name: "A" # Name of the node
		  ip_address: "192.168.1.1" # IP Address of the node
		  mac_address: "aa:aa:aa:aa:aa:aa" # MAC Address of the node
		  queries: # ARP Requests the Node performs. It is a list of IPs
		    - "192.168.1.2"
			- "192.168.1.3"
		- name: "B"
		  ip_address: "192.168.1.2"
		  mac_address: "bb:bb:bb:bb:bb:bb"
		  queries:
			- "192.168.1.3"
		- name: "C"
		  ip_address: "192.168.1.3"
		  mac_address: "cc:cc:cc:cc:cc:cc"
		  queries: []
	```
	
3. Run the program

	```
	python3 main.py
	```
	The program will output both in console (shared by all nodes) and also will create separate log files for each node in `./logs/`
	
4. Check logs in `./logs/` for IO output.

### Working
The program consists of three modules,

1. Node
	- Represents a single node in the network. 
	- Consists of two threads to enable parallel processing
		1. Receiver Thread - To handle incoming messages (ARP Requests and Responses).
		2. Sender Thread - To send ARP queries.
	- Consists of a thread safe `Queue` to handle simultaneous incoming messages.

2. Network
	- Acts similarly to a **Switch** in a local network.
	- Contains a routing table to **forward messages** to nodes according to their **MAC Address**
	- Handles unicasting and broadcasting operations.

3. Logger
	- Used to log the IO operations in the program.

0.  ARP Message 
	- Class used to represent the ARP Message
	- Parameters:

	```
		1. htype
			- Size: 2 bytes
			- Description: Hardware Type, 1 corresponds to Ethernet
		2. ptype
			- Size: 2 bytes
			- Description: Protocol Type, 0x0800 corresponds to IPv4
		3. hlen
			- Size: 1 byte
			- Description: Length (in octets) of a hardware address.
									Ethernet address length is 6.
		4. plen
			- Size: 1 byte
			- Description: Length (in octets) of internetwork addresses.
						   IPv4 address length is 6.
		5. opcode
			- Size: 2 bytes
			- Description: Specifies the operation that the sender is performing. 
						   1 for request, 2 for reply.
		6. sha
			- Size: 6 bytes
			- Description: Sender hardware address. (MAC Address)
		7. spa
			- Size: 4 bytes
			- Description: Sender protocol address. (IP Address)
		8. tha
			- Size: 6 bytes
			- Description: Target hardware address. (MAC Address). 
						   00:00:00:00:00:00 if sending ARP Request
		9. tpa
			- Size: 4 bytes
			- Description: Target protocol address. (IP Address)
	```

---