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
#define Z_BAAN_TIJD    500

#define Y_INITIAL_DIST 200
#define CALIBRATION     40

//#define DEBUG_LOG

#line 31 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void setup();
#line 48 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void loop();
#line 69 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_naar(int pos);
#line 81 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_naar(int pos);
#line 92 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_duw();
#line 103 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_reset();
#line 110 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void SI_log(String msg);
#line 117 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
int SI_send_packet(int reqid, const char* data);
#line 123 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void SI_recv_packets();
#line 128 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_handshake(int packetid);
#line 135 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
bool SI_packet_available(int packetid);
#line 142 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
const char * SI_get_packet(int packetid);
#line 151 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction, int duratie);
#line 158 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_beweeg(int pwm, bool direction);
#line 166 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_pwm(int value);
#line 171 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_direction(bool direction);
#line 176 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void X_set_brake(bool enabled);
#line 183 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction, int duratie);
#line 190 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_beweeg(int pwm, bool direction);
#line 198 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_pwm(int value);
#line 203 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_direction(bool direction);
#line 208 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Y_set_brake(bool enabled);
#line 215 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_beweeg(int pwm, bool direction, int duratie);
#line 222 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_beweeg(int pwm, bool direction);
#line 229 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_pwm(int value);
#line 234 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_direction(bool direction);
#line 239 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
void Z_set_brake();
#line 31 "c:\\Users\\mcmaa\\src\\kbs2\\src\\arduino\\orderpickrobot\\orderpickrobot.ino"
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

  SI_log("Resetting Z");
  Z_reset();
}

void loop()
{
  for(int i = 0; i < 5; i++)
  {
    X_naar(i);
    delay(HALF_SECOND);
    Y_naar(i);
    delay(HALF_SECOND);
    Z_duw();

    SI_log("Going to X,Y" + String(i) + ", " + String(i));
  }
}

////// POSITIE FUNCTIES ///////////////

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

/**
 * @brief Geef een duw aan product
 * 
 */
void Z_duw()
{
  Z_beweeg(MOTOR_PK, VOORUIT, Z_BAAN_TIJD);
  delay(QUARTER_SECOND);
  Z_reset();
}

/**
 * @brief Reset de Z-motor naar achteren
 * 
 */
void Z_reset()
{
  Z_beweeg(MOTOR_PK, ACHTERUIT, Z_BAAN_TIJD);
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
  Z_set_brake();
  Z_set_direction(direction);
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

void Z_set_brake()
{
  Z_set_pwm(0);
}
