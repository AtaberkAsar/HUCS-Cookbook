import sys

messages = dict()
output = open(sys.argv[2], 'w')
with open(sys.argv[1]) as f:
    for line in f:
        message_line = line.strip().split('\t')
        messages[message_line[0]] = messages.get(message_line[0], dict())
        messages[message_line[0]].update({message_line[1] : message_line[2]})
    message_number = 1
    for i in sorted(messages):
        output.write(f"Message\t{message_number}\n")
        message_number += 1
        for j in sorted(messages[i]):
            output.write(str(i) + '\t' + str(j) + '\t' + messages[i][j] + "\n")
    output.close()


