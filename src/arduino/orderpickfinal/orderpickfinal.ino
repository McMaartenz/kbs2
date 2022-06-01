#include <stdlib.h>

// movement variables
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
#define MOTOR_PK       170
#define MOTOR_PK_ONDER 150

/*
 * Motor X - Geel & bruin
 * Motor X + Geel & wit
 * 
 * Motor Y - Blauw
 * Motor Y + Rood
 * 
 * Motor Z - Roze & bruin
 * Motor Z + Roze & wit
 */

#define X_BAAN_TIJD   4000
#define X_OFFSET       875

#define Y_BAAN_TIJD    2000
#define Y_INITIAL_DIST 1000
#define Y_OFFSET       975

#define Z_BAAN_TIJD    500
int X_POS, Y_POS;

// packet variables
#define PAKKET_MAX_LENGTE 64

int** punten_arr = NULL;
int punten_aantal = 0;
int huidig_punt = 0;

void setup()
{
  // initialize movement code
  pinMode(PIN_X_DIRECTION, OUTPUT);
  pinMode(PIN_X_PWM,       OUTPUT);
  pinMode(PIN_X_BRAKE,     OUTPUT);
  pinMode(PIN_Y_DIRECTION, OUTPUT);
  pinMode(PIN_Y_PWM,       OUTPUT);
  pinMode(PIN_Y_BRAKE,     OUTPUT);
  TCCR2B = TCCR2B & B11111000 | B00000001; // TCCR2B: 1 / 1
  TCCR2A = TCCR2A & B11111000 | B00000001; // TCCR2A: 1 / 1
  X_POS = 1;
  Y_POS = 1;

  Z_reset();
  Y_reset();
  X_reset(LINKS);

  Serial.begin(9600);
  while (!Serial);
}

void loop()
{
    handlePacket();
}

/**
 * @brief Whether the string has a certain prefix
 * 
 * @param str String to test
 * @param pre Prefix to use
 * @return String starting with the prefix
 */
bool hasPrefix(const char* str, const char* pre)
{
    return strncmp(pre, str, strlen(pre)) == 0;
}

/**
 * @brief Sleep for ms, handle packets in the background
 *
 * @param ms Milliseconds to sleep
 */
void sleep(unsigned long ms)
{
    ms = millis() + ms;
    while (ms > (millis() - 250))
    {
        handlePacket();
    }
    while (ms > millis());
}

////// PACKAGES ///////////////
/**
 *  @brief Handle a single packet coming in
 */
void handlePacket()
{
    if (!Serial.available())
    {
        return;
    }

    char buffer[PAKKET_MAX_LENGTE]; // Pakket max-lengte 64
    for (int i = 0; i < PAKKET_MAX_LENGTE; i++)
    {
        buffer[i] = '\0';
    }
    for (int i = 0; i < PAKKET_MAX_LENGTE; i++)
    {
        while (!Serial.available());
        char currentChar = Serial.read();
        if (currentChar == '\n')
        {
            buffer[i] = 0;
            break;
        }

        buffer[i] = currentChar;
    }

    if (hasPrefix(buffer, "status"))
    {
        Serial.println("OK");
    }
    else if (hasPrefix(buffer, "ping"))
    {
        Serial.println("Pong!");
    }
    else if (hasPrefix(buffer, "step"))
    {
        if (punten_aantal <= 0)
        {
            Serial.println("ErrorNoPoints");
            return;
        }
        if (punten_arr == NULL || huidig_punt >= punten_aantal)
        {
            Serial.println("ErrorIllegalState");
            return;
        }

        // PULL NEXT COORDINATE
        int requested_x, requested_y;
        requested_x = punten_arr[huidig_punt][0];
        requested_y = punten_arr[huidig_punt][1];

        huidig_punt++;
        if (huidig_punt == punten_aantal)
        {
            Serial.println("OKEndPoint");
        }
        else
        {
            Serial.println("OK");
        }
        ////// MOVEMENT ///////////////

        X_naar(requested_x);
        delay(250);
        Y_naar(requested_y);
        delay(250);
        // Z_duw();

        // zodra klaar
        Serial.println("uitgetikt");
    }
    else if (hasPrefix(buffer, "pos!"))
    {
        // Start reading points array
        char* lengthBuff = new char[3];
        lengthBuff[0] = buffer[4];
        lengthBuff[1] = buffer[5];
        lengthBuff[2] = '\0';
        int pointAmount = atoi(lengthBuff);

        int** punten = new int*[pointAmount];
        for (int i = 0; i < pointAmount; i++)
        {
            int j = 6 + i * 2;
            if ((j + 1) > PAKKET_MAX_LENGTE)
            {
                Serial.println("ErrorPacketTooLong");
                return;
            }
            else if (buffer[j] == '\0' || buffer[j + 1] == '\0')
            {
                Serial.println("ErrorSizeMismatch");
                return;
            }

            punten[i] = new int[2];
            punten[i][0] = buffer[j] - '0';
            punten[i][1] = buffer[j + 1] - '0';
        }

        punten_arr = punten;
        punten_aantal = pointAmount;
        huidig_punt = 0;

        Serial.println("OK");
    }
    else
    {
        Serial.println("InaudibleGarbage");
    }
}

