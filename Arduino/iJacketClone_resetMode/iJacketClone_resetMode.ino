// #include <EEPROM.h>

void setup()
{	
	// If programming mode is enabled, restart device
	pinMode(4,OUTPUT);
	// if(EEPROM.read(0) == 255)
	if(true)
	{
		delay(300);
		digitalWrite(4, LOW);
	}
	else
	{
		digitalWrite(4, HIGH);
	}
}

void loop()
{
}