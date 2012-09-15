from random import uniform, randrange, sample
from numpy.random import permutation
from math import exp, log

def y_function(b):
   if b:
      return 1
   else:
      return -1

e_out = 0.0
iters = 0
for i in range(100):
   x1,y1,x2,y2 = (uniform(-1,1), uniform(-1,1), uniform(-1,1), uniform(-1,1))
   target = [(y2-y1)/(x2-x1)*x1-y1, -(y2-y1)/(x2-x1), 1]
   N = [(1,uniform(-1,1),uniform(-1,1)) for i in range(100)]
   classification = map(y_function, [(x[0]*target[0] + x[1]*target[1] + x[2]*target[2]) > 0 for x in N])
   w_new = [0,0,0]
   w_old = [0,0,0]
   error = 1
   while error > 0.01:
      perm = permutation(len(N))
      for n in perm:
         inner_p = sum(map(lambda x: x[0]*x[1], zip(N[n], w_new)))
         gradient = map(lambda x: -classification[n]*x/(1+exp(classification[n]*inner_p)), N[n])
         w_new = map(lambda x: x[0] - 0.01*x[1], zip(w_new, gradient))
      inner_ps = map(lambda n: sum(map(lambda x: x[0]*x[1], zip(n, w_new))), N)
      error = sum(map(lambda x: (x[0]-x[1])**2, zip(w_new, w_old)))**0.5
      w_old = w_new
      iters += 1
      
   N = [(1,uniform(-1,1),uniform(-1,1)) for i in range(1000)]
   classification = map(y_function, [(x[0]*target[0] + x[1]*target[1] + x[2]*target[2]) > 0 for x in N])
   approx_classification = map(y_function, [(x[0]*w_new[0] + x[1]*w_new[1] + x[2]*w_new[2]) > 0 for x in N])
   error = map(y_function, [classification[x] != approx_classification[x] for x in range(len(N))])
      
   e_out += sum(map(lambda x: (x + abs(x))/2, error))/1000.0
e_out /= 100
iters /= 100
print e_out, iters






















#from mpmath import *

#mp.dps = 100
#u,v = mpf(1.0),mpf(1.0)
#error = 1
#iterations = 0
#for i in range(15): #while error >= 10**-14:
#   u -= 0.1*(2*exp(-2*u)*(u*exp(v+u)-2*v)*(exp(v+u)+2*v))
#   v -= 0.1*(2*exp(-2*u)*(u*exp(v+u)-2)*(u*exp(v+u)-2*v))
#   u = u-0.1*(2*u*exp(2*v)-4*v*exp(v-u)+4*u*v*exp(v-u)-8*v*v*exp(-2*u))
#   v = v-0.1*(2*u*u*exp(2*v)-4*u*exp(v-u)-4*u*v*exp(v-u)+8*v*exp(-2*u))   
#   error = float((u*exp(v)-2*v*exp(-u))**2)
   #u = u_next
   #v = v_next
#   iterations += 1
   
#print float(u),float(v), error
#print iterations


