"""
Name: url_redirect.py
Author: Patrick Walsh
Date: 3/29/2021
Purpose: Creates flask web application to run from a web browser, resulting
in a simple website that gives examples of Open Redirects.

To run the code, you need to set an environmental variable aligned with the file we just created
with the Flask app, and then run flask. In command prompt, navigate to the folder where this file
is located, then run the following commands:

set FLASK_APP=url_redirect.py
flask run

In Powershell:

$env:FLASK_APP = "url_redirect"
flask run
"""


from flask import Flask
from flask import render_template


app = Flask(__name__)  # creates instance of flask class


@app.route('/')  # function decorator shows path of URL
def main():
    """
    Loads main webpage
    """
    return render_template('main.html')


@app.route('/fake_google')
def fake():
    """
    Loads fake_google webpage
    """
    return render_template('fake_google.html')
