#include "input_handler.h"

int InputHandler::charArrToInt(char charArr[])
// Char Array to Integer convertor
{
    bool signFlag = false;
    int i = 0, number = 0;
    while (charArr[i] != '\0')
    {
        if (charArr[i] == '-')
        {
            signFlag = true;
            continue;
        }
        number = 10 * number + charArr[i] - '0';
        ++i;
    }
    return signFlag ? -number : number;
}

int *InputHandler::stringToIntArray(std::string stringArr, int *numbers)
// Char Array to Integer convertor
{
    int strLen = stringArr.length();
    *numbers = 0;
    int start = 1;
    int end = stringArr.find(',', start);

    while (end != std::string::npos) // string::npos used to find index of last blank space
    {
        (*numbers)++;
        start = end + 1;
        end = stringArr.find(',', start);
    }
    (*numbers)++;

    int *intArray = new int[*numbers];
    int i = 0;
    start = 1;
    end = stringArr.find(',', start);

    while (end != std::string::npos) // string::npos used to find index of last blank space
    {
        intArray[i++] = stringToInt(stringArr.substr(start, end - start));
        start = end + 1;
        end = stringArr.find(',', start);
    }
    intArray[i] = stringToInt(stringArr.substr(start, stringArr.length() - start - 1));

    return intArray;
}

int InputHandler::stringToInt(std::string string)
// String to Integer Convertor
{
    bool signFlag = false; // Used to determine if the number is negative
    int number = 0;

    for (int i = 0; i < string.length(); i++)
    {
        if (string[i] == '-')
        {
            signFlag = true;
            continue;
        }
        number = 10 * number + string[i] - '0';
    }

    return signFlag ? -number : number;
}

std::string *InputHandler::splitLine(std::string line, int itemNum, char divider)
{
    std::string *stringArray = new std::string[itemNum];
    int i = 0;
    int start = 0;
    int end = line.find(divider);

    while (end != -1) // string::npos used to find index of last blank space
    {
        stringArray[i++] = line.substr(start, end - start);
        start = end + 1;
        end = line.find(divider, start);
    }
    stringArray[i] = line.substr(start, line.length() - start);

    return stringArray;
}

void InputHandler::fileHandler(std::string inputFile, std::string outputFile, EventQueue *eventQueue)
{
    std::ofstream outputs(outputFile.c_str());
    std::ifstream inputs(inputFile.c_str());
    std::string line;

    std::getline(inputs, line, '\n');
    int cashierNumber = stringToInt(line);
    std::getline(inputs, line, '\n');
    int customerNumber = stringToInt(line);
    eventQueue = new EventQueue(customerNumber);

    Order **orders = new Order *[customerNumber];
    int orderCounter = 0;
    while (std::getline(inputs, line, '\n'))
    {
        std::string *splittedLine = splitLine(line, 4, ' ');
        Order *tempOrder = new Order(std::stof(splittedLine[0]),
                                     std::stof(splittedLine[1]),
                                     std::stof(splittedLine[2]),
                                     std::stof(splittedLine[3]),
                                     orderCounter);
        orders[orderCounter] = tempOrder;
        Event *newEvent = new Event("enter",
                                    std::stof(splittedLine[0]),
                                    tempOrder);
        eventQueue->enqueue(*newEvent);
        ++orderCounter;
    }

    EventHandler eventHandler = EventHandler(eventQueue, cashierNumber, customerNumber);
    eventHandler.orders = orders;
    eventHandler.handleEvents(outputs);

    outputs.close();
    inputs.close();
}
