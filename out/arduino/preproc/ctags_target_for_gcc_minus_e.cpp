# 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"







void setup()
{
  pinMode(12, 0x1);
  pinMode(3, 0x1);
  pinMode(9, 0x1);
  pinMode(13, 0x1);
  pinMode(11, 0x1);
  pinMode(8, 0x1);

  Serial.begin(115200);
}

void loop()
{
  A_beweeg(255, true, 1000);

  delay(1000);
  A_set_brake(true);
  delay(1000);
}

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

void A_set_pwm(int value)
{
  analogWrite(3, value);
}

void B_set_pwm(int value)
{
  analogWrite(11, value);
}

void A_set_direction(bool direction)
{
  digitalWrite(12, direction);
}

void B_set_direction(bool direction)
{
  digitalWrite(13, direction);
}

void A_set_brake(bool enabled)
{
  digitalWrite(9, enabled);
}

void B_set_brake(bool enabled)
{
  digitalWrite(8, enabled);
}
