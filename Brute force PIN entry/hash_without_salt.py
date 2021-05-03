"""
Filename: hash_without_salt.py
Author: Patrick Walsh
Date: 5/2/2021
Purpose: Program generates a hash of a password then compares the hash with
a stored hash value. The program has the option of adding 'salt' to the hash 
to make it less vulnerable to brute force or dictionary attacks. Set variable
using_salt to True to add salt. The program can also generate a secure random 
string to be adding as salt to the hash.
"""

import hashlib
import secrets
import string

# hash values stored in imaginary database of password, both with and without salt.
stored_hash = 'b9c950640e1b3740e98acb93e669c65766f6670dd1609ba91ff41052ba48c6f3'
stored_hash_with_salt = 'b9c950640e1b3740e98acb93e669c65766f6670dd1609ba91ff41052ba48c6f310f4bb4ed3c2f654b83cc53df31b3a22a020b8c3dd5be7823eb4159102685dd9'

password = 'password1234'  # plaintext password
salted = 's0m3s@lty1n3$$'  # plaintext salt (should differ for each password)

using_salt = True  # set to True to use hashed password with salt

'''
# variables for generating random secure salt
LOWERCASE_LETTERS = string.ascii_letters[:26]
UPPERCASE_LETTERS = string.ascii_letters[26:]
NUMBERS = string.digits
SPECIAL_CHARACTERS = string.punctuation

def pour_salt():
    """
    Generates random salt and hashes it.
    """
    salt = ''
    
    for i in range(10):
        characters = [secrets.choice(LOWERCASE_LETTERS), secrets.choice(UPPERCASE_LETTERS), secrets.choice(NUMBERS), secrets.choice(SPECIAL_CHARACTERS)]
        salt += characters[secrets.randbelow(len(characters))]
    
    return salt
salted = pour_salt()
'''


def hashed_password(password):
    """
    Takes in string password and generates a hash of that password.
    """
    sha256_hash = \
        hashlib.sha256(password.encode()).hexdigest()
    # Appends the hashed salt at the end of hashed password if using_salt is set to True.
    if using_salt:
        hashed_salt = hashlib.sha256(salted.encode()).hexdigest()
        sha256_hash = sha256_hash + hashed_salt
    return sha256_hash


hashed = hashed_password(password)  # hashed_password() function called with password as input.


# Check to see if hashed password matches what is in database.
# Checks both salted and unsalted password hashes, depending on if using_salt is set to True.
if using_salt:
    if hashed == stored_hash_with_salt:
        print('WITH SALT')
        print('Stored hash:     ' + stored_hash_with_salt)
        print('Hashed password: ' + hashed)
        print('Password hashes match!')
    else:
        print('WITH SALT')
        print('Stored hash:     ' + stored_hash_with_salt)
        print('Hashed password: ' + hashed)
        print('Password hashes do not match.')
else:
    if hashed == stored_hash:
        print('WITHOUT SALT')
        print('Stored hash:     ' + stored_hash)
        print('Hashed password: ' + hashed)
        print('Password hashes match!')
    else:
        print('WITHOUT SALT')
        print('Stored hash:     ' + stored_hash)
        print('Hashed password: ' + hashed)
        print('Password hashes do not match.')    


