from socket import *
serverName = "www.ingonline.nu"
serverPort = 80
httplink = "/et1447/index2.html"
clientSocket = socket(AF_INET, SOCK_STREAM)

request = ("GET /et1447/index5.html HTTP/1.1\r\nHost: www.ingonline.nu\r\n\r\n")

print (request)


clientSocket.connect((serverName, serverPort))
print (clientSocket.getpeername())
print (clientSocket.getsockname())


clientSocket.sendall(bytes(request, "utf-8"))
print( clientSocket.recvfrom(1024))

clientSocket.close()

