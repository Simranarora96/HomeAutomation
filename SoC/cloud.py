import bluetooth
import paho.mqtt.client as mqtt
import json
from pyfcm import FCMNotification
from twilio.rest import Client
import datetime
import threading
myregistration_id = "c-Kwm3KJnrQ:APA91bEM7c3QahIipg9dr5XF-7aReCjLlUrzhXsbbFFF0H-BRu4tzklP5XMFTUuOQT5JiWvBo8PPSHbpF2KxrKVAk1XhUi8nzJBrg0FLvhw_pAkU6neqr4vUuf-U-ZjVF1s7G2v0AiQw"
api_key = "AAAADcmd45g:APA91bHlv7o3Wnp4p-U5ij0QAZtRCBpRUr3JqourObNS96P2FV849h-if8vBdsdJ7BqUG9lwx-kTCqsyj3ksVZWSS0uGy9bnXXqXPUwdN7dOiIUXHnijRKgyuOlXNVTZpCKJEMP6ag_h"
account_sid = "AC0aa0a1072fbfc41e14c747bd169f3371"
auth_token = "57454bee974e1528d00416af11cb384b"
def on_connect(client, userdata, flags, rc):
    print("Connected with result code"+ str(rc))
    client.subscribe([("esp8266_arduino",0),("homestationapp101",0)])
    
state = "xyz"

    


def on_message(client, userdata, msg):
    #print("Message Received:", str(msg.payload))
    message = msg.payload.decode()
    global momregistration_id
    global myregistration_id
    global state
    if message == 'Door Locked' or message == 'Door Unlocked':
        cmd = message
        if state != cmd :
            print("in state loop")
            #print(message)
            if cmd == "Door Locked" :
                print("received command door locked.")
                push_service = FCMNotification(api_key=api_key)
                now = datetime.datetime.now()
                timestamp = now.strftime("%d-%m-%Y at %H:%M")
                data_message = {"message" : "Door Locked","timestamp" : timestamp}
                message_body = "hello"
                registration_ids = [myregistration_id]
                result = push_service.notify_multiple_devices(registration_ids=registration_ids, message_body=message_body, data_message=data_message)
                client1 = Client(account_sid, auth_token)
                client1.messages.create(to="+919971088600", from_="+19124175840", body="Door Locked!")
                call = client1.api.account.calls.create(to="+919971088600", from_="+19124175840", url="http://twimlets.com/holdmusic?Bucket=com.twilio.music.ambient")
                state = "Door Locked"
            
            if cmd == "Door Unlocked":
                print("Received command door unlocked.")
                push_service = FCMNotification(api_key=api_key)
                now = datetime.datetime.now()
                timestamp = now.strftime("%d-%m-%Y at %H:%M")
                data_message = {"message" : "Door Unlocked","timestamp" : timestamp}
                message_body = "hello"
                
                registration_ids = [myregistration_id]
                result = push_service.notify_multiple_devices(registration_ids=registration_ids, message_body=message_body, data_message=data_message)
                client2 = Client(account_sid, auth_token)
                client2.messages.create(to="+919971088600", from_="+19124175840", body="Door Unlocked!")
                call = client2.api.account.calls.create(to="+919971088600", from_="+19124175840", url="http://twimlets.com/holdmusic?Bucket=com.twilio.music.ambient")
                state = "Door Unlocked"
    
    
    
    if 'baddr' in message:
        msgble = json.loads(msg.payload)
        print("received bluetooth message")
        ble = msgble['baddr']
        print(ble)
        cmd = msgble['cmd']
        notify = msgble['notify']
        if (0 < cmd) and (cmd < 10) :
            try:
                bd_addr = ble
                port= 1
                sock = bluetooth.BluetoothSocket (bluetooth.RFCOMM)
                sock.connect((bd_addr,port))
                var2 = str(cmd)#converting int to str as arg in sock.send() is str
                sock.send(var2)#in final server sock.send(cmd)
                push_service = FCMNotification(api_key=api_key)
                now = datetime.datetime.now()
                timestamp = now.strftime("%d-%m-%Y at %H:%M")
                data_message = {"message" : notify,"timestamp" : timestamp}
                message_body = "hello"
                
                registration_ids = [myregistration_id]
                result = push_service.notify_multiple_devices(registration_ids=registration_ids, message_body=message_body, data_message=data_message)
                
            except:
                print("Host down")
                push_service = FCMNotification(api_key=api_key)
                now = datetime.datetime.now()
                timestamp = now.strftime("%d-%m-%Y at %H:%M")
                data_message = {"message" : "Bluetooth device offline!","timestamp" : timestamp}
                message_body = "hello"
                
                registration_ids = [myregistration_id]
                result = push_service.notify_multiple_devices(registration_ids=registration_ids, message_body=message_body, data_message=data_message)
                
            '''    
            response=""
            while 1:
                data = sock.recv(1024)
                print(data)
                response += data.decode()
                data_end = response.find('\n')
                print(data_end)
                if "\n" in data:
                    print(response)
                    client.publish("app2",response)
                    sock.close()
                    break'''
    
    if 'myregistration_id' in message:
        myregistration_id = json.loads(message)['myregistration_id']
        print("my token id refreshed")

    if message == 'hello from ESP8266':
        print("Door lock Connected", message)
        
    if message == 'status':
        client.publish("checkmydevice","online")
        print("published to me")
    
        
     
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message


client.connect("iot.eclipse.org", 1883, 60)
client.loop_forever()


