# Ataberk ASAR
# 2210356135
# python q2.py sequences.txt global BLOSUM62.txt -10 -5

import sys


def read_sequences(file):
    with open(file) as f:
        f.readline()
        seq1 = f.readline().strip()
        f.readline()
        seq2 = f.readline().strip()
    return seq1, seq2


def read_scoring_matrix(file):
    scoring_matrix = dict()
    key = list()
    with open(file) as f:
        for line in f:
            if(line.startswith('#')):
                continue
            if(len(key) == 0):
                key = line.strip().split()
                continue
            scores = line.strip().split()
            for i in range(len(key)):
                scoring_matrix[scores[0]] = scoring_matrix.get(
                    scores[0], dict())
                scoring_matrix[scores[0]].update({key[i]: int(scores[i + 1])})

    return key, scoring_matrix


def alignment_finder(seq1, seq2, algorithm, scoring_matrix, open_penalty, extend_penalty):
    seq1_len = len(seq1)
    seq2_len = len(seq2)
    partial_scores_table = [
        [0 for i in range(seq1_len + 1)] for j in range(seq2_len + 1)]
    path_table = [
        ['' for i in range(seq1_len + 1)] for j in range(seq2_len + 1)]

    # Initializing partial scores table
    partial_scores_table[0][1] = open_penalty if algorithm == 'global' else 0
    partial_scores_table[1][0] = open_penalty if algorithm == 'global' else 0
    for i in range(seq1_len):
        partial_scores_table[0][i + 1] = (open_penalty +
                                          i * extend_penalty) if algorithm == 'global' else 0
    for i in range(seq2_len):
        partial_scores_table[i + 1][0] = (open_penalty +
                                          i * extend_penalty) if algorithm == 'global' else 0

    # Initializing path table
    path_table[0][0] = 'S'  # START
    for i in range(seq1_len):
        path_table[0][i + 1] = 'L'
    for i in range(seq2_len):
        path_table[i + 1][0] = 'U'

    # Traverse matrix
    max_val = 0
    max_x = 0
    max_y = 0

    for i in range(1, seq2_len + 1):
        for j in range(1, seq1_len + 1):
            score = scoring_matrix[seq2[i-1]][seq1[j-1]]

            right = partial_scores_table[i][j-1] + \
                (extend_penalty if 'L' in path_table[i][j-1] else open_penalty)
            down = partial_scores_table[i-1][j] + \
                (extend_penalty if 'U' in path_table[i-1][j] else open_penalty)
            cross = partial_scores_table[i-1][j-1] + score

            value = max(right, down, cross) if algorithm == 'global' else max(
                right, down, cross, 0)
            partial_scores_table[i][j] = value

            if(value >= max_val and algorithm == 'local'):
                max_val = value
                max_x = j
                max_y = i
            elif(algorithm == 'global'):
                max_val = value
                max_x = j
                max_y = i

            if(partial_scores_table[i][j] == right):
                path_table[i][j] += 'L'
            if(partial_scores_table[i][j] == down):
                path_table[i][j] += 'U'
            if(partial_scores_table[i][j] == cross):
                path_table[i][j] += 'C'

    return partial_scores_table, path_table, max_val, max_x, max_y


def aligner(seq1, seq2, algorithm, path_table, x, y):
    seq1_iterator = x
    seq2_iterator = y
    aligned_seq1 = ''
    aligned_seq2 = ''

    # TODO MORE THAN ONE POSSIBLE SEQ

    while((algorithm == 'global' and path_table[seq2_iterator][seq1_iterator] != 'S') or
          (algorithm == 'local' and path_table[seq2_iterator][seq1_iterator] != '' and seq2_iterator > 0 and seq1_iterator > 0)):

        path_to = path_table[seq2_iterator][seq1_iterator]
        if('C' in path_to):
            aligned_seq1 = seq1[seq1_iterator - 1] + aligned_seq1
            aligned_seq2 = seq2[seq2_iterator - 1] + aligned_seq2
            seq1_iterator -= 1
            seq2_iterator -= 1
            continue
        if('L' in path_to):
            aligned_seq1 = seq1[seq1_iterator - 1] + aligned_seq1
            aligned_seq2 = '-' + aligned_seq2
            seq1_iterator -= 1
            continue
        if('U' in path_to):
            aligned_seq1 = '-' + aligned_seq1
            aligned_seq2 = seq2[seq2_iterator - 1] + aligned_seq2
            seq2_iterator -= 1
            continue

    return aligned_seq1, aligned_seq2


def sequence_printer(seq1, seq2, score):
    counter = 0

    print(seq1)
    for i in range(len(seq1)):
        if(seq1[i] == seq2[i]):
            counter += 1
            print('|', end='')
        else:
            print(' ', end='')
    print('\n' + seq2)

    print(f'Alignment score: {float(score)}')
    print(
        f'Identity value: {counter}/{len(seq1)} ({round(counter/len(seq1)*100, 1)}%)')


try:
    seq1, seq2 = read_sequences(sys.argv[1])  # filename

    alignment_algorithm = sys.argv[2].lower()  # global or local
    assert alignment_algorithm in ['global', 'local']

    scoring_matrix_file = sys.argv[3]  # filename

    gap_opening_penalty = int(sys.argv[4])

    gap_extension_penalty = int(sys.argv[5])

    outputfile = sys.argv[6]

except AssertionError:
    raise RuntimeError('Alignment algorithm must be either local or global')
except IndexError:
    outputfile = 'false'


key, scoring_matrix = read_scoring_matrix(scoring_matrix_file)

partial_scores_table, path_table, max_val, max_x, max_y = alignment_finder(seq1, seq2, alignment_algorithm, scoring_matrix,
                                                                           gap_opening_penalty, gap_extension_penalty)

if(outputfile != 'false'):
    with open(outputfile, 'w+') as f:
        for i in partial_scores_table:
            for j in i:
                f.write(f'{j}\t\t')
            f.write('\n')

        f.write('\n\n\n')

        for i in path_table:
            for j in i:
                f.write(f'{j}\t\t')
            f.write('\n')

seq1, seq2 = aligner(seq1, seq2, alignment_algorithm, path_table, max_x, max_y)
sequence_printer(seq1, seq2, max_val)
