import random


def call_me():
    a = "ABCDE"
    integer = random.randint(1000, 9999)
    newword = '%s%s' % (a, integer)+'Z'
    print(newword)


call_me()
