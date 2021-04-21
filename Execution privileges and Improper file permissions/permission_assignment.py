"""
Filename: permission_assignment.py
Author: Patrick Walsh
Date: 4/21/2021
Purpose: Program shows an example of Incorrect Permission Assignment for Critical Resource
as described in CWE-732 taken from the CWE's list of common software weaknesses.
It also shows how to mitigate this vulnerability.
"""

import os
import stat
from stat import S_IWUSR, S_IREAD, S_IRGRP, S_IROTH

# set to True to give file 'safe' permissions, i.e. read/write for owner, and only readable for group and others
# set to False to give file 'unsafe' permissions, i.e. readable, writeable, and executable by everyone
safe_permissions = True  


def create_file(file_name):
    """
    Takes in a filename as an argument, creates the file and sets its permissions.
    """
    
    try:
        with open(file_name, 'x') as f:  # create file inside current directory
                    f.write('This is a line of text!')
                    f.write('\nHere is a second line of text!')
                    
        if safe_permissions:
            os.chmod(file_name, S_IWUSR|S_IREAD|S_IRGRP|S_IROTH)  # sets file to be read/write for owner, and only readable for group and others
        else:
            os.chmod(file_name, 0o777)  # set file to be readable, writeable, and executable by everyone
            
        print("File created: "+ file_name)
        
    except OSError:
        print("Failed to create file: " + file_name)
        
    return 0
    
    
    
def check_permissions(file_name):
    """
    Takes in a filename as an argument and checks the permissions of that file 
    for user, group, and others.
    """

    exists = os.access(file_name, os.F_OK)  # check if file exists

    if exists:
        # checks permissions only for current user
        # read = os.access(file_name, os.R_OK)
        # write = os.access(file_name, os.W_OK)
        # execute = os.access(file_name, os.X_OK)
        # print(file_name + " exists!")
        # print("\nCan " + file_name + ":")
        # print("\n...be read? ", read)
        # print("\n...be written to? ", write)
        # print("\n...be executed? ", execute)
        st = os.stat(file_name)
        
        print("\n-----PERMISSIONS-----\n")
        
        print("Owner:")
        # print(st.st_mode & stat.S_IRWXU)  # rwx by owner
        read_owner = bool(st.st_mode & stat.S_IRUSR)  # r by owner
        write_owner = bool(st.st_mode & stat.S_IWUSR)  # w by owner
        exe_owner = bool(st.st_mode & stat.S_IXUSR)  # x by owner
        print("Readable: ", read_owner)
        print("Writeable: ", write_owner)
        print("Executeable: ", exe_owner)
        
        print("\nGroup:")
        # print(stat.S_IRWXG)  # rwx by group
        read_group = bool(st.st_mode & stat.S_IRGRP)  # w by group
        write_group = bool(st.st_mode & stat.S_IWGRP)  # w by group
        exe_group = bool(st.st_mode & stat.S_IXGRP)  # x by group
        print("Readable: ", read_group) 
        print("Writeable: ", write_group)
        print("Executeable: ", exe_group)
        
        print("\nOthers:")
        # print(stat.S_IRWXO)  # rwx by others
        read_others = bool(st.st_mode & stat.S_IROTH)  # r by others
        write_others = bool(st.st_mode & stat.S_IWOTH)  # w by others
        exe_others = bool(st.st_mode & stat.S_IXOTH)  # x by others
        print("Readable: ", read_others)
        print("Writeable: ", write_others)
        print("Executeable: ", exe_others)
        
        
        
    else:
        print(file_name + " does not exist")
        
    return 0
    
    
    
create_file("test2")  # creates file and sets permissions

check_permissions("test2")  # checks file permissions
