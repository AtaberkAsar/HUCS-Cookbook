# Ataberk ASAR

print("""<-----Rules----->
1. BRUSH DOWN
2. BRUSH UP
3. VEHICLE ROTATES RIGHT
4. VEHICLEROTATES LEFT
5. MOVE UP TO X 
6. JUMP
7. REVERSE DIRECTION
8. VIEW THE MATRIX
0. EXIT
Please enter the commands with a plus sign (+) between them.""")

while True:
    flag = False
    commands = input()
    commands = commands.split("+")
    matrix_index = int(commands.pop(0))
    room = [[0 for i in range(matrix_index)] for j in range(matrix_index)]

    for i in commands:
        if (i[0:2] != '5_'):
            i = int(i)
            if((i<0) or (i>8)):
                print("You entered an incorrect command. Please try again!")
                flag = True

    if(flag):
        continue

    x, y = 0, 0 # initial coordinates
    brushDown = False # initial brush state
    facing = 0 # 0:right, 1:up, 2:left, 3:down; shows the orientation of car
    for i in commands:
        if(i == "1"):
            brushDown = True
            room[y][x] = 1

        elif(i == "2"):
            brushDown = False

        elif(i == "3"):
            facing = (facing-1)%4

        elif(i == "4"):
            facing = (facing+1)%4

        elif(i[0] == "5"):
            target = int(i[2:])
            for j in range(target):
                if(facing == 0):
                    x += 1
                    x = x%matrix_index
                elif(facing == 1):
                    y -= 1
                    y = y%matrix_index
                elif(facing == 2):
                    x -= 1
                    x = x%matrix_index
                else:
                    y += 1
                    y = y%matrix_index
                if(brushDown):
                    room[y][x] = 1

        elif(i == "6"):
            for k in range(3):
                if(facing == 0):
                    x += 1
                    x = x%matrix_index
                elif(facing == 1):
                    y -= 1
                    y = y%matrix_index
                elif(facing == 2):
                    x -= 1
                    x = x%matrix_index
                else:
                    y += 1
                    y = y%matrix_index
            brushDown = False

        elif(i == "7"): 
                if(facing == 0):
                    facing = 2
                elif(facing == 1):
                    facing = 3
                elif(facing == 2):
                    facing = 0 # Ataberk ASAR
                else:
                    facing = 1

        elif(i == "8"): 
            for l in range(matrix_index + 2):
                for m in range(matrix_index + 2):
                    if(l%(matrix_index + 1) == 0):
                        print("+", end="")
                    elif(m%(matrix_index + 1) == 0):
                        print("+", end="")
                    elif(room[l-1][m-1] == 1):
                        print("*", end=""),
                    else:
                        print(" ", end=""),
                print("")

        else:
            break
    break

#TEST COMMANDS
#20+5_5+3+5_1+3+1+5_4+4+5_7+4+5_4+4+5_3+4+5_2+7+6+8+0
#9+3+1+5_3+4+5_4+4+5_3+7+6+3+5_2+4+1+5_3+2+5_2+1+3+5_9+8+0
#5+3+1+5_3+1+3+5_2+6+2+5_2+1+7+5_4+5_3+8+0
#7+5_2+1+6+4+5_5+3+5_2+6+2+7+4+1+5_4+8+0
# Ataberk ASAR
