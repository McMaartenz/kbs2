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
#define MOTOR_PK_ONDER  48

#define QUARTER_SECOND 250
#define HALF_SECOND    500
#define ONE_SECOND    1000

#define X_BAAN_TIJD   1725
// #define X_OFFSET       440 //x offset boven
#define X_OFFSET       475

#define Y_BAAN_TIJD   1525
#define Y_INITIAL_DIST 550
#define Y_OFFSET       600

#define Z_BAAN_TIJD    500

#define CALIBRATION    110

// Stop de volgende lijn in comments om de serial niet te exploderen bij het pakkettensysteem
#define DEBUG_LOG

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

int X_POS, Y_POS;

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

  SI_log("Resetting Z & Y");
  Z_reset();
  Y_reset();

  X_POS = 1;
  Y_POS = 1;
}

void loop()
{
	handlePacket();
  X_reset(LINKS);
  Y_reset();

  for(int i = 1; i <= 5; i++)
  {
    X_naar(i);
    delay(QUARTER_SECOND);

    // Y_naar(5);
    for(int j = 1; j <= 5; j++)
    {
      SI_log("Going to X,Y " + String(i) + ", " + String(j));
      Y_naar(j);
      delay(HALF_SECOND);
      Z_duw();
      SI_log(String(Y_POS));
    }
  }
}

////// POSITIE FUNCTIES ///////////////

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

/**
 * @brief Beweeg naar X-positie
 * 
 * @param pos 1 - 5
 */
void X_naar(int pos)
{
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

/**
 * @brief Beweeg naar Y-positie
 * 
 * @param pos 1 - 5
 */
void Y_naar(int pos)
{
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

////// MOTOR Y-axis ///////////////////

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


////// SERIAL INTERFACE ///////////////


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
    while (ms < (millis() - 250))
    {
        handlePacket();
    }
    while (ms < millis());
}

/**
 *  @brief Handle a single packet coming in
 */
void handlePacket()
{
    if (!Serial.available())
    {
        return;
    }

    char buffer[12]; // Pakket max-lengte 12
    for (int i = 0; i < 12; i++)
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
    else if (hasPrefix(buffer, "pos!"))
    {
        // Start reading points array
        char* lengthBuff = new char[3];
        lengthBuff[0] = buffer[4];
        lengthBuff[1] = buffer[5];
        lengthBuff[2] = '\0';

        int pointAmount = atoi(lengthBuff);

        Serial.println("OK: " + String(pointAmount));
    }
    else
    {
        Serial.println("InaudibleGarbage");
    }
}

void SI_log(String s)
{
  #ifdef DEBUG_LOG
  Serial.println(s);
  #endif
}
