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

    while (end != std::string::npos) // string::npos used to find index of last blank space
    {
        stringArray[i++] = line.substr(start, end - start);
        start = end + 1;
        end = line.find(divider, start);
    }
    stringArray[i] = line.substr(start, line.length() - start);

    return stringArray;
}

void InputHandler::fileHandler(std::string inputFile, std::string outputFile, Street *street)
{
    std::ofstream outputs(outputFile.c_str());
    std::ifstream inputs(inputFile.c_str());
    std::string line;

    while (std::getline(inputs, line, '\n'))
    {
        std::string *splittedLine = splitLine(line, 5, '\t');
        if (splittedLine[0] == "add_apartment")
            street->add_apartment(splittedLine[1][0], stringToInt(splittedLine[3]), splittedLine[2]);
        else if (splittedLine[0] == "add_flat")
        {
            Apartment *apartment = street->searchApartment(splittedLine[1][0]);
            apartment->add_flat(stringToInt(splittedLine[4]), stringToInt(splittedLine[3]), stringToInt(splittedLine[2]));
        }
        else if (splittedLine[0] == "remove_apartment")
            street->remove_apartment(splittedLine[1][0]);
        else if (splittedLine[0] == "make_flat_empty")
        {
            Apartment *apartment = street->searchApartment(splittedLine[1][0]);
            apartment->make_flat_empty(stringToInt(splittedLine[2]));
        }
        else if (splittedLine[0] == "find_sum_of_max_bandwidths")
            street->find_sum_of_max_bandwidths(outputs);
        else if (splittedLine[0] == "merge_two_apartments")
            street->merge_two_apartments(splittedLine[1][0], splittedLine[2][0]);
        else if (splittedLine[0] == "relocate_flats_to_same_apartment")
        {
            int temp = 0;
            int *flat_num = &temp;
            int *flatArray = stringToIntArray(splittedLine[3], flat_num);
            street->relocate_flats_to_same_apartment(splittedLine[1][0], stringToInt(splittedLine[2]), flatArray, *flat_num);
        }
        else if (splittedLine[0] == "list_apartments")
        {
            street->list_apartments(outputs);
        }
    }

    outputs.close();
    inputs.close();
}
