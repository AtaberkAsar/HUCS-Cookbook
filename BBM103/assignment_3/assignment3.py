import sys

network = dict()
with open(sys.argv[1]) as f:
    for line in f:
        items = line.strip().split(':')
        key, values = items[0], items[1]
        network[key] = values.split()

possible_friends = set()

output = open('output.txt', 'w')

def ANU(new_user):
    if (new_user in network):
        output.write("ERROR: Wrong input type! for 'ANU'! -- This user already exists!!\n")
    else:
        network[new_user] = []
        output.write(f"User '{new_user}' has been added to the social network successfully\n")

def DEU(username):
    if (username in network):
        network.pop(username)
        for i in network:
            if(username in network[i]):
                network[i].remove(username)
        output.write(f"User '{username}' and his/her all relations have been removed successfully\n")
    else:
        output.write(f"ERROR: Wrong input type! for 'DEU'!--There is no user named '{username}'!!\n")

def ANF(source_user, target_user):
    if (source_user in network):
        if(target_user in network):
            if(target_user in network[source_user]):
                output.write(f"ERROR: A relation between '{source_user}' and '{target_user}' already exists!!\n")
            else:
                network[source_user].append(target_user)
                network[target_user].append(source_user)
                output.write(f"Relation between '{source_user}' and '{target_user}' has been added successfully\n")
        else:
            output.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{target_user}' found!!\n")
    elif(target_user in network):
        output.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{source_user}' found!!\n")
    else:
        output.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{source_user}' and '{target_user}' found!\n")

def DEF(source_user, target_user):
    if (source_user in network):
        if(target_user in network):
            if(target_user in network[source_user]):
                network[source_user].remove(target_user)
                network[target_user].remove(source_user)
                output.write(f"Relation between '{source_user}' and '{target_user}' has been deleted successfully\n")
            else:
                output.write(f"ERROR: No relation between '{source_user}' and '{target_user}' found!!\n")
        else:
            output.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{target_user}' found!!\n")
    elif(target_user in network):
        output.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{source_user}' found!!\n")
    else:
        output.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{source_user}' and '{target_user}' found!!\n")

def CF(username):
    if(username in network):
        output.write(f"User '{username}' has {len(network[username])} friends\n")
    else:
        output.write(f"ERROR: Wrong input type! for 'CF'! -- No user named '{username}' found!\n")

def printFPF(username, maximum_distance):
    global possible_friends
    if(maximum_distance == 0):
        return possible_friends

    if(maximum_distance > 0):
        for i in network[username]:
            possible_friends.add(i)
            possible_friends = printFPF(i, maximum_distance -1)
        return possible_friends

def FPF(username, maximum_distance):
    maximum_distance = int(maximum_distance)
    global possible_friends
    if(username in network):
        if(maximum_distance > 3 or maximum_distance < 1):
            output.write(f"ERROR: Wrong input type! for 'FPF'! -- Illegal maximum distance {maximum_distance}!!\n")
            return None
        printFPF(username, maximum_distance)
        possible_friends.discard(username)
        output.write(f"User '{username}' has {len(possible_friends)} possible friends when maximum distance is {maximum_distance}\n")
        output.write(f"These possible friends: {{{str(sorted(possible_friends))[1:-1]}}}\n")
        possible_friends.clear()
    else:
        output.write(f"ERROR: Wrong input type! for 'FPF'! -- No user named '{username}' found!\n")

def SF(username, MD):
    MD = int(MD)
    mutual_friends = dict()
    mutual_friends_survived_threshold = set()
    if(username in network):
        if(MD<2 or MD>3):
            output.write("Error: Mutually Degree cannot be less than 1 or greater than 4\n")
            return None
        for i in network[username]:
            for j in network:
                if(j in network[i]):
                    mutual_friends[j] = mutual_friends.get(j, 0) + 1
        for i in mutual_friends:
            if(mutual_friends[i] >= MD):
                mutual_friends_survived_threshold.add(i)
        mutual_friends_survived_threshold.discard(username)
        if(len(mutual_friends_survived_threshold) == 0):
            output.write(f"No possible friend in the Suggestion List for '{username}' (when MD is {MD})\n")
            return None
        output.write(f"Suggestion List for '{username}' (when MD is {MD}):\n")
        text = f"The suggested friends for '{username}':"
        for i in (sorted(mutual_friends_survived_threshold)):
            output.write(f"'{username}' has {mutual_friends[i]} mutual friends with '{i}'\n")
            text += f"'{i}'" + ","
        output.write(text[:-1] + "\n")
    else:
        output.write(f"Error: Wrong input type! for 'SF'! -- No user named '{username}' found!!\n")

with open(sys.argv[2]) as f:
    for line in f:
        items = line.strip().split()
        if (len(items) == 2):
            globals()[items[0]](items[1])
        else:
            globals()[items[0]](items[1], items[2])

output.close()