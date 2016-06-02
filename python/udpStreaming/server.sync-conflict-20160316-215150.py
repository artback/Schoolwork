from socket import *

UDP_IP = ""
UDP_PORT = 5005

sock = socket(AF_INET,SOCK_DGRAM)
sock.bind((UDP_IP, UDP_PORT))

print ("server listening")

while True:
    data, addr = sock.recvfrom(1024)
    print ("message received")
