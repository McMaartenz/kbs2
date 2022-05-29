#include <stdlib.h>

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

    char buffer[32]; // Pakket max-lengte 32
    for (int i = 0; i < 32; i++)
    {
        buffer[i] = '\0';
    }
    for (int i = 0; i < 32; i++)
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

        Serial.println("OK:" + String(pointAmount));
    }
    else
    {
        Serial.println("InaudibleGarbage");
    }
}
