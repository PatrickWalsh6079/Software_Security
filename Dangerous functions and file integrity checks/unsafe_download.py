"""
Filename: unsafe_download.py 
Author: Patrick Walsh
Date: 4/12/2021
Purpose: Program downloads file from a website and checks the integrity
of the file by verifying the MD5 hash checksum. The program also
encrypts the hash to protect the integrity of the hash value.
"""


# https://www.python.org/ftp/python/3.9.4/python-3.9.4-amd64.exe
# MD5 sum: ebc65aaa142b1d6de450ce241c50e61c


import hashlib
import urllib.request


# URL to .exe file to be downloaded
url = "https://www.python.org/ftp/python/3.9.4/python-3.9.4-amd64.exe"
 
# Downloads file and saves to specified directory
print("DOWNLOADING python-3.9.4-amd64.exe FROM PYTHON.ORG......\n\n")
urllib.request.urlretrieve(url, '/home/ec2-user/environment/Week_4/python_file.exe')

md5_hash = 'ebc65aaa142b1d6de450ce241c50e61c'  # hash of file as specified by website

# Creates hash of file contents
check_hash = hashlib.md5(open('/home/ec2-user/environment/Week_4/python_file.exe','rb').read()).hexdigest()


# Compare hash of file contents with known hash value of file
print("LET'S CHECK THE INTEGRITY OF DOWNLOADED FILE.....\n\n")

print("Unencrypted hash:")
print(md5_hash, "<--- hash from python.org")
print(check_hash, "<--- hash from downloaded file")
if md5_hash == check_hash:
    print("\nHashes match! Integrity check passed!")
else:
    print("\nHashes do NOT match! Integrity check failed!")
    
    
print("\n------------------------------------------------------------\n")


# function encrypts has values
def encrypted_hash(hash_string):
    md5_encrypt = \
        hashlib.md5(hash_string.encode()).hexdigest()
    return md5_encrypt


md5_hash_encrypted = encrypted_hash(md5_hash)  # function call
check_hash_encrypted = encrypted_hash(check_hash)  # function call


print("Encrypted hash:")
print(md5_hash_encrypted, "<--- encrypted hash from python.org")
print(check_hash_encrypted, "<--- encrypted hash from downloaded file")
if md5_hash_encrypted == check_hash_encrypted:
    print("\nEncrypted hashes match! Integrity check passed!")
else:
    print("\nnEncrypted hashes do NOT match! Integrity check failed!")

