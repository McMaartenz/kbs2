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

    bool eql = strcmp(buffer, "status") == 0;
    if (eql)
    {
        Serial.println("Ye");
    }
    else
    {
        Serial.println("No");
    }
}
