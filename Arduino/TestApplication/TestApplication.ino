#include <ComputerSerial.h>
#include <LiquidCrystal.h>
#include <EEPROM.h>

//Constants
static const int PIN_LED = 2;
static const int PIN_SOUND = 3;
static const int RESET_PIN = 4;
static const int PIN_DEBUG_LED = 13;

static const int PIN_SCREEN_A = 7;
static const int PIN_SCREEN_B = 8;
static const int PIN_SCREEN_1 = 9;
static const int PIN_SCREEN_2 = 10;
static const int PIN_SCREEN_3 = 11;
static const int PIN_SCREEN_4 = 12;

unsigned long time;

static const int TEMPERATURE_SENSOR = 5;
static const int LIGHT_SENSOR = 6;

//Global variables
static ComputerSerial comp;
static LiquidCrystal lcd(PIN_SCREEN_A, PIN_SCREEN_B, PIN_SCREEN_1, PIN_SCREEN_2, PIN_SCREEN_3, PIN_SCREEN_4);

// Reset
void* reset(byte flag, byte data[], word dataSize)
{
}

void setup()
{
	//Initialize computer serial class
	comp.begin(115200);
	
	comp.setDeviceName("uCSS");
	comp.setDeviceVersion("v0.1a");
	
	// Set BT in programming mode, 115200 baudrate
	comp.addDeviceService("RESET", "");
	comp.attachFunction(comp.OPCODE_RESET, &reset);
	
	// LCD
	lcd.begin(16, 2);
	lcd.setCursor(0, 0);
	lcd.clear();

	//Download links
	comp.addDeviceDownloadLink("http://folk.ntnu.no/svarvaa/utils/pro2www/#appId1", "OSNAP Jacket");
	
	// Timer used by LCD
	time = millis();
}

void loop()
{
	//Run every 2 sec
	if(millis() >= time+1000) {
		lcd.clear();
		
		// Calculate and print temperature
		float temperature = (((analogRead(TEMPERATURE_SENSOR) * 5.0) / 1024) - 0.5) * 100;
		lcd.setCursor(0, 0);
		lcd.print("Temp: ");
		lcd.print(temperature);
		lcd.print(" C");

		// // Print light sensor
		// lcd.setCursor(0,1);
		// lcd.print("Light: ");
		// lcd.print(analogRead(map(analogRead(LIGHT_SENSOR), 0, 1023, 0, 100);));
		// lcd.print(" %");
		
		time = millis();
	}
}

void serialEvent()
{
	comp.serialEvent();
}
