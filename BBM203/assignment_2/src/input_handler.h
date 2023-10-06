#include "assignment_2.h"
#include <iostream>
#include <fstream>
#include <sstream>

class InputHandler
{
public:
    static int charArrToInt(char charArr[]);
    static int *stringToIntArray(std::string stringArr, int *numbers);
    static int stringToInt(std::string string);
    static std::string *splitLine(std::string line, int itemNum, char divider);
    static void fileHandler(std::string inputFile, std::string outputFile, Street *street);
};