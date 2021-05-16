from dataclasses import dataclass

'''
Class describing an ARP Message.

Currently descriptors exist only for certain request types

Reference: http://www.tcpipguide.com/free/t_ARPMessageFormat.htm

Parameters:
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
    6. tha
        - Size: 6 bytes
        - Description: Target hardware address. (MAC Address)
                       00:00:00:00:00:00 if sending ARP Request
    7. tpa
        - Size: 4 bytes
        - Description: Target protocol address. (IP Address)
'''

@dataclass
class ARPMessage:
    htype: int = 1
    ptype: int = 0x0800
    hlen: int = 6
    plen: int = 6
    opcode: int = None
    sha: str = None
    spa: str = None
    tha: str = None
    tpa: str = None

    def __str__(self):
        s = ''
        s += '\tHardware Type: {}\n'.format(self.htype)
        s += '\tProtocol Type: {}\n'.format(hex(self.ptype))
        s += '\tHLEN: {}\n'.format(self.hlen)
        s += '\tPLEN: {}\n'.format(self.plen)
        s += '\tOpcode: {}\n'.format(self.opcode)
        s += '\tSender Hardware Address: {}\n'.format(self.sha)
        s += '\tSender Protocol Address: {}\n'.format(self.spa)
        s += '\tTarget Hardware Address: {}\n'.format(self.tha)
        s += '\tTarget Protocol Address: {}\n'.format(self.tpa)

        return s