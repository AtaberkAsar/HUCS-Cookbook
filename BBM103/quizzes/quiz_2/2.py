import sys

givenString = sys.argv[1]

givenString = givenString.strip('"')
numbers = givenString.split(",")
sumAll, sumEven = 0, 0
evenNumbers = '"'

for i in numbers:
    i = int(i)
    if(i>=0):
        sumAll += i
        if (i%2 == 0):
            sumEven += i
            evenNumbers +=  str(i) + " "

evenNumbers = evenNumbers.rstrip()
evenNumbers += '"'
evenNumbers = evenNumbers.replace(" ",",")

print("Even Numbers:", evenNumbers)
print("Sum of Even Numbers:", sumEven)
print("Even Number Rate:", sumEven/sumAll)
