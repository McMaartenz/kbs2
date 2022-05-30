#include <stdlib.h>

#define PAKKET_MAX_LENGTE 64

int** punten_arr = NULL;
int punten_aantal = 0;
int huidig_punt = 0;

void setup()
{
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
        // ga naar X,Y
        // duw

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
