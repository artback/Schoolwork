


from socket import *
UDP_IP = "127.0.0.1"
UDP_PORT = 5005
MESSAGE = "Hello, World!"
print "UDP target IP:", UDP_IP
print "UDP target port:", UDP_PORT
sock = socket(AF_INET,SOCK_DGRAM) # UDP
f =
v = 50
S =
with open("send", "wb") as out:
    out.seek((1024 * 1024 * 1024) - 1)
    out.write('\0')
for i in v:
    sock.sendto(out, (UDP_IP, UDP_PORT))



