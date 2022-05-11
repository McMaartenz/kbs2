#include <Arduino.h>
#line 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
#define PIN_A_DIRECTION 12
#define PIN_A_PWM        3
#define PIN_A_BRAKE      9
#define PIN_B_DIRECTION 13
#define PIN_B_PWM       11
#define PIN_B_BRAKE      8

#line 8 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup();
#line 24 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void loop();
#line 35 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
int SI_send_packet(int reqid, const char* data);
#line 43 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_beweeg(int pwm, bool direction, int duratie);
#line 50 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_beweeg(int pwm, bool direction);
#line 58 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_pwm(int value);
#line 63 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_direction(bool direction);
#line 68 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_brake(bool enabled);
#line 75 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_beweeg(int pwm, bool direction, int duratie);
#line 82 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_beweeg(int pwm, bool direction);
#line 90 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_set_pwm(int value);
#line 95 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_set_direction(bool direction);
#line 100 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_set_brake(bool enabled);
#line 8 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup()
{
  pinMode(PIN_A_DIRECTION, OUTPUT);
  pinMode(PIN_A_PWM,       OUTPUT);
  pinMode(PIN_A_BRAKE,     OUTPUT);
  pinMode(PIN_B_DIRECTION, OUTPUT);
  pinMode(PIN_B_PWM,       OUTPUT);
  pinMode(PIN_B_BRAKE,     OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);
  digitalWrite(12, LOW);
  digitalWrite(13, HIGH);

  Serial.begin(115200);
}

void loop()
{
  B_beweeg(255, false, 1000);

  delay(1000);
  B_set_brake(true);
  delay(1000);
}

////// SERIAL INTERFACE ///////////////

int SI_send_packet(int reqid, const char* data)
{
  int packetid = 0;
  return packetid;
}

////// MOTOR A ////////////////////////

void A_beweeg(int pwm, bool direction, int duratie)
{
  A_beweeg(pwm, direction);
  delay(duratie);
  A_beweeg(0, false);
}

void A_beweeg(int pwm, bool direction)
{
  A_set_brake(true);
  A_set_direction(direction);
  A_set_brake(false);
  A_set_pwm(pwm);
}

void A_set_pwm(int value)
{
  analogWrite(PIN_A_PWM, value);
}

void A_set_direction(bool direction)
{
  digitalWrite(PIN_A_DIRECTION, direction);
}

void A_set_brake(bool enabled)
{
  digitalWrite(PIN_A_BRAKE, enabled);
}

////// MOTOR B ////////////////////////

void B_beweeg(int pwm, bool direction, int duratie)
{
  B_beweeg(pwm, direction);
  delay(duratie);
  B_beweeg(0, false);
}

void B_beweeg(int pwm, bool direction)
{
  B_set_brake(true);
  B_set_direction(direction);
  B_set_brake(false);
  B_set_pwm(pwm);
}

void B_set_pwm(int value)
{
  analogWrite(PIN_B_PWM, value);
}

void B_set_direction(bool direction)
{
  digitalWrite(PIN_B_DIRECTION, direction);
}

void B_set_brake(bool enabled)
{
  digitalWrite(PIN_B_BRAKE, enabled);
}

