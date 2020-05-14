from socket import *

fileSocket = socket(AF_INET, SOCK_STREAM)
port = 1010
adress = ""

    fileSocket.bind((adress, port))
    fileSocket.listen(1)
    conn, addr = fileSocket.accept()
    filetransfer = ""

    filetransfer = conn.recv(1024).decode()
    print (filetransfer)

    if(filetransfer == "S"):
        f = open('text.txt', 'rb')
    elif(filetransfer == "M"):
        f = open('image.jpg', 'rb')
    else:
        f = open('video.mp4', 'rb')

    l = f.read(1024)
    while(l):
        print("sending")
        conn.send(l)
        l = f.read(1024)
    f.close()
    conn.close()
    print ("closing filetransfer , BYE!")
