# 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
# 13 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
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
  X_beweeg(255, true, 1000);

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
  analogWrite(3, value);
}

void X_set_direction(bool direction)
{
  digitalWrite(12, direction);
}

void X_set_brake(bool enabled)
{
  digitalWrite(9, enabled);
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
  analogWrite(11, value);
}

void Y_set_direction(bool direction)
{
  digitalWrite(13, direction);
}

void Y_set_brake(bool enabled)
{
  digitalWrite(8, enabled);
}
