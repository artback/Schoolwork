from socket import *

s = socket(AF_INET, SOCK_STREAM)
host = "127.0.0.1"
port = 21040

s.connect((host, port))
FileSize = input ("choose fileSize S,M,L")

s.send(FileSize)

if(FileSize == "S"):
    f = open('text.txt','rb')
    l = f.read(1024)
elif(FileSize == "M"):
        f  = open('image.jpg', 'rb')
        s.send(f)
else:
        f  = open('video.mp4', 'rb')
        l = f.read(1024)
while (l):
    print ("Receiving...")
    f.write(l)
    l = c.recv(1024)
f.close()

print (s.recv(1024))
s.close
