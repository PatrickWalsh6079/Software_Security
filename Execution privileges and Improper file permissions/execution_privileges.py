"""
Filename: execution_privileges.py
Author: Patrick Walsh
Date: 4/21/2021
Purpose: Program shows an example of Execution with Unnecessary Privileges
as described in CWE-250 taken from the CWE's list of common software weaknesses.
It also shows how to mitigate this vulnerability.
"""

import os


# dictionary contains list of users and their corresponding passwords
users = {'user1':'pass1', 'user2':'pass2', 'user3':'pass3'}



def vulnerable_function(username, password):
    """
    Function takes in two arguments: username and password. 
    If username and password match what is in the users dictionary,
    the function executes some OS file tasks. Otherwise it exits with
    an error message.
    This function has a software vulnerability in that is raises system privileges
    without checking to make sure they are properly lowered every time. This 
    vulnerability is fixed in the function below safe_function().
    """
    if username in users and password == users[username]:
        
        privileges = "lowered"
        print("PRIVILEGES STATUS: " + privileges)
        
        try:
            print("Raising system privileges to execute tasks...")
            privileges = "raised"
            print("PRIVILEGES STATUS: " + privileges)
            root = "/home/ec2-user/environment/"
            path = root + username
            os.mkdir(path)  # create new directory with username
            with open(path + '/test.txt', 'x') as f:  # create file inside new directory
                f.write('Create a new text file!')
            print("\n.....tasks complete.....")
            print("\n...Lowering system privileges...")
            privileges = "lowered"
            print("PRIVILEGES STATUS: " + privileges)
        
        except OSError:
            print("Directory for " + username + " already exists.")
            print("PRIVILEGES STATUS: " + privileges)
            return False
    
    return True




def safe_function(username, password):
    """
    Function takes in two arguments: username and password. 
    If username and password match what is in the users dictionary,
    the function executes some OS file tasks. Otherwise it exits with
    an error message.
    This function fixes the software vulnerability as seen in vulnerable_function(). 
    It solves this by ensuring that system privileges are lowered each time the 
    function is executed.
    """
    if username in users and password == users[username]:
        
        privileges = "lowered"
        print("PRIVILEGES STATUS: " + privileges)
        
        try:
            print("Raising system privileges to execute tasks...")
            privileges = "raised"
            print("PRIVILEGES STATUS: " + privileges)
            root = "/home/ec2-user/environment/"
            path = root + username
            os.mkdir(path)  # create new directory with username
            with open(path + '/test.txt', 'x') as f:  # create file inside new directory
                f.write('Create a new text file!')
            print("\n.....tasks complete.....")
            print("\n...Lowering system privileges...")
            privileges = "lowered"
            print("PRIVILEGES STATUS: " + privileges)
        
        except OSError:
            print("Directory for " + username + " already exists.")
            # make sure privileges are lowered before exiting
            if privileges == "raised":
                privileges = "lowered"
            print("PRIVILEGES STATUS: " + privileges)
            return False
    
    return True


print("vulnerable_function():")   
vulnerable_function("user1", "pass1")

print("\n\nsafe_function():")
safe_function("user1", "pass1")

