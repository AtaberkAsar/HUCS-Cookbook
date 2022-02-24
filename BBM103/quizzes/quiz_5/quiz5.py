import sys

operands = []
comparison_data = []

def TryNotToGoBoom():
    for i in range(max(len(operands), len(comparison_data))):
        print("------------")
        try:
            """try: # checks if comparison data is longer than operands, error not specified in pdf, if occurs it raises an exception instead of index error
                operands[i] += ''
            except:
                raise Exception""" # I removed it as it is the same as no input, so i guess raising an index error was right

            text = ''
            for j in operands[i]:
                text += j + ' '
            text = text[:-1]

            if (len(operands[i]) < 4): # added since line 26 raises a value error, not index error
                raise IndexError

            if (len(operands[i]) > 4): # added since line 26 raises value error if more than 4 input
                raise Exception
            
            div_val, nondiv_val, from_val, to_val =  map(lambda x : int(float(x)), operands[i])
            data = []

            for j in range(from_val, to_val + 1):
                if((j % div_val == 0) and (j % nondiv_val != 0)):
                    data.append(str(j))
            
            assert data == comparison_data[i]

            print("My results:\t\t", end = '')
            for j in data:
                print(' ' + j, end = '')
            print("\nResults to compare:\t", end = '')
            for j in comparison_data[i]:
                print(' ' + j, end = '')
            print("\nGoool!!!")
        
        except ValueError:
            print(f"ValueError: only numeric input is accepted.\nGiven Input: {text}")
        
        except IndexError:
            print(f"IndexError: number of operands less than expected.\nGiven Input: {text}")
        
        except ZeroDivisionError:
            print(f"ZeroDivisionError: You can’t divide by 0.\nGiven Input: {text}")

        except AssertionError:
            print("My results:\t\t", end = '')
            for j in data:
                print(' ' + j, end = '')
            print("\nResults to compare:\t", end = '')
            for j in comparison_data[i]:
                print(' ' + j, end = '')
            print("\nAssertionError: results don’t match.")
        
        except:
            print("kaBOOM: run for your life!")


try:
    with open(sys.argv[1]) as f:
        for line in f:
            operands.append(line.strip().split())
    with open(sys.argv[2]) as f:
        for line in f:
            comparison_data.append(line.strip().split())
    TryNotToGoBoom()
except IndexError:
    print("IndexError: number of input files less than expected.")
except IOError as ioError:
    print(f"IOError: cannot open {ioError.filename}")
finally:
    print("˜ Game Over ˜")
