from socket import *

s = socket(AF_INET, SOCK_STREAM)
host = "127.0.0.1"
port = 1010
s.connect((host, port))
FileSize = input ("choose fileSize S,M,L")
s.send(bytes((FileSize, "utf-8"))

c, addr = s.accept()

l = c.recv(1024)
while l :
    l = c.recv(1024)
f.close()
c.send(bytes(('quit', "utf-8"))
c.close()
