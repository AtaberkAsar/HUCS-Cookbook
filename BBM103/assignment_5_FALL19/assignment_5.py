# Ataberk ASAR

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

dataset = pd.read_csv("rover_as1\\brandnew-breast-cancer-wisconsin.csv")
dataset.Bare_Nuclei = pd.to_numeric(dataset.Bare_Nuclei, errors='coerce')
dataset.fillna(round(dataset["Bare_Nuclei"].mean(), 2), inplace = True)

border = int(len(dataset)*0.8)
trainSet = dataset[:border]
testSet = dataset[border:]

weights = np.random.default_rng().uniform(-1, 1, (1, 9))[0]
epochs = 7
epoch_loss_train = []
epoch_acc_train = []
epoch_loss_test = []
epoch_acc_test = []
acc_test = [0]*140
acc_train = [0]*559

def get_weighted_sum(training_inputs, weights):
    return np.dot(training_inputs, weights)
def sigmoid(outputs):
    return (1/(1 + np.exp(-0.005*outputs)))
def sigmoid_der(outputs):
    return 0.005*outputs*(1 - outputs)
def tunings(loss, outputs):
    return loss*sigmoid_der(outputs)
def update_weigths(training_inputs, arrangements):
    return np.dot(training_inputs.T, arrangements)

def trainer(trainSet, weights, individual_loss_train):
    for i in range(len(trainSet)):
        training_inputs = trainSet.loc[i][1:-1]
        training_outputs = trainSet.loc[i][-1]
        outputs = get_weighted_sum(training_inputs, weights)
        outputs = sigmoid(outputs)
        loss = training_outputs - outputs
        individual_loss_train.append(loss)
        arrangements = tunings(loss, outputs)
        weights += update_weigths(training_inputs, arrangements)
        value = 1.0 if(outputs > 0.61) else 0.0
        acc_train[i] = value
        # Ataberk ASAR
def tester(testSet, weights, individual_loss_test):
    for i in range(len(testSet)):
        testing_inputs = testSet.loc[i+559][1:-1]
        testing_outputs = testSet.loc[i+559][-1]
        outputs = get_weighted_sum(testing_inputs, weights)
        outputs = sigmoid(outputs)
        loss = testing_outputs - outputs
        individual_loss_test.append(loss)
        value = 1.0 if(outputs > 0.61) else 0.0
        acc_test[i] = value

def run(trainSet, testSet, weights, epochs):
    for e in range(epochs):
        individual_loss_train = []
        individual_loss_test = []

        trainer(trainSet, weights, individual_loss_train)
        average_loss_train = sum(individual_loss_train)/len(individual_loss_train)
        epoch_loss_train.append(average_loss_train)
        accuracy_train = np.where(acc_train == trainSet["Class"], 1, 0)
        ave_acc_train = accuracy_train.sum()/len(accuracy_train)
        epoch_acc_train.append(ave_acc_train)

        tester(testSet, weights, individual_loss_test)
        average_loss_test = sum(individual_loss_test)/len(individual_loss_test)
        epoch_loss_test.append(average_loss_test)
        accuracy_test = np.where(acc_test == testSet["Class"], 1, 0)
        ave_acc_test = accuracy_test.sum()/len(accuracy_test)
        epoch_acc_test.append(ave_acc_test)

run(trainSet, testSet, weights, epochs)

plt.matshow(trainSet.corr(), cmap='jet')
plt.colorbar()
plt.savefig("1_Corrolations.pdf")

epoch_loss = pd.DataFrame({"Training":epoch_loss_train, "Testing":epoch_loss_test})
epoch_loss_plot = epoch_loss.plot(ylabel = "Loss", xlabel = "Epochs", grid=True).get_figure()
epoch_loss_plot.savefig("1_Loss.pdf")

epoch_acc = pd.DataFrame({"Training":epoch_acc_train, "Testing":epoch_acc_test})

# Ataberk ASAR
epoch_acc_plot = epoch_acc.plot(ylabel = "Accuracy", xlabel = "Epochs", grid=True).get_figure()
epoch_acc_plot.savefig("1_Acc.pdf")
