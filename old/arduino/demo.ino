void setup() {
  // put your setup code here, to run once:
  pinMode(A0, INPUT);
  pinMode(13, OUTPUT);
  Serial.begin(9600);
  digitalWrite(13, HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
  int x = map(analogRead(A0), 0, 1023, 1, 100);

  if (Serial.available() > 0) {
    String b = Serial.readString();
    b.trim();
    if(b == "rood") {
      digitalWrite(13, HIGH);
    }
    else {
      digitalWrite(13, LOW);
    }
  }
  Serial.println(x);
}