#!/usr/bin/pyton

"""
Author: Ben Doyle - ben@tfa.ie

A simple server that listens for UT android app requests for wordpress articles, fetches them from database, returns them in desired format.

VERSION 0.2 - Returns headline data with post ID
"""

import socket
import select
import time
import sys
import thread
import MySQLdb
import re
### Connection Class ###
# A class that represents a connection from a client
class Connection:
    def __init__(self, connection, address):
        self.client = connection #connection to client
        intake = self.client.recv(1024)
        
        #If statements that respond to requests based on protocol
        if intake == 'h3ll0':
            #self.client.send(
            self.client.send(self.do_da_database_shake())
        elif intake == 'recent_headline_plz':
            self.client.send(self.get_recent_headline())

            
        self.client.close() #close link between server and client
    
# Initial test function for testing mysql connections
    def do_da_database_shake(self):
        db = MySQLdb.connect('localhost', 'utdummyuser', 'utdummyuser123', 'utdummy')
        cur = db.cursor()

        cur.execute("SELECT post_content from wp_posts WHERE ID='10'")
        for row in cur.fetchall():
            result=row[0]

        print result
        return result


# Function that returns most recent headline - thumbnail pic, title
    def get_recent_headline(self):
        db = MySQLdb.connect('localhost', 'utdummyuser', 'utdummyuser123', 'utdummy')
        cur = db.cursor()

        #Find the most recent article
        cur.execute("SELECT ID from wp_posts WHERE post_status='publish'")
        number_of_ids = int(cur.rowcount)
        most_recent=0
        for i in range(number_of_ids):
            row = cur.fetchone()
           #debug  print row[0]
            if row[0] > most_recent:
                most_recent = row[0]
        
        #Prepare the headline according to the protocol
        #debug print "max = ",most_recent
        cur.execute("SELECT post_title from wp_posts WHERE ID=%s",(most_recent))
        result = '/*WP2A---HEADLINE*/'
        for row in cur.fetchall():
            result+=row[0]
            #debug print "returning headline, most recent post ID = ",result
        result += '/*WP2A---ENDHEAD*//*WP2A---ID*/'+str(most_recent)+'/*WP2A---ENDID*/'

       #grab thumbnail for article, wordpress auto genereates a 150x150 which will be used for this, it is in www.example.com/wp-content/uploads/theme/some number/original_image_name-150x150.jpg
        #grab post_content for url to original image
        cur.execute("SELECT post_content from wp_posts WHERE ID='%s'",(most_recent))
        for row in cur.fetchall():
            post_data=row[0]

        #grab 150x150 thumbnail from result
        match = re.search(r'^.*src="(.*)-\d\d\dx\d\d\d\.jpg".', post_data)
        if match:
            result += '/*WP2A---THUMB*/' +str(match.group(1)) + '-150x150.jpg/*WP2A---ENDTHUMB*/'
        else: #if no article has image, return this ut placeholder 150x150.jpg
            result += '/*WP2A---THUMB*/' + 'http://utdummy.b.tfa.ie/wp-content/uploads/2013/utlogo.jpg' + '/*WP2A---ENDTHUMB*/'
        print result
        return result

###END CONNECTION CLASS


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
