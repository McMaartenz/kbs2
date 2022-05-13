#include <Arduino.h>
#line 1 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
#define PIN_X_DIRECTION 12
#define PIN_X_PWM        3
#define PIN_X_BRAKE      9
#define PIN_Y_DIRECTION 13
#define PIN_Y_PWM       11
#define PIN_Y_BRAKE      8
#define PIN_Z_DIRECTION  4
#define PIN_Z_PWM        5

#define LINKS         true
#define RECHTS       false
#define OMHOOG        true
#define OMLAAG       false
#define VOORUIT       true
#define ACHTERUIT    false
#define MOTOR_PK       192

#define QUARTER_SECOND 250
#define HALF_SECOND    500
#define ONE_SECOND    1000
#define X_BAAN_TIJD   1525
#define Y_BAAN_TIJD   1525

#define Y_INITIAL_DIST 200
#define CALIBRATION     40

#define DEBUG_LOG

#line 29 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup();
#line 43 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void loop();
#line 63 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_naar(int pos);
#line 75 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_naar(int pos);
#line 84 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void SI_log(String msg);
#line 91 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
int SI_send_packet(int reqid, const char* data);
#line 97 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void SI_recv_packets();
#line 102 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_handshake(int packetid);
#line 109 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_available(int packetid);
#line 116 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
const char * SI_get_packet(int packetid);
#line 125 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction, int duratie);
#line 132 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction);
#line 140 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_pwm(int value);
#line 145 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_direction(bool direction);
#line 150 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_brake(bool enabled);
#line 157 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction, int duratie);
#line 164 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction);
#line 172 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_pwm(int value);
#line 177 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_direction(bool direction);
#line 182 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_brake(bool enabled);
#line 189 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_beweeg(int pwm, bool direction, int duratie);
#line 196 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_beweeg(int pwm, bool direction);
#line 204 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_pwm(int value);
#line 209 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_direction(bool direction);
#line 214 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_brake(bool enabled);
#line 29 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup()
{
  pinMode(PIN_X_DIRECTION, OUTPUT);
  pinMode(PIN_X_PWM,       OUTPUT);
  pinMode(PIN_X_BRAKE,     OUTPUT);
  pinMode(PIN_Y_DIRECTION, OUTPUT);
  pinMode(PIN_Y_PWM,       OUTPUT);
  pinMode(PIN_Y_BRAKE,     OUTPUT);

  Serial.begin(115200);
  TCCR2B = TCCR2B & B11111000 | B00000001; // TCCR2B: 1 / 1
  TCCR2A = TCCR2A & B11111000 | B00000001; // TCCR2A: 1 / 1
}

void loop()
{
  for(int i = 0; i < 5; i++)
  {
    X_naar(i);
    delay(HALF_SECOND);
    Y_naar(i);
    delay(HALF_SECOND);

    SI_log("Going to X,Y" + String(i) + ", " + String(i));
  }
}

////// POSITIE FUCNTIES ///////////////

/**
 * @brief Beweeg naar X-positie
 * 
 * @param pos 0 - 4
 */
void X_naar(int pos)
{
  X_beweeg(MOTOR_PK, LINKS, X_BAAN_TIJD);
  delay(QUARTER_SECOND);
  X_beweeg(MOTOR_PK, RECHTS, X_BAAN_TIJD / 4 * pos + pos * CALIBRATION);
}

/**
 * @brief Beweeg naar Y-positie
 * 
 * @param pos 0 - 4
 */
void Y_naar(int pos)
{
  Y_beweeg(MOTOR_PK, OMLAAG, Y_BAAN_TIJD);
  delay(QUARTER_SECOND);
  Y_beweeg(MOTOR_PK, OMHOOG, Y_BAAN_TIJD / 4 * pos + pos * CALIBRATION + Y_INITIAL_DIST);
}

////// SERIAL INTERFACE ///////////////

void SI_log(String msg)
{
#ifdef DEBUG_LOG
  Serial.println(msg);
#endif
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
  analogWrite(PIN_Z_PWM, value);
}

void Z_set_direction(bool direction)
{
  digitalWrite(PIN_Z_DIRECTION, direction);
}

void Z_set_brake(bool enabled)
{
  // TODO
  //digitalWrite(PIN_Z_BRAKE, enabled);
}
