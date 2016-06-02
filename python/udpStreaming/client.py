from socket import *
import time


UDP_IP = "192.168.0.247"
UDP_PORT = 5005
print ("UDP target IP:", UDP_IP)
print ("UDP target port:", UDP_PORT)
sock = socket(AF_INET, SOCK_DGRAM)
f = 0.01
v = 60
S = 1000
t_end = time.time() + v
while time.time() < t_end:
  sock.sendto(bytes(S), (UDP_IP, UDP_PORT))
  time.sleep(f)
sock.close()
