#include <iostream>
#include <fstream>
#include <sstream>

int charArrToInt(char charArr[]);
int stringToInt(std::string string);
int *getDims(char charArr[], int *dims);
int **dynamic2DArrayAlloc(int **A, int nRow, int nCol);
int *splitLine(std::string line, int itemNum);
int **fileReader(std::string inputFile, int nRow, int nCol, int **A);
void searchTreasure(struct Map &map, struct Key &key, int x, int y, bool found, std::ostream &stream);
void fileWriter(std::string outputFile, struct Map &map, struct Key &key);
void delDynamic2DArray(int **A, int nRow);

struct Map
{
    int width;
    int height;
    int **matrix;
};

struct Key
{
    int dim;
    int mid;
    int **matrix;
};

int main(int argc, char *argv[])
{
    int *mapDims;
    mapDims = getDims(argv[1], mapDims);
    int keyDim = charArrToInt(argv[2]);
    std::string mapFile = argv[3];
    std::string keyFile = argv[4];
    std::string outputFile = argv[5];

    struct Map map;
    map.height = mapDims[0];
    map.width = mapDims[1];
    delete[] mapDims;

    struct Key key;
    key.dim = keyDim;
    key.mid = key.dim / 2; // middle index of key array

    map.matrix = fileReader(mapFile, map.height, map.width, map.matrix);
    key.matrix = fileReader(keyFile, key.dim, key.dim, key.matrix);

    fileWriter(outputFile, map, key);

    delDynamic2DArray(map.matrix, map.height);
    delDynamic2DArray(key.matrix, keyDim);

    return 0;
}

int charArrToInt(char charArr[])
// Char Array to Integer convertor
{
    int i = 0, number = 0;
    while (charArr[i] != '\0')
    {
        number = 10 * number + charArr[i] - '0';
        ++i;
    }
    return number;
}

int stringToInt(std::string string)
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

int *getDims(char charArr[], int *dims)
// Decyphers char array heightxwidth into [height, width]
{
    int row = 0, col = 0;
    bool xFlag = false;
    int i = 0;

    while (true)
    {
        if (charArr[i] == '\0')
            break;

        if (charArr[i] == 'x')
        {
            ++i;
            xFlag = true;
            continue;
        }

        if (!xFlag)
            row = row * 10 + charArr[i] - '0';
        else
            col = col * 10 + charArr[i] - '0';
        ++i;
    }

    dims = new int[2];
    dims[0] = row;
    dims[1] = col;
    return dims;
}

int **dynamic2DArrayAlloc(int **A, int nRow, int nCol)
// Allocates 2d int array dynamically
{
    A = new int *[nRow];
    for (int i = 0; i < nRow; ++i)
        A[i] = new int[nCol];

    return A;
}

int *splitLine(std::string line, int itemNum)
// splits string line, items seperated by blank spaces, into int array
{
    int *A = new int[itemNum];
    int i = 0;
    int start = 0;
    int end = line.find(' ');

    while (end != std::string::npos) // string::npos used to find index of last blank space
    {
        A[i++] = stringToInt(line.substr(start, end - start));
        start = end + 1;
        end = line.find(' ', start);
    }
    A[i] = stringToInt(line.substr(start, line.length() - start));

    return A;
}

int **fileReader(std::string inputFile, int nRow, int nCol, int **A)
// reads input file, and stores values into 2d array
{
    A = dynamic2DArrayAlloc(A, nRow, nCol);

    std::ifstream file(inputFile.c_str());
    std::string line;

    int rowi = 0;
    while (std::getline(file, line, '\n'))
    {
        int *numbers = splitLine(line, nCol);

        for (int coli = 0; coli < nCol; ++coli)
        {
            A[rowi][coli] = numbers[coli];
        }
        ++rowi;
    }

    file.close();

    return A;
}

void searchTreasure(struct Map &map, struct Key &key, int x, int y, bool found, std::ostream &stream)
// search function, used to find treasure recursively
{
    // found is a flag var used to determine search is ended
    if (found)
        return;
    else
    {
        int sum = 0;
        // matrix multiplication
        for (int i = -key.mid; i <= key.mid; ++i)
        {
            for (int j = -key.mid; j <= key.mid; ++j)
            {
                sum += map.matrix[y + i][x + j] * key.matrix[key.mid + i][key.mid + j];
            }
        }

        stream << y << ',' << x << ':';

        if (sum < 0)
            sum = sum % 5 + 5; // if sum is negative, -4 <= sum % 5 <= 0, thus 5 added to make it positive

        switch (sum % 5)
        {
        case 0:
            stream << sum;
            searchTreasure(map, key, x, y, true, stream);
            break;
        case 1:
            stream << sum << std::endl;
            if (y - key.dim < 0)
                searchTreasure(map, key, x, y + key.dim, false, stream);
            else
                searchTreasure(map, key, x, y - key.dim, false, stream);
            break;
        case 2:
            stream << sum << std::endl;
            if (y + key.dim > map.height)
                searchTreasure(map, key, x, y - key.dim, false, stream);
            else
                searchTreasure(map, key, x, y + key.dim, false, stream);
            break;
        case 3:
            stream << sum << std::endl;
            if (x + key.dim > map.width)
                searchTreasure(map, key, x - key.dim, y, false, stream);
            else
                searchTreasure(map, key, x + key.dim, y, false, stream);
            break;
        case 4:
            stream << sum << std::endl;
            if (x - key.dim < 0)
                searchTreasure(map, key, x + key.dim, y, false, stream);
            else
                searchTreasure(map, key, x - key.dim, y, false, stream);
            break;
        default:
            // default block should NOT be executed normally
            std::cout << "[ERROR] Unknown ERROR during treasure hunt" << std::endl;
            return;
        }
    }
}

void fileWriter(std::string outputFile, struct Map &map, struct Key &key)
// writes into output file
{
    std::ofstream file(outputFile.c_str());

    searchTreasure(map, key, key.mid, key.mid, false, file);

    file.close();
}

void delDynamic2DArray(int **A, int nRow)
// deletes dynamically allocated 2d arrays
{
    for (int i = 0; i < nRow; ++i)
    {
        delete[] A[i];
    }
    delete[] A;
}