from subprocess import call
from datetime import datetime
from random import randrange
from shutil import copy

for i in range(100000):
   test = list(open('front_base.pdf','rb').read())
   test[randrange(len(test))] = '%c' % randrange(256)
   test = ''.join(test)
   front = open('front.pdf', 'w')
   front.write(test)
   front.close()
   call('pdftk front.pdf background back.pdf output out.pdf'.split())
   copy('front.pdf', str(i) + '_' + str(datetime.now()))
   print i
