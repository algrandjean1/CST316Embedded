#include <Arduino.h>
#include <string.h>
#include <Wire.h> //I2C library
void setup();
void loop();
void turnHeaterHigh();
void turnHeaterLow();
void readGasLevel();
byte fetch_humidity_temperature(unsigned int *p_H_dat, unsigned int *p_T_dat);
void print_float(float f, int num_digits);
#line 1 "src/mq7code.ino"

/* Original author Posted by David Houlding at 2:10 PM 36 
*  original source code found at
* http://davidhoulding.blogspot.com/
* Modified and reused bye 316 Air project team
*/

//#include <string.h>
//#include <Wire.h> //I2C library

byte fetch_humidity_temperature(unsigned int *p_Humidity, unsigned int *p_Temperature);
void print_float(float f, int num_digits);

#define HIH6100_TEMP_HUMIDITY_DIGITAL_IN_PIN 4
#define TRUE 1
#define FALSE 0

#define VOLTAGE_REGULATOR_DIGITAL_OUT_PIN 8
#define MQ7_ANALOG_IN_PIN 0
#define MQ4_ANALOG_IN_PIN 1

#define HEATER_5_V_TIME_MILLIS 60000
#define HEATER_1_4_V_TIME_MILLIS 90000

#define GAS_LEVEL_READING_PERIOD_MILLIS 1000

unsigned long startMillis;
unsigned long switchTimeMillis;
boolean heaterInHighPhase;
byte _status;
unsigned int H_dat, T_dat;
float RH, T_C;

void setup() {
  Serial.begin(9600);
  
  pinMode(VOLTAGE_REGULATOR_DIGITAL_OUT_PIN, OUTPUT);
  
  startMillis = millis();
  
  turnHeaterHigh();
  Wire.begin();
  pinMode(HIH6100_TEMP_HUMIDITY_DIGITAL_IN_PIN, OUTPUT);
  digitalWrite(HIH6100_TEMP_HUMIDITY_DIGITAL_IN_PIN, HIGH); // this turns on the HIH3610
  delay(5000);
  Serial.println(">>>>>>>>>>>>>>>>>>>>>>>>");  // just to be sure things are working
  
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
  
  _status = fetch_humidity_temperature(&H_dat, &T_dat);   
  
  RH = (float) H_dat * 6.10e-3;
  T_C = (float) T_dat * 1.007e-2 - 40.0;
  
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

    float tem = (((T_C*9)/5)+32);

  int wholeT = (int) tem;
  int wholeH = (int) RH;
  
  String message = String(wholeT)+","+String(wholeH)+","+String(co2Level)+","+String(methaneLevel);
  char charBuf[50];
  message.toCharArray(charBuf, 50);
  Serial.write(charBuf);
}

byte fetch_humidity_temperature(unsigned int *p_H_dat, unsigned int *p_T_dat)
{
      byte address, Hum_H, Hum_L, Temp_H, Temp_L, _status;
      unsigned int H_dat, T_dat;
      address = 0x27;;
      Wire.beginTransmission(address); 
      Wire.endTransmission();
      delay(100);
      
      Wire.requestFrom((int)address, (int) 4);
      Hum_H = Wire.read();
      Hum_L = Wire.read();
      Temp_H = Wire.read();
      Temp_L = Wire.read();
      Wire.endTransmission();
      
      _status = (Hum_H >> 6) & 0x03;
      Hum_H = Hum_H & 0x3f;
      H_dat = (((unsigned int)Hum_H) << 8) | Hum_L;
      T_dat = (((unsigned int)Temp_H) << 8) | Temp_L;
      T_dat = T_dat / 4;
      *p_H_dat = H_dat;
      *p_T_dat = T_dat;
      return(_status);
}
   
void print_float(float f, int num_digits)
{
    int f_int;
    int pows_of_ten[4] = {1, 10, 100, 1000};
    int multiplier, whole, fract, d, n;

    multiplier = pows_of_ten[num_digits];
    if (f < 0.0)
    {
        f = -f;
        Serial.print("-");
    }
    whole = (int) f;
    Serial.println(whole);
    fract = (int) (multiplier * (f - (float)whole));

    Serial.print(whole);
    Serial.print(".");

    for (n=num_digits-1; n>=0; n--) // print each digit with no leading zero suppression
    {
         d = fract / pows_of_ten[n];
         Serial.print(d);
         fract = fract % pows_of_ten[n];
    }
    Serial.println(f);
} 
