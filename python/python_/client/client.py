from socket import *

s = socket(AF_INET, SOCK_STREAM)
host = "127.0.0.1"
port = 1010
s.connect((host, port))
FileSize = input("choose fileSize S,M,L")
s.send(FileSize.encode())
l = s.recv(4096)

while(l)
    l = s.recv(1024)
    print("reciving")

s.close()
