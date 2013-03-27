/*
* Copyright 2012 NTNU
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

#include <Arduino.h>

#include "ComputerSerial.h"

void* ComputerSerial::placeHolder(uint8_t flag, uint8_t content[], word contentSize)
{
	return NULL;
}

ComputerSerial::ComputerSerial(long baud)
{
    //Initialize with baudrate if requested
    if(baud != 0) begin(baud);
}

void ComputerSerial::begin(long baud){
	Serial.begin(baud);

	for (int i = 0; i < NUM_OPCODES; ++i)
    {
		attachFunction(i, &ComputerSerial::placeHolder);
	}
}

void ComputerSerial::commandHandler(word size, uint8_t opcode, uint8_t flag, uint8_t content[]) {
	switch (opcode) {
		case OPCODE_PING:
			ping();
			break;
		case OPCODE_TEXT:
			text(size, flag, content);
			break;
		case OPCODE_SENSOR:
			sensor(flag);
			break;
		case OPCODE_DATA:
			data(size, flag, content);
			break;
		case OPCODE_PIN_R:
			pinRead(flag);
			break;
		case OPCODE_PIN_W:
			pinWrite(flag, content[0]);
			break;
        case OPCODE_DEVICE_INFO:
            getDeviceInfo();
            break;
		case OPCODE_RESET:
			break;
		default:
			break;
	}
}

void ComputerSerial::setDeviceName(const String &name)
{
    deviceName = name;
}

void ComputerSerial::setDeviceVersion(const String &version)
{
    deviceVersion = version;
}

void ComputerSerial::addDeviceService(const char service[], const char pin[])
{
    //Is the first element in the JSon array? If not we need to add a comma seperator
    if(deviceServices.length() > 0) deviceServices += ", ";

    //Append element to array
	deviceServices += "{ \"id\": \"";
	deviceServices += service;
	deviceServices += "\", \"pins\" :\"";

	//Append pins
	if(sizeof(pin) > 0)  deviceServices += pin;
	else                 deviceServices += "-1";

    //the end
	deviceServices += "\"}";;

	// sample: {"name":"service",
	//			"pins":"1,2,3" }

}

void ComputerSerial::addDeviceDownloadLink(const char link[], const char platform[])
{
    //Is the first element in the JSon array? If not we need to add a comma seperator
    if(deviceDownloadLinks.length() > 0) deviceDownloadLinks += ", ";

    //Append element to array
	deviceDownloadLinks += "{\"name\":\"";
    deviceDownloadLinks += platform;
    deviceDownloadLinks += "\", \"link\":\"";
    deviceDownloadLinks += link;
    deviceDownloadLinks += "\"}";

	// sample: {"platform":"link"}
}

void ComputerSerial::getDeviceInfo(){
    //Build the device info JSON object
    String deviceInfo;

    //Device name
	deviceInfo += "{\"name\":\"";
	deviceInfo += deviceName;
	deviceInfo += "\",";

    //Device version
    deviceInfo += "\"version\":\"";
    deviceInfo += deviceVersion;
    deviceInfo += "\",";

    //Device services
	deviceInfo += "\"services\": [";
	deviceInfo += deviceServices;
	deviceInfo += "],";

    //Device links
	deviceInfo += "\"links\": [";
	deviceInfo += deviceDownloadLinks;
	deviceInfo += "]}";

    word len = deviceInfo.length();

    //send response back
	Serial.write(START_BYTE);
	Serial.write(highByte(len));
	Serial.write(lowByte(len));
	Serial.write(OPCODE_RESPONSE);
	Serial.write(OPCODE_DEVICE_INFO);

    for(word i = 0; i < len; i++)
    {
        Serial.write(deviceInfo[i]);
    }
}

void ComputerSerial::ack(uint8_t opcode, uint8_t content[], word contentSize)
{
    //Packet header
	Serial.write(START_BYTE);
	Serial.write(highByte(contentSize));
	Serial.write(lowByte(contentSize));
	Serial.write(OPCODE_RESPONSE);
	Serial.write(opcode);

	//Packet Payload
	for (int i = 0; i < contentSize; i++)
    {
		Serial.write(content[i]);
	}
}

void ComputerSerial::ping()
{
	// Send ping response
	ack(OPCODE_PING);
}

void ComputerSerial::text(word size, uint8_t flag, uint8_t content[])
{
	// Print content on display(flag)
	functions[OPCODE_TEXT](flag, content, size);
	ack(OPCODE_TEXT);
}

void ComputerSerial::sensor(uint8_t number) {
	// Send value of sensor(number)
	uint8_t content[] = {};
	int *status = (int*)functions[OPCODE_SENSOR](number, content, 0);

	uint16_t value = *status;
	uint8_t replyContent[] = {value >> 8, value};

	ack(OPCODE_SENSOR, replyContent, 2);

	free(status);
}

void ComputerSerial::data(word size, uint8_t flag, uint8_t content[]) {
	functions[OPCODE_DATA](flag, content, size);
	ack(OPCODE_DATA);
}

void ComputerSerial::pinRead(uint8_t pin) {
	// Send pin(pin) value
	pinMode(pin, INPUT);
	int value = digitalRead(pin);
	uint8_t content[] = {value > 0 ? 1 : 0};
	ack(OPCODE_PIN_R, content, 1);
}

void ComputerSerial::pinWrite(uint8_t pin, uint8_t value) {
	// Set value of pin(pin)
	pinMode(pin, OUTPUT);
	digitalWrite(pin, value ? HIGH : LOW);
	ack(OPCODE_PIN_W);
}

void ComputerSerial::reset() {
	// Reset arduino
}

void ComputerSerial::attachFunction(uint8_t opcode,
	void* (*handler)(uint8_t flag, uint8_t content[], word contentSize))
{
    functions[opcode] = handler;
}

void ComputerSerial::serialEvent() {
    static int state = STATE_START;
	static long time = 0;
	static word size = 0;
	static uint8_t opcode = 0;
	static uint8_t flag = 0;
	static uint8_t *content = NULL;
	static uint8_t content_counter = 0;

    //Check if there is a timeout
	if (millis() > time && state != STATE_START) {
		state = STATE_START;
	}
	time = millis() + TIMEOUT;

    //Recieved new data?
	while(Serial.available())
    {
		switch (state)
		{
			case STATE_START:
				if (Serial.read() == START_BYTE)
                {
                    state = STATE_SIZE_HIGH;
                    if(content != NULL) free(content);
                }
				break;

			case STATE_SIZE_HIGH:
				size = Serial.read() << 8;    //get high byte
				state = STATE_SIZE_LOW;
				break;

            case STATE_SIZE_LOW:
				size |= Serial.read() & 0xFF; //get low byte
				state = STATE_OPCODE;

				//Try to allocate memory for the payload
				content = (uint8_t*)malloc(size);
				if(content == NULL) state = STATE_START;
                break;

			case STATE_OPCODE:
				opcode = Serial.read();
				state = STATE_FLAG;
				break;

			case STATE_FLAG:
				flag = Serial.read();
				state = STATE_CONTENT;
				break;

			case STATE_CONTENT:
				content[content_counter] = Serial.read();
				content_counter++;
				if (content_counter >= size)
                {
					commandHandler(size, opcode, flag, content);
					content_counter = 0;
					state = STATE_START;
				}
				break;
			default:
				break;
		}
		bytesReceived++;
	}
}

unsigned int ComputerSerial::getBytesReceived(){
	return bytesReceived;
}