////// POSITIE FUNCTIES ///////////////
////// RESETS ///////////////
/**
 * @brief Reset X-baan
 * 
 */
void X_reset(bool direction)
{
  X_beweeg(MOTOR_PK, direction, X_BAAN_TIJD * 1.2);
  if (direction == RECHTS)
  {
    track_X(5);
  }
  else
  {
    track_X(1);
  }
}

/**
 * @brief Reset Y-baan
 * 
 */
void Y_reset()
{
  Y_beweeg(MOTOR_PK, OMLAAG, Y_BAAN_TIJD * 1.2);
  Y_beweeg(MOTOR_PK, OMHOOG, Y_INITIAL_DIST);
  track_Y(1);
}

////// X MOVE ///////////////
/**
 * @brief Beweeg naar X-positie
 * 
 * @param pos 1 - 5
 */
void X_naar(int pos)
{
  if (pos == X_POS) {
    return;
  }
  if (pos == 1)
  {
    X_reset(LINKS);
    track_X(1);
    return;
  }
  if (pos == 5)
  {
    X_reset(RECHTS);
    track_X(5);
    return;
  }
  int relpos = pos - X_POS;
  if (relpos < 0)
  {
    X_beweeg(MOTOR_PK, LINKS, X_OFFSET * (0 - relpos));
  }
  else
  {
    X_beweeg(MOTOR_PK, RECHTS, X_OFFSET * (relpos));
  }
  track_X(pos);
}

////// Y MOVE ///////////////
/**
 * @brief Beweeg naar Y-positie
 * 
 * @param pos 1 - 5
 */
void Y_naar(int pos)
{
    if (pos == Y_POS) {
    return;
  }
  if (pos == 1)
  {
    Y_reset();
    track_Y(1);
    return;
  }
  int relpos = pos - Y_POS;
  if (relpos < 0)
  {
    Y_beweeg(MOTOR_PK, OMLAAG, Y_OFFSET * (0 - relpos));
    Y_beweeg(150, OMHOOG, 50);
  }
  else
  {
    Y_beweeg(MOTOR_PK, OMHOOG, Y_OFFSET * relpos);
  }
  track_Y(pos);
}

////// Z MOVE ///////////////
/**
 * @brief Geef een duw aan product
 * 
 */
void Z_duw()
{
  Z_beweeg(MOTOR_PK, VOORUIT, Z_BAAN_TIJD);
  delay(250);
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

////////////////////////////////////////
////// MOTOR X-axis ///////////////////

void track_X(int newpos) {
  X_POS = newpos;
}
void track_Y(int newpos) {
  Y_POS = newpos;
}

void X_beweeg(int pwm, bool direction, int duratie)
{
  X_beweeg(pwm, direction);
  sleep(duratie);
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
  sleep(duratie);
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

////// MOTOR Z-axis ///////////////////

void Z_beweeg(int pwm, bool direction, int duratie)
{
  Z_beweeg(pwm, direction);
  sleep(duratie);
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
