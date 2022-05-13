# 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
# 29 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup()
{
  pinMode(12, 0x1);
  pinMode(3, 0x1);
  pinMode(9, 0x1);
  pinMode(13, 0x1);
  pinMode(11, 0x1);
  pinMode(8, 0x1);

  Serial.begin(115200);
  
# 39 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino" 3
 (*(volatile uint8_t *)(0xB1)) 
# 39 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
        = 
# 39 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino" 3
          (*(volatile uint8_t *)(0xB1)) 
# 39 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
                 & 248 | 1; // TCCR2B: 1 / 1
  
# 40 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino" 3
 (*(volatile uint8_t *)(0xB0)) 
# 40 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
        = 
# 40 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino" 3
          (*(volatile uint8_t *)(0xB0)) 
# 40 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
                 & 248 | 1; // TCCR2A: 1 / 1
}

void loop()
{
  for(int i = 0; i < 5; i++)
  {
    X_naar(i);
    delay(500);
    Y_naar(i);
    delay(500);

    SI_log("Going to X,Y" + String(i) + ", " + String(i));
  }
}

////// POSITIE FUCNTIES ///////////////

/**

 * @brief Beweeg naar X-positie

 * 

 * @param pos 0 - 4

 */
# 63 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_naar(int pos)
{
  X_beweeg(192, true, 1525);
  delay(250);
  X_beweeg(192, false, 1525 / 4 * pos + pos * 40);
}

/**

 * @brief Beweeg naar Y-positie

 * 

 * @param pos 0 - 4

 */
# 75 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_naar(int pos)
{
  Y_beweeg(192, false, 1525);
  delay(250);
  Y_beweeg(192, true, 1525 / 4 * pos + pos * 40 + 200);
}

////// SERIAL INTERFACE ///////////////

void SI_log(String msg)
{

  Serial.println(msg);

}

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
  bool packet_handshake = false;
  return packet_handshake;
}

bool SI_packet_available(int packetid)
{
  // Whether packet has been 
  bool packet_available = false;
  return packet_available;
}

const char* SI_get_packet(int packetid)
{
  // Return last packet data or null if none was received
  const char* packet_data = "packetdata";
  return packet_data;
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

////// MOTOR Y-axis ///////////////////

void Z_beweeg(int pwm, bool direction, int duratie)
{
  Z_beweeg(pwm, direction);
  delay(duratie);
  Z_beweeg(0, false);
}

void Z_beweeg(int pwm, bool direction)
{
  Z_set_brake(true);
  Z_set_direction(direction);
  Z_set_brake(false);
  Z_set_pwm(pwm);
}

void Z_set_pwm(int value)
{
  analogWrite(5, value);
}

void Z_set_direction(bool direction)
{
  digitalWrite(4, direction);
}

void Z_set_brake(bool enabled)
{
  // TODO
  //digitalWrite(PIN_Z_BRAKE, enabled);
}
