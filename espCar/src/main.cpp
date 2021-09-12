#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

const char* ssid = "wifi_ssid";
//const char* pass = "pass";

WiFiUDP Udp;

#define PORT 5002

//pins

#define BACK1 1
#define BACK2 3
#define STEER1 15
#define STEER2 13



void initPins(){
  pinMode(BACK1,OUTPUT);
  digitalWrite(BACK1,LOW);
  pinMode(BACK2,OUTPUT);
  digitalWrite(BACK2,LOW);
  pinMode(STEER1,OUTPUT);
  digitalWrite(STEER1,LOW);
  pinMode(STEER2,OUTPUT);
  digitalWrite(STEER2,LOW);
}

void front(){
  digitalWrite(BACK1,HIGH);
}

void back(){
  digitalWrite(BACK2,HIGH);
}

void left(){
  digitalWrite(STEER1,HIGH);
}

void right(){
  digitalWrite(STEER2,HIGH);
}

void stopMovement(){
  // Serial.printf("back1: %d and back2: %d\n",digitalRead(BACK1),digitalRead(BACK2));
  if(digitalRead(BACK1) == HIGH)
    digitalWrite(BACK1,LOW);
  else
    digitalWrite(BACK2,LOW);  
}

void stopSteer(){
  if(digitalRead(STEER1) == HIGH)
    digitalWrite(STEER1,LOW);
  else
    digitalWrite(STEER2,LOW);  
}



void receivePacket(void){
  char packet[10] = {0};
  int packetSize = Udp.parsePacket();
  if(packetSize){ // have a packet
    // Serial.printf("GOT ONE from %s\n",Udp.remoteIP().toString().c_str());
    int len = Udp.read(packet,10);
    if(len)
      packet[len] = '\0';  
    int dir = atoi(packet);
    switch(dir){
      case 8: //front
        front();
        break;
      case 2:
        back();
        break;
      case 4:
        left();
        break;
      case 6:
        right();
        break;
      case 7:
        stopMovement();
        break;
      case 3:
        stopSteer();
        break;
      default:
        Serial.printf("something nasty happened...%d\n",dir);  
    }
   // delay(5000);
  }
 // else Serial.printf("no packets came...yet\n");
}


void setup() {
  Serial.begin(115200);

  WiFi.begin(ssid);
  while(WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.print(".");  
  }
  Serial.println();
  Serial.println("connected!");
  Serial.println(WiFi.localIP());
  Udp.begin(PORT);
  initPins();

}


void loop() {
  receivePacket();  
}