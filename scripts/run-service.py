#!/usr/bin/python3.5

import os
import subprocess
import sys

from subprocess import PIPE

import requests
import time


def serviceRaise(relativePath, healthUrl, serverName):
  os.chdir(relativePath)
  with open('./run-out.txt', 'w') as f:
    p = subprocess.Popen('mvn spring-boot:run -P dev-standalone', shell=True, stdout=f, stderr=f)
  time.sleep(15)
  timeout = time.time() + 60   # 20 sec from now

  while True:
    try:
      if time.time() > timeout:
        raise TimeoutError("Cannot connect to config on url: " + healthUrl)
      time.sleep(3)
      print(serverName + " Request performing")
      r = requests.get(healthUrl)
      data = r.json()
      if r.status_code == 200:
        print(serverName + " OK")
        print(data)
        break
    except requests.exceptions.ConnectionError:
      pass
  return p


serviceName = sys.argv[1]
os.environ["MAVEN_OPTS"] = "-Xmx128M"
if serviceName == "":
  raise ValueError('A very specific bad thing happened')

if serviceName == "ui":
  ui = serviceRaise("../sqap-ui", "http://localhost:8080/health", "UI")



# config = serviceRaise("../sqap-config", "http://localhost:8888/health", "Config")
# #TODO async from here
# discovery = serviceRaise("../sqap-discovery", "http://localhost:8081/health", "Discovery")
# gateway = serviceRaise("../sqap-gateway", "http://localhost:8090/health", "Gateway")
# auth = serviceRaise("../sqap-auth", "http://localhost:8083/health", "Auth")
# #admin = serviceRaise("../sqap-admin", "http://localhost:8884/health", "Admin")
# ui = serviceRaise("../sqap-ui", "http://localhost:8080/health", "UI")
#TODO kill subprocesses on quit
