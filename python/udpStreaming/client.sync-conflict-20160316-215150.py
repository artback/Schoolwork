from socket import *

UDP_IP = "127.0.0.1"
UDP_PORT = 5005
print ("UDP target IP:", UDP_IP)
print ("UDP target port:", UDP_PORT)
sock = socket(AF_INET, SOCK_DGRAM)
f = 0.06
v = 50
S = 1000
while True:
  sock.sendto(bytes(1000), (UDP_IP, UDP_PORT))

sock.close()
