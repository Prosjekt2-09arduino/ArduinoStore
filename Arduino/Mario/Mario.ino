#include <ComputerSerial.h>

#include <NewTone.h>
NewTone tone1;
int i = 0;

//Constants
static const int PIN_LED = 2;
static const int PIN_SOUND = 3;
static const int RESET_PIN = 4;
// static const int PIN_VIBRATION = 5;
static const int PIN_DEBUG_LED = 13;

static const int PIN_SCREEN_A = 7;
static const int PIN_SCREEN_B = 8;
static const int PIN_SCREEN_1 = 9;
static const int PIN_SCREEN_2 = 10;
static const int PIN_SCREEN_3 = 11;
static const int PIN_SCREEN_4 = 12;

//Global variables
static ComputerSerial comp;

void setup()
{
	//Initialize computer serial class
	comp.begin(115200);
	
	// Initialize reset function
	pinMode(RESET_PIN,OUTPUT);
	digitalWrite(RESET_PIN,HIGH);
	
	comp.setDeviceName("uCSS");
	comp.setDeviceVersion("v0.1a");
	
	// LED
	comp.addDeviceService("LED_LAMP", "2,13");
	pinMode(PIN_LED, OUTPUT);
	pinMode(PIN_DEBUG_LED, OUTPUT);

	//Download links
	comp.addDeviceDownloadLink("http://folk.ntnu.no/svarvaa/utils/pro2www/#appId1", "OSNAP Jacket");
	
	
	tone1.begin(3);//Playback on Pin 11, change to whatever you may need
}

