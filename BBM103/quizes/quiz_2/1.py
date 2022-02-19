import sys

a = int(sys.argv[1])
b = int(sys.argv[2])
c = int(sys.argv[3])

discr = b**2 - 4*a*c
if(discr < 0):
    print("There is no real solution")
else:
    root_1 = (-b + discr**0.5)/(2*a)
    if(discr == 0):
        print("There are two repeated real number solutions\nSolution(s):", root_1, root_1)
    else:
        root_2 = (-b - discr**0.5)/(2*a)
        print("There are two solutions\nSolution(s):", root_1, root_2)
