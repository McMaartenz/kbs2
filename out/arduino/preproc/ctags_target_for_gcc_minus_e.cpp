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
  A_set_direction(true);
  A_set_brake(false);
  A_set_pwm(100);
  delay(1000);

  A_set_brake(true);
  delay(1000);
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
