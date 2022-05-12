#include <Arduino.h>
#line 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
#define PIN_X_DIRECTION 12
#define PIN_X_PWM        3
#define PIN_X_BRAKE      9
#define PIN_Y_DIRECTION 13
#define PIN_Y_PWM       11
#define PIN_Y_BRAKE      8
#define LINKS         true
#define RECHTS       false
#define OMHOOG        true
#define OMLAAG       false
#define MOTOR_PK       255

#line 13 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup();
#line 25 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void loop();
#line 36 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
int SI_send_packet(int reqid, const char* data);
#line 42 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void SI_recv_packets();
#line 47 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_handshake(int packetid);
#line 52 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_available(int packetid);
#line 57 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
const char * SI_get_packet(int packetid);
#line 64 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction, int duratie);
#line 71 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction);
#line 79 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_pwm(int value);
#line 84 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_direction(bool direction);
#line 89 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_brake(bool enabled);
#line 96 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction, int duratie);
#line 103 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction);
#line 111 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_pwm(int value);
#line 116 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_direction(bool direction);
#line 121 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_brake(bool enabled);
#line 13 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup()
{
  pinMode(PIN_X_DIRECTION, OUTPUT);
  pinMode(PIN_X_PWM,       OUTPUT);
  pinMode(PIN_X_BRAKE,     OUTPUT);
  pinMode(PIN_Y_DIRECTION, OUTPUT);
  pinMode(PIN_Y_PWM,       OUTPUT);
  pinMode(PIN_Y_BRAKE,     OUTPUT);

  Serial.begin(115200);
}

void loop()
{
  X_beweeg(MOTOR_PK, LINKS, 1000);

  delay(1000);
  X_set_brake(true);
  delay(1000);
}

////// SERIAL INTERFACE ///////////////

int SI_send_packet(int reqid, const char* data)
{
  int packetid = 0;
  return packetid;
}

void SI_recv_packets()
{
  // Put packets into buffer
}

bool SI_packet_handshake(int packetid)
{
  // Whether packet handshake was successful
}

bool SI_packet_available(int packetid)
{
  // Whether packet has been received
}

const char* SI_get_packet(int packetid)
{
  // Return last packet data or null if none was received
}

////// MOTOR X-axis ///////////////////

void X_beweeg(int pwm, bool direction, int duratie)
{
  X_beweeg(pwm, direction);
  delay(duratie);
  X_beweeg(0, false);
}

void X_beweeg(int pwm, bool direction)
{
  X_set_brake(true);
  X_set_direction(direction);
  X_set_brake(false);
  X_set_pwm(pwm);
}

void X_set_pwm(int value)
{
  analogWrite(PIN_X_PWM, value);
}

void X_set_direction(bool direction)
{
  digitalWrite(PIN_X_DIRECTION, direction);
}

void X_set_brake(bool enabled)
{
  digitalWrite(PIN_X_BRAKE, enabled);
}

////// MOTOR Y-axis ///////////////////

void Y_beweeg(int pwm, bool direction, int duratie)
{
  Y_beweeg(pwm, direction);
  delay(duratie);
  Y_beweeg(0, false);
}

void Y_beweeg(int pwm, bool direction)
{
  Y_set_brake(true);
  Y_set_direction(direction);
  Y_set_brake(false);
  Y_set_pwm(pwm);
}

void Y_set_pwm(int value)
{
  analogWrite(PIN_Y_PWM, value);
}

void Y_set_direction(bool direction)
{
  digitalWrite(PIN_Y_DIRECTION, direction);
}

void Y_set_brake(bool enabled)
{
  digitalWrite(PIN_Y_BRAKE, enabled);
}

