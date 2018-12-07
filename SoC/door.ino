#include <PubSubClient.h>
#include <ESP8266WiFi.h>

const char* ssid = "Simran";
const char* password = "simr1223";

char* topic = "esp8266_arduino";
char* server = "iot.eclipse.org";

void callback(char* topic, byte* payload, unsigned int length) {
  // handle message arrived
}
WiFiClient wifiClient;
PubSubClient client(server, 1883, callback, wifiClient);
//iloveyoumerijaan


int val = 0;
int unlock = 0;
String macToStr(const uint8_t* mac)
{
  String result;
  for (int i = 0; i < 6; ++i) {
    result += String(mac[i], 16);
    if (i < 5)
      result += ':';
  }
  return result;
}

void setup() {
  Serial.begin(115200);
  delay(10);
  
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  pinMode(1, OUTPUT);
  pinMode(2, INPUT);
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // Generate client name based on MAC address and last 8 bits of microsecond counter
  String clientName;
  clientName += "esp8266-";
  uint8_t mac[6];
  WiFi.macAddress(mac);
  clientName += macToStr(mac);
  clientName += "-";
  clientName += String(micros() & 0xff, 16);

  Serial.print("Connecting to ");
  Serial.print(server);
  Serial.print(" as ");
  Serial.println(clientName);
  
  if (client.connect((char*) clientName.c_str())) {
    Serial.println("Connected to MQTT broker");
    Serial.print("Topic is: ");
    Serial.println(topic);
    
    
  }
  else {
    Serial.println("MQTT connect failed");
    Serial.println("Will reset and try again...");
    abort();
  }
}

void loop() {
  val = digitalRead(2);
  String payload = "";
  
  if(val== HIGH)
  {
  
  digitalWrite(1, LOW);
  if (client.connected()){
    Serial.print("Sending payload: ");
    Serial.println(payload);
    payload = "Door Unlocked";
    if (client.publish(topic, (char*) payload.c_str())) {
      Serial.println("Publish ok");
      //sunny = 1;
    }
    else {
      Serial.println("Publish failed");
    }
  }
  }
  else
  {
                         // Wait for a second
  digitalWrite(1, HIGH);  // Turn the LED off by making the voltage HIGH
  if (client.connected()){
    Serial.print("Sending payload: ");
    Serial.println(payload);
    payload = "Door Locked";
    if (client.publish(topic, (char*) payload.c_str())) {
      Serial.println("Publish ok");
    }
    else {
      Serial.println("Publish failed");
    }
  }
  }
  //delay(1000);
  static int counter = 0;
  
}
