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
#line 20 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void loop();
#line 31 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_pwm(int value);
#line 36 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_set_pwm(int value);
#line 41 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_direction(bool direction);
#line 46 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void B_set_direction(bool direction);
#line 51 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void A_set_brake(bool enabled);
#line 56 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
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

  Serial.begin(115200);
}

void loop()
{
  A_set_direction(true);
  A_set_brake(false);
  A_set_pwm(100);
  delay(1000);

  A_set_brake(true);
  delay(1000);
}

void A_set_pwm(int value)
{
  analogWrite(PIN_A_PWM, value);
}

void B_set_pwm(int value)
{
  analogWrite(PIN_B_PWM, value);
}

void A_set_direction(bool direction)
{
  digitalWrite(PIN_A_DIRECTION, direction);
}

void B_set_direction(bool direction)
{
  digitalWrite(PIN_B_DIRECTION, direction);
}

void A_set_brake(bool enabled)
{
  digitalWrite(PIN_A_BRAKE, enabled);
}

void B_set_brake(bool enabled)
{
  digitalWrite(PIN_B_BRAKE, enabled);
}

