
/* Original author Posted by David Houlding at 2:10 PM 36 
*  original source code found at
* http://davidhoulding.blogspot.com/
* Modified and reused bye 316 Air project team
*/

#include <string.h>
#define VOLTAGE_REGULATOR_DIGITAL_OUT_PIN 8
#define MQ7_ANALOG_IN_PIN 0
#define MQ4_ANALOG_IN_PIN 1

#define HEATER_5_V_TIME_MILLIS 60000
#define HEATER_1_4_V_TIME_MILLIS 90000

#define GAS_LEVEL_READING_PERIOD_MILLIS 1000

unsigned long startMillis;
unsigned long switchTimeMillis;
boolean heaterInHighPhase;

void setup() {
  Serial.begin(9600);
  
  pinMode(VOLTAGE_REGULATOR_DIGITAL_OUT_PIN, OUTPUT);
  
  startMillis = millis();
  
  turnHeaterHigh();
  
  Serial.println("Elapsed Time (s), Gas Level");
}

void loop() {
  if(heaterInHighPhase) {
    // 5v phase of cycle. see if need to switch low yet
    if(millis() > switchTimeMillis)  {
      turnHeaterLow();
    }
  }
  else {
    // 1.4v phase of cycle. see if need to switch high yet
    if(millis() > switchTimeMillis) {
      turnHeaterHigh();
    }
  }
  
  readGasLevel();
  delay(GAS_LEVEL_READING_PERIOD_MILLIS);
}

void turnHeaterHigh(){
  // 5v phase
  digitalWrite(VOLTAGE_REGULATOR_DIGITAL_OUT_PIN, LOW);
  heaterInHighPhase = true;
  switchTimeMillis = (millis() + HEATER_5_V_TIME_MILLIS);
}

void turnHeaterLow(){
  // 1.4v phase
  digitalWrite(VOLTAGE_REGULATOR_DIGITAL_OUT_PIN, HIGH);
  heaterInHighPhase = false;
  switchTimeMillis = (millis() + HEATER_1_4_V_TIME_MILLIS);
}

void readGasLevel(){
  unsigned int co2Level = analogRead(MQ7_ANALOG_IN_PIN);
unsigned int methaneLevel = analogRead(MQ4_ANALOG_IN_PIN);
  unsigned int time = (millis() - startMillis) / 1000;
  
  char timeString[20];
  Serial.println(sprintf(timeString,"%d",time));
  Serial.write(sprintf(timeString,"%d",time));
  
  char co2LevelString[20];
  Serial.println(sprintf(co2LevelString,"%d",co2Level));
  Serial.write(sprintf(co2LevelString,"%d",co2Level));
  char methaneLevelString[20];
  Serial.println(sprintf(methaneLevelString,"%d",methaneLevel));
  Serial.write(sprintf(methaneLevelString,"%d",methaneLevel));
}

