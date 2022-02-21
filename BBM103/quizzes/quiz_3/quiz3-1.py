import sys

x = int(sys.argv[1])
n = int(sys.argv[2])

num = x**n
line = f"{x}^{n} = {num} ="

def adder(num):
    global line
    if(num<10):
        return line[:-2]
    num = str(num)
    my_sum = 0
    for i in range(len(num)):
        line += f" {num[i]} +"
        my_sum += int(num[i])
    line = line[:-1] + "= " + str(my_sum) + " ="
    if(my_sum > 9):
        my_sum = adder(my_sum)
    return line[:-2]

print(adder(num))
