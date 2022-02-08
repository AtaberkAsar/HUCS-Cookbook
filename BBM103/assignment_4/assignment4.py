# Ataberk ASAR

import sys

game = list()

with open(sys.argv[1]) as f:
    for line in f:
        game.append(line.strip().split())

gameOver = False
score = 0
weights = {
    "B" : 9,
    "G" : 8,
    "W" : 7,
    "Y" : 6,
    "R" : 5,
    "P" : 4,
    "O" : 3,
    "D" : 2,
    "F" : 1,
    "X" : 0,
    0 : 0
}

def LetsPlay():
    global gameOver
    while not gameOver:
        region = []
        region, gameOver = checkGameOver(region)
        if(gameOver):
            break
        move(region)

def checkGameOver(region):
    global gameOver
    game_map = []
    for i in range(len(game)): # copying my nested list for making changes on it without changing the original game board
        row = []
        for j in game[i]:
            row.append(j)
        game_map.append(row)

    for i in range(len(game_map)): # dividing my game map into regions based on the possible patterns
        for j in range(len(game_map[0])):
            target = game_map[i][j]
            if(target == 0): # controlS that the ball is not found in a previous pattern already
                continue

            balls = [[i, j]]
            last_balls = [[i, j]]

            if(target == 'X'): # specific region pattern for a bomb on the board
                for k in range(len(game)):
                    balls.append([k, j])
                for k in range(len(game[0])):
                    balls.append([i, k])
                balls_WDuplicates, balls = balls, []
                for k in balls_WDuplicates:
                    if(not k in balls):
                        balls.append(k)
                region.append(balls)
                continue # continue after bomb pattern

            def regionDivider(): # finds pattern for not a bomb
                nonlocal balls # balls already in the region
                nonlocal last_balls # balls added to the region last
                this_balls = [] # new balls that will be added to a region

                if(len(last_balls) == 0): # if no new balls last iteration, terminate
                    return None

                for i,j in last_balls:
                    target = game_map[i][j]
                    for k,l in [[-1, 0], [0, -1], [1, 0], [0, 1]]: # check the above, left, below and right side of the current ball
                        try:
                            if(game_map[i + k][j + l] == target):
                                if((i + k == -1) or (j + l == -1)): # eliminates matches in -1 indexes in nested list, ie. ignore matches across game map, top to bottom or left to right
                                    continue
                                this_balls.append([i + k, j + l])
                        except IndexError:
                            pass
                for k in balls: # if new balls are already in the region, ignore them
                    if (k in this_balls):
                        this_balls.remove(k)
                for k in this_balls: # add new balls to the region
                    balls.append(k)
                last_balls = this_balls

                regionDivider()
                return None

            regionDivider()
            region.append(balls) # add new pattern to regions
            for k,l in balls: # crossing out the added balls to a pattern
                game_map[k][l] = 0

    for i in region: # checking gameover here
        gameOver = True
        if((len(i) > 1) or (['X'] in game)): # if there is any pattern that has more than one ball game is not over
            gameOver = False
            break
    if(len(game) == 0): # if game map is empty
        gameOver = True
    return region, gameOver

def move(region):
    global game

    y, x = map(lambda coordinate:int(coordinate), input("Please enter a row and column number: ").strip().split())
    print()

    try:
        target = game[y][x]
    except IndexError:
        print("Please enter a valid size!\n")
        return None

    if (target == 0): # no balls on selected row and column
        print("Please enter a valid size!\n")
        return None

    ballPopper(region, y, x, target)

    reshaper()

    printMap()

    printScore()

def ballPopper(region, y, x, target):
    global game
    global score

    for i in region:
        if([y, x] in i): # find a pattern that includes the ball
            if(target == 'X'): # if it is bomb
                for j,k in i:
                    if(game[j][k] == 'X' and (j != y or k != x)): # if another bomb in range
                        i.remove([j, k])
                        ballPopper(region, j, k, target)
                    score += weights[game[j][k]]
                    game[j][k] = 0
                break
            else: # if it is ball
                if(all([game[j][k] == target for j,k in i])): # checks if found pattern is not a bomb pattern
                    if(len(i) == 1): # checks if it is a lone bomb
                        break
                    for j,k in i:
                        score += weights[game[j][k]]
                        game[j][k] = 0
                    break

def reshaper():
    global game

    for k in range(len(game) - 1): # needed atmost row_num - 1 iteration to transfer an empty cell from bottom to top
        for i in range(len(game)):
            for j in range(len(game[0])):
                try:
                    if(game[i + 1][j] == 0): # if below of a ball is empty, transfer that ball one row below
                        game[i][j], game[i + 1][j] = 0, game[i][j]
                except IndexError:
                    pass
    
    i = 0
    while i < len(game): # cross out a row of zeros
        if(all([ball == 0 for ball in game[i]])):
            del game[i]
            continue
        i +=1

    game = TGame(game) # transpose the game board

    i = 0
    while i < len(game): # cross out a row of zeros in transposed game board, ie. cross out a column of zeros in original game board
        if(all([ball == 0 for ball in game[i]])):
            del game[i]
            continue
        i += 1

    game = TGame(game) # transpose the transposed game board, obtain the original board

def TGame(game):
    try:
        row_num, column_num = len(game), len(game[0])
        transposed_game = []

        for i in range(column_num):
            row = []
            for j in range(row_num):
                row.append(game[j][i])
            transposed_game.append(row)
        return transposed_game
    except IndexError: # if game map is empty, no more balls...
        return game

def printMap():
    for i in range(len(game)):
        for j in game[i]:
            print(f"{' ' if j == 0 else j}", end = ' ')
        print()
    print()

def printScore():
    print(f"Your score is: {score} \n")

printMap()
printScore()
LetsPlay()
print("Game over!")

# Ataberk ASAR
