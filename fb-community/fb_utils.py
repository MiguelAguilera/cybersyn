#!/usr/bin/env python
# -*- coding: utf-8 -*-

from urllib2 import urlopen, URLError, HTTPError
from simplejson import loads
import time


def get_access_keys_from_file(filename='fb.keys'):
    with open(filename) as f:
        FACEBOOK_APP_ID = f.readline().strip("\n")
        FACEBOOK_APP_SECRET = f.readline().strip("\n")
    return (FACEBOOK_APP_ID, FACEBOOK_APP_SECRET)


def get_access_token_from_file(filename='fb.token'):
    with open(filename) as f:
        accessToken = f.readline().strip("\n")
    return accessToken


def get_json(url):
    read = False
    jsonContent = {}
    while not read:
        count = 0
        try:
            content = urlopen(url)
            jsonContent = loads(content.read())
            read = True
        except URLError as e:
            print 'URLError', e
            if count >= 10:
                print e
                jsonContent = None
                print 'Waiting for 300s'
                time.sleep(300)
                read = True
            else:
                print 'Waiting for 60s'
                count += 1
                time.sleep(60)

        except HTTPError as e:
            print 'HTTPError', e
            if count >= 10:
                print e.getcode()
                jsonContent = None
                print 'Waiting for 300s'
                time.sleep(300)
                read = True
            else:
                print 'Waiting for 60s'
                count += 1
                time.sleep(60)
    return jsonContent