void loop()
{
	if(i<140) {
		switch(i) {
			case 0: delay(500);tone1.play(660,100); break;
			case 1: delay(75);tone1.play(660,100); break;
			case 2: delay(150);tone1.play(660,100); break;
			case 3: delay(150);tone1.play(510,100); break;
			case 4: delay(50);tone1.play(660,100); break;
			case 5: delay(150);tone1.play(770,100); break;
			case 6: delay(275);tone1.play(380,100); break;
			case 7: delay(287);tone1.play(510,100); break;
			case 8: delay(225);tone1.play(380,100); break;
			case 9: delay(200);tone1.play(320,100); break;
			case 10: delay(250);tone1.play(440,100); break;
			case 11: delay(150);tone1.play(480,80); break;
			case 12: delay(165);tone1.play(450,100); break;
			case 13: delay(75);tone1.play(430,100); break;
			case 14: delay(150);tone1.play(380,100); break;
			case 15: delay(100);tone1.play(660,80); break;
			case 16: delay(100);tone1.play(760,50); break;
			case 17: delay(75);tone1.play(860,100); break;
			case 18: delay(150);tone1.play(700,80); break;
			case 19: delay(75);tone1.play(760,50); break;
			case 20: delay(175);tone1.play(660,80); break;
			case 21: delay(150);tone1.play(520,80); break;
			case 22: delay(75);tone1.play(580,80); break;
			case 23: delay(75);tone1.play(480,80); break;
			case 24: delay(175);tone1.play(510,100); break;
			case 25: delay(275);tone1.play(380,100); break;
			case 26: delay(200);tone1.play(320,100); break;
			case 27: delay(250);tone1.play(440,100); break;
			case 28: delay(150);tone1.play(480,80); break;
			case 29: delay(165);tone1.play(450,100); break;
			case 30: delay(75);tone1.play(430,100); break;
			case 31: delay(150);tone1.play(380,100); break;
			case 32: delay(100);tone1.play(660,80); break;
			case 33: delay(100);tone1.play(760,50); break;
			case 34: delay(75);tone1.play(860,100); break;
			case 35: delay(150);tone1.play(700,80); break;
			case 36: delay(75);tone1.play(760,50); break;
			case 37: delay(175);tone1.play(660,80); break;
			case 38: delay(150);tone1.play(520,80); break;
			case 39: delay(75);tone1.play(580,80); break;
			case 40: delay(75);tone1.play(480,80); break;
			case 41: delay(250);tone1.play(500,100); break;
			case 42: delay(150);tone1.play(760,100); break;
			case 43: delay(50);tone1.play(720,100); break;
			case 44: delay(75);tone1.play(680,100); break;
			case 45: delay(75);tone1.play(620,150); break;
			case 46: delay(150);tone1.play(650,150); break;
			case 47: delay(150);tone1.play(380,100); break;
			case 48: delay(75);tone1.play(430,100); break;
			case 49: delay(75);tone1.play(500,100); break;
			case 50: delay(150);tone1.play(430,100); break;
			case 51: delay(75);tone1.play(500,100); break;
			case 52: delay(50);tone1.play(570,100); break;
			case 53: delay(110);tone1.play(500,100); break;
			case 54: delay(150);tone1.play(760,100); break;
			case 55: delay(50);tone1.play(720,100); break;
			case 56: delay(75);tone1.play(680,100); break;
			case 57: delay(75);tone1.play(620,150); break;
			case 58: delay(150);tone1.play(650,200); break;
			case 59: delay(150);tone1.play(1020,80); break;
			case 60: delay(150);tone1.play(1020,80); break;
			case 61: delay(75);tone1.play(1020,80); break;
			case 62: delay(150);tone1.play(380,100); break;
			case 63: delay(150);tone1.play(500,100); break;
			case 64: delay(150);tone1.play(760,100); break;
			case 65: delay(50);tone1.play(720,100); break;
			case 66: delay(75);tone1.play(680,100); break;
			case 67: delay(75);tone1.play(620,150); break;
			case 68: delay(150);tone1.play(650,150); break;
			case 69: delay(150);tone1.play(380,100); break;
			case 70: delay(75);tone1.play(430,100); break;
			case 71: delay(75);tone1.play(500,100); break;
			case 72: delay(150);tone1.play(430,100); break;
			case 73: delay(75);tone1.play(500,100); break;
			case 74: delay(50);tone1.play(570,100); break;
			case 75: delay(110);tone1.play(500,100); break;
			case 76: delay(150);tone1.play(760,100); break;
			case 77: delay(50);tone1.play(720,100); break;
			case 78: delay(75);tone1.play(680,100); break;
			case 79: delay(75);tone1.play(620,150); break;
			case 80: delay(150);tone1.play(650,200); break;
			case 81: delay(150);tone1.play(1020,80); break;
			case 82: delay(150);tone1.play(1020,80); break;
			case 83: delay(75);tone1.play(1020,80); break;
			case 84: delay(150);tone1.play(380,100); break;
			case 85: delay(150);tone1.play(500,100); break;
			case 86: delay(150);tone1.play(760,100); break;
			case 87: delay(50);tone1.play(720,100); break;
			case 88: delay(75);tone1.play(680,100); break;
			case 89: delay(75);tone1.play(620,150); break;
			case 90: delay(150);tone1.play(650,150); break;
			case 91: delay(150);tone1.play(380,100); break;
			case 92: delay(75);tone1.play(430,100); break;
			case 93: delay(75);tone1.play(500,100); break;
			case 94: delay(150);tone1.play(430,100); break;
			case 95: delay(75);tone1.play(500,100); break;
			case 96: delay(50);tone1.play(570,100); break;
			case 97: delay(210);tone1.play(585,100); break;
			case 98: delay(275);tone1.play(550,100); break;
			case 99: delay(210);tone1.play(500,100); break;
			case 100: delay(180);tone1.play(380,100); break;
			case 101: delay(150);tone1.play(500,100); break;
			case 102: delay(150);tone1.play(500,100); break;
			case 103: delay(75);tone1.play(500,100); break;
			case 104: delay(150);tone1.play(500,60); break;
			case 105: delay(75);tone1.play(500,80); break;
			case 106: delay(150);tone1.play(500,60); break;
			case 107: delay(175);tone1.play(500,80); break;
			case 108: delay(75);tone1.play(580,80); break;
			case 109: delay(175);tone1.play(660,80); break;
			case 110: delay(75);tone1.play(500,80); break;
			case 111: delay(150);tone1.play(430,80); break;
			case 112: delay(75);tone1.play(380,80); break;
			case 113: delay(300);tone1.play(500,60); break;
			case 114: delay(75);tone1.play(500,80); break;
			case 115: delay(150);tone1.play(500,60); break;
			case 116: delay(175);tone1.play(500,80); break;
			case 117: delay(75);tone1.play(580,80); break;
			case 118: delay(75);tone1.play(660,80); break;
			case 119: delay(225);tone1.play(870,80); break;
			case 120: delay(162);tone1.play(760,80); break;
			case 121: delay(300);tone1.play(500,60); break;
			case 122: delay(75);tone1.play(500,80); break;
			case 123: delay(150);tone1.play(500,60); break;
			case 124: delay(175);tone1.play(500,80); break;
			case 125: delay(75);tone1.play(580,80); break;
			case 126: delay(175);tone1.play(660,80); break;
			case 127: delay(75);tone1.play(500,80); break;
			case 128: delay(150);tone1.play(430,80); break;
			case 129: delay(75);tone1.play(380,80); break;
			case 130: delay(300);tone1.play(660,100); break;
			case 131: delay(75);tone1.play(660,100); break;
			case 132: delay(150);tone1.play(660,100); break;
			case 133: delay(150);tone1.play(510,100); break;
			case 134: delay(50);tone1.play(660,100); break;
			case 135: delay(150);tone1.play(770,100); break;
			case 136: delay(225);tone1.play(380,100); break;
		}
		i++;
	}
}

void serialEvent()
{
	comp.serialEvent();
}
