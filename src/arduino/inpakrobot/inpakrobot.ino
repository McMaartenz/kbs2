
int producten[] = {4, 7, 2, 6, 5, 2, 6, 3, 2};
int aantalProducten = 9;
int maxinhoud = 10;
boolean volgendePak = false;

int powerMotorR = 12;
int motorSpeed = 3;
int aanpassnelheid = 1;
int buttonPin = 2;

int buttonState = 0;
void setup() {
  // put your setup code here, to run once:
  pinMode(powerMotorR, OUTPUT);
  pinMode(motorSpeed, OUTPUT);

  pinMode(buttonPin, INPUT);

  Serial.begin(9600);
}

void brake(){
  digitalWrite(powerMotorR, LOW);
  analogWrite(motorSpeed, 150);
  delay(50);
  analogWrite(motorSpeed, 0);
  delay(50);
}

void brake2(){
  digitalWrite(powerMotorR, HIGH);
  analogWrite(motorSpeed, 150);
  delay(50);
  analogWrite(motorSpeed, 0);
  delay(50);
}


void kwartDraai(){
  if(volgendePak == true){
    digitalWrite(powerMotorR, HIGH);
    analogWrite(motorSpeed, 220);
    delay(550 * aanpassnelheid);
    brake();
  }
}

void kwartDraaiTerug(){
  if(volgendePak == true){
    digitalWrite(powerMotorR, LOW);
    analogWrite(motorSpeed, 220);
    delay(550 * aanpassnelheid);
    brake2();
  }
}

void buttonCheck(){
  buttonState = digitalRead(buttonPin);
  if(volgendePak == false){
    if(buttonState == HIGH){
      volgendePak = true;
      while(buttonState == HIGH){
        buttonState = digitalRead(buttonPin);
      }
    }
  }
  delay(50);
}

void binPacker(){
  int i;
  int resultaat = 0;
  int opslag[4] = {10,10,10,10};
  int positie = -1;
  if(volgendePak){
  for(i = 0; i < aantalProducten; i++){
      while(volgendePak == false){
        buttonCheck();
      }
      if(volgendePak == true){
        int j;
        int minimaal = maxinhoud + 1;
        int bi = 0;
        Serial.print("volgende product --> ");
        Serial.println(producten[i]);
        for(j = 0; j < resultaat; j++){
          if(opslag[j] >= producten[i] && opslag[j] - producten[i] < minimaal){
            bi = j;
            minimaal = opslag[j] - producten[i];
          } 
        }
        if (minimaal == maxinhoud + 1){
          opslag[resultaat] = maxinhoud - producten[i];
          positie++;
          kwartDraai();
          while(positie < resultaat){
              kwartDraai();
              positie++;
              }
          resultaat++;
          delay(100);
        }
        else{
          if(bi == positie){
            opslag[bi] -= producten[i];
          }
          else{
            while(positie > bi){
              positie--;
              kwartDraaiTerug();
            }
            while(positie < bi){
              positie++;
              kwartDraai();
            }
            opslag[bi] -= producten[i];
          }
          
        }
        for(int i = 0; i < 4; i++){
          Serial.print("|");
          Serial.print(opslag[i]);
          Serial.print("|");
          }
        Serial.println("");
        Serial.print(bi);
        Serial.print(" - ");
        Serial.println(positie);
      }
      volgendePak = false;
    }
    Serial.print("aantal dozen gebruikt: ");
    Serial.println(resultaat);
    positie = 0;
  }
}

void loop() {
  buttonCheck();
  binPacker();
}
