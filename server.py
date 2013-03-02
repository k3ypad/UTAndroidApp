#!/usr/bin/pyton

"""
Author: Ben Doyle - ben@tfa.ie

A simple server that listens for UT android app requests for wordpress articles, fetches them from database, returns them in desired format.

VERSION 0.1 - Listens for a string, returns first article html.
"""

import socket
import select
import time
import sys
import thread
import MySQLdb
### Connection Class ###
# A class that represents a connection from a client
class Connection:
    def __init__(self, connection, address):
        #TODO make dis
        self.client = connection #connection to client
        intake = self.client.recv(1024)
        if intake == 'h3ll0':
            #self.client.send(
            self.client.send(self.do_da_database_shake())
            
        self.client.close() #close link between server and client

    def do_da_database_shake(self):
        db = MySQLdb.connect('localhost', 'utdummyuser', 'utdummyuser123', 'utdummy')
        cur = db.cursor()

        cur.execute("SELECT post_content from wp_posts WHERE ID='10'")
        for row in cur.fetchall():
            result=row[0]

        print result
        return result



# Standalone function to start service
# Takes input params, starts listening for clients
# fires up a thread per clien
def start_service(port):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        server_socket.bind(('',port))
        server_socket.listen(0)
    except:
        print ("Couldn't bind to localhost on port:",port,"\n is something using this? \n Terminating")
        server_socket.close()
        sys.exit(-1)

    print ("Serving on port",port) #Confirm server is running

    #Serve requests on client connection
    while 1:
        try:
            thread.start_new_thread(Connection,server_socket.accept()) #On client fire up thread
        except Exception:
            print ("Error, possibly idle or thread lim reached")


### Main ###
if __name__ == '__main__':
    host=''
    port=8080 #TODO parse cmd line args

    try:
        start_service(port)
    except KeyboardInterrupt:
        print("Ctrl C - Stopping Server")
        sys.exit(1)
