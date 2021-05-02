"""
Filename: brute_force_PIN.py
Author: Patrick Walsh
Date: 5/2/2021
Purpose: Program shows a simulation of an ATM display
screen that asks the user for their PIN. The program
is vulnerable to a brute force entry that randomly guesses
the PIN until it finds the right one. The program also
provides a mitigation technique to limit how often a PIN
can be entered and locks the account after a certain
number of incorrect PINs have been guessed.
"""

import time
import secrets
import string

PIN_CORRECT = '4437'  # the correct PIN
attempt_num = 0  # number of brute force attempts
using_mitigation = True  # if set to False, will not employ brute force mitigation
pin_guess = ''  # start PIN as empty string
NUMBERS = string.digits  # constant for generating random number values


print("WELCOME TO THE ATM!\nPLEASE ENTER A PIN\n\n")


def brute_force(pin):
    """
    Takes in PIN as 4 character string and returns a
    randomly generated combination of 4 digits.
    """
    # PIN is 4 digits long
    characters = [secrets.choice(NUMBERS), secrets.choice(NUMBERS), secrets.choice(NUMBERS),
                  secrets.choice(NUMBERS)]
    for i in range(4):  # generates random PIN with secrets module
        pin += characters[secrets.randbelow(len(characters) - 1)]

    return pin


while True:
    if using_mitigation:
        time.sleep(1)  # limits attempts to once per second
        if attempt_num > 4:  # locks account after 5 failed login attempts
            print('Maximum attempts reached. Account locked.')
            break

    if brute_force(pin_guess) == PIN_CORRECT:
        print('PIN accepted')
        print('Attempt number:', attempt_num)
        break
    else:
        print(brute_force(pin_guess), 'Incorrect PIN')
        attempt_num += 1
