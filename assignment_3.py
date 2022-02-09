import os

import splitfolders

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers, regularizers
from tensorflow.keras.preprocessing.image import ImageDataGenerator

from sklearn.metrics import confusion_matrix
from tensorflow.keras.models import Model

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

# Using GPU
physical_devices = tf.config.list_physical_devices("GPU")
tf.config.experimental.set_memory_growth(physical_devices[0], True)

root = 'C:/Users/usr/Desktop/Dataset/'
save_root = 'C:/Users/usr/Desktop/My_Dataset/'

if not os.path.exists(save_root + 'train/'):
    splitfolders.ratio(root, save_root, seed = 42, ratio = (.65, .15, .2))

# Confusion Matrix
def plot_confusion_matrix(cm, classes, normalize = True, title = 'Confusion Matrix', cmap = plt.cm.Reds):
    size = len(classes) / 2
    plt.figure(figsize = (size, size))
    plt.imshow(cm, interpolation = 'nearest', cmap = cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation = 45)
    plt.yticks(tick_marks, classes)

    if normalize:
        cm = cm.astype('float') / (cm.sum(axis = 1)[:, np.newaxis] + 1)
        cm = np.around(cm, decimals = 2)
        print("Normalized Confusion Matrix")
    else:
        print("Confusion Matrix, without Normalization")

    plt.tight_layout()
    plt.ylabel('True Label')
    plt.xlabel('Predicted Label')
    plt.show()

classes = "airport_inside, artstudio, bakery, bar, bathroom, bedroom, bookstore, bowling, buffet, casino, church_inside, classroom, closet, clothingstore, computerroom"
classes = classes.split(', ')

train_gen = ImageDataGenerator(
    rescale = 1/255.,
    data_format = 'channels_last',
    )
valid_gen = ImageDataGenerator(
    rescale = 1/255.,
    data_format = 'channels_last',
    )   
test_gen = ImageDataGenerator(
    rescale = 1/255.,
    data_format = 'channels_last',
    )

batch_size = 64

ds_train = train_gen.flow_from_directory(
    'C:/Users/usr/Desktop/My_Dataset/train',
    target_size = (128, 128), # (64, 64) if not ResNet50
    batch_size = batch_size,
    class_mode = 'categorical',
    color_mode = 'rgb',
)

ds_valid = valid_gen.flow_from_directory(
    'C:/Users/usr/Desktop/My_Dataset/val',
    target_size = (128, 128), # (64, 64) if not ResNet50
    batch_size = batch_size,
    class_mode = 'categorical',
    color_mode = 'rgb'
)

ds_test = test_gen.flow_from_directory(
    'C:/Users/usr/Desktop/My_Dataset/test',
    target_size = (128, 128), # (64, 64) if not ResNet50
    batch_size = batch_size,
    class_mode = 'categorical',
    color_mode = 'rgb',
    shuffle = False
)

num_train = ds_train.samples
num_valid = ds_valid.samples
num_test = ds_test.samples

class CNNBlock(layers.Layer):
    def __init__(self, out_channels, kernel_size = 3): # 13
        super(CNNBlock, self).__init__()
        self.conv = layers.Conv2D(out_channels, kernel_size, padding = 'same', kernel_regularizer = regularizers.l2(0.001)) #l2 0.001  #same
        self.bn = layers.BatchNormalization()

    def call(self, input_tensor, training = False):
        x = self.conv(input_tensor)
        x = self.bn(x, training = training)
        x = tf.nn.relu(x)
        return x
"""
i = keras.Input(shape = (64, 64, 3))

Layer1 = layers.Conv2D(64, 3, padding = 'same', activation = 'relu')(i)

Layer2 = layers.Conv2D(64, 3, padding = 'same', activation = 'relu')(Layer1)
Layer2 += Layer1
Layer2 = layers.MaxPooling2D()(Layer2)

Layer3 = layers.Conv2D(64, 5, padding = 'same', activation = 'relu')(Layer2)
Layer3 += Layer2
Layer3 = layers.MaxPooling2D()(Layer3)

Layer4 = layers.Conv2D(64, 5, padding = 'same', activation = 'relu')(Layer3)
Layer4 += Layer3
Layer4 = layers.MaxPooling2D()(Layer4)

Layer5 = layers.Conv2D(64, 3, padding = 'same', activation = 'relu')(Layer4)
Layer5 += Layer4
Layer5 = layers.MaxPooling2D()(Layer5)

x = layers.GlobalAveragePooling2D()(Layer5)
#x = layers.Flatten()(Layer5)
#x = layers.Dropout(0.2)(x)
x = layers.Dense(32, activation = 'relu')(x)
x = layers.Dropout(0.5)(x)
output = layers.Dense(15, activation = 'softmax')(x)

model = Model(inputs = i, outputs = output)

print(model.summary())

model.compile(
    optimizer = keras.optimizers.Nadam(learning_rate = 0.0003),
    loss = tf.keras.losses.CategoricalCrossentropy(),
    metrics = ['accuracy']
)

checkpoint_path = "training_2/cp.ckpt"
checkpoint_dir = os.path.dirname(checkpoint_path)

# Create a callback that saves the model's weights
cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=checkpoint_path,
                                                 save_weights_only=True,
                                                 verbose=1)

r = model.fit(ds_train, validation_data = ds_valid, epochs = 100)
"""

#  ResNet50V2

model = keras.applications.ResNet50V2(include_top = True)
model.trainable = False
base_input = model.layers[0].input
base_output = model.layers[-2].output
final_output = layers.Dense(15)(base_output)


new_model = Model(inputs = base_input, outputs = final_output)

new_model.compile(
    optimizer = keras.optimizers.Adam(learning_rate = 3e-4),
    loss = keras.losses.CategoricalCrossentropy(from_logits = True),
    metrics = ['accuracy']
    )

# change new_model to model if not ResNet50
r = new_model.fit(ds_train,
                    batch_size = batch_size,
                    steps_per_epoch = num_train // batch_size,
                    validation_data = ds_valid,
                    validation_steps = num_valid // batch_size,
                    epochs = 10
                    )

plt.plot(r.history['accuracy'], label = 'acc')
plt.plot(r.history['val_accuracy'], label = 'val_acc')
plt.legend()
plt.show()

plt.plot(r.history['loss'], label = 'loss')
plt.plot(r.history['val_loss'], label = 'val_loss')
plt.legend()
plt.show()

test_acc = new_model.evaluate(ds_test,
                                batch_size = batch_size,
                                steps = num_test // batch_size
                                )
                                
predictions = new_model.predict(ds_test, batch_size = batch_size).argmax(axis = 1)

cm = confusion_matrix(ds_test.classes, predictions)

plot_confusion_matrix(cm, classes)