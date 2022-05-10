void setup() {
  // put your setup code here, to run once:
  pinMode(A0, INPUT);
  pinMode(13, OUTPUT);
  Serial.begin(115200);
  digitalWrite(13, HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
  int x = map(analogRead(A0), 0, 1023, 0, 100);

  if (Serial.available() > 0)
  {
    char buff[255];
    String serialIn = Serial.readString();
    serialIn.trim();

    serialIn.toCharArray(buff, 255);
    
    String requests[10];
    int j = 0;
    String str = "";
    for (int i = 0; i < 255; i++)
    {
      if (buff[i] == 0)
      {
        break;
      }
      if (buff[i] == ',')
      {
        requests[j++] = str;
        str = "";
        continue;
      }
      str += buff[i];
    }

    for(int i = 0; i < 10; i++)
    {
      String b = requests[i];
      
      if(b == "roodaan")
      {
        digitalWrite(13, HIGH);
        Serial.println("ACK,");
      }
      else if (b == "rooduit")
      {
        digitalWrite(13, LOW);
        Serial.println("ACK,");
      }
      else if(b == "pot")
      {
        Serial.println(String(x) + ",");
      }
    }
  }
}
