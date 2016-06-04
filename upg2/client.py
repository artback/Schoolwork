from socket import *

s = socket(AF_INET, SOCK_STREAM)
host = "192.168.0.247"
port = 1010
s.connect((host, port))
FileSize = input("choose fileSize S,M,L")
s.send(FileSize.encode())
l = s.recv(4096)

while (l):
    s.recv(1024)

s.close()
