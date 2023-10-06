#include <iostream>
#include <fstream>
#include <sstream>

#include "primary_tree.h"

class InputHandler
{
public:
    static int charArrToInt(char charArr[]);
    static int *stringToIntArray(std::string stringArr, int *numbers);
    static int stringToInt(std::string string);
    static int stringToFloat(std::string string);
    static std::string *splitLine(std::string line, int itemNum, char divider);
    static void fileHandler(std::string inputFile, std::string avlOutputFile, std::string llrbOutputFile);
};