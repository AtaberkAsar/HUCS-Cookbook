import sys

given_set = sys.argv[1]

given_set = given_set.strip('"')
toList = [int(i) for i in given_set.split(",")]
toList = [i for i in toList if i>0]
toList.sort()
turn = 0
line = ""

def luckynums(toList, turn):
    i = toList[turn]
    deleter = i if i>1 else 2
    if deleter >= len(toList):
        return toList
    del toList[deleter-1::deleter]

    turn += 1
    return luckynums(toList, turn)

toList = luckynums(toList, turn)
for i in toList:
    line += f"{str(i)} "
print(line[:-1])
