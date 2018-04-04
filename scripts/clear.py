#!/usr/bin/python3.5
import os
os.system("fuser -k 8888/tcp")
os.system("fuser -k 8081/tcp")
os.system("fuser -k 8090/tcp")
os.system("fuser -k 8083/tcp")
os.system("fuser -k 8084/tcp")
os.system("fuser -k 8082/tcp")
print("kill signals sended")
