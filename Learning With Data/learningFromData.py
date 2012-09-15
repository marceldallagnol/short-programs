from random import uniform, randrange
from numpy.linalg import pinv
from numpy import matrix, array

#min_avg = 0
#for k in range(2000):
#   flips = []

#   for j in range(1000):
#      toss = 0
#      for i in range(10):
#         toss += randrange(2)
#      flips.append(toss)
#   print min(flips)
#   min_avg += min(flips)
#print flips
#print min_avg / 2000.0


def y_function(b):
   if b:
      return 1
   else:
      return -1
      
def y_function2(b):
   if float(b[0]) > 0:
      return 1
   else:
      return -1
      
def run():
   error = 0
   final_g = [0 for i in range(7)]
   max_err = 100000000000000000000
   for i in range(1000):
      #error = 0
      p = [(uniform(-1,1), uniform(-1,1)), (uniform(-1,1), uniform(-1,1))]
      target = [-p[0][0]+(p[0][0]-p[1][0])*p[1][0]/(p[1][0]-p[1][1]), 1, -(p[0][0]-p[1][0])/(p[1][0]-p[1][1])]
      N = [(1,uniform(-1,1),uniform(-1,1)) for i in range(1000)]
      classification = map(y_function, [(x[1]**2 + x[2]**2 - 0.6) > 0 for x in N])
      flipped_nums = []
      for j in range(100):
         flip = randrange(0,1000)
         while flip in flipped_nums:
            flip = randrange(0,1000)
         flipped_nums.append(flip)
      for f in flipped_nums:
         classification[f] = -classification[f]
      
      M = [(1, x[1], x[2], x[1]*x[2], x[1]**2, x[2]**2) for x in N]
      g = matrix.transpose(matrix(pinv([list(m) for m in M]))*matrix([[float(c)] for c in classification]))
     
      N = [(1,uniform(-1,1),uniform(-1,1)) for i in range(1000)]
      classification = map(y_function, [(x[1]**2 + x[2]**2 - 0.6) > 0 for x in N])
      flipped_nums = []
      for j in range(100):
         flip = randrange(0,1000)
         while flip in flipped_nums:
            flip = randrange(0,1000)
         flipped_nums.append(flip)
      for f in flipped_nums:
         classification[f] = -classification[f]
      
      M = [(1, x[1], x[2], x[1]*x[2], x[1]**2, x[2]**2) for x in N]
      g_classification = matrix(map(y_function2, [g*matrix.transpose(matrix(m)) for m in M]))
      #final_g = [a + b for (a,b) in zip(final_g,[x/abs(g.tolist()[0][0]) for x in g.tolist()[0]])]
      errors = [x != t for (x,t) in zip(g_classification.tolist()[0], classification)]
      for e in errors:
         if e:
            error += 1
      #if error < max_err:
      #   max_err = error
      #   final_g = g
      #   print [x/abs(g.tolist()[0][0]) for x in g.tolist()[0]]
   error /= 1000.0 * 1000.0
   print error
   #print final_g



def y_function(b):
   if b:
      return 1
   else:
      return -1
     
iterations = 0
error = 0
for i in range(10000):
   p = [(uniform(-1,1), uniform(-1,1)), (uniform(-1,1), uniform(-1,1))]
   target = [1, -(p[0][0]-p[1][0])/(p[1][0]-p[1][1]), -p[0][0]+(p[0][0]-p[1][0])*p[1][0]/(p[1][0]-p[1][1])]
   N = [(uniform(-1,1),uniform(-1,1),1) for i in range(10)]
   classification = map(y_function, [(x[0]*target[0] + x[1]*target[1] + x[2]*target[2]) > 0 for x in N])
   g = matrix.transpose(matrix(pinv([list(n) for n in N]))*matrix([[float(c)] for c in classification])).tolist()[0]
   g_classification = map(y_function, [(x[0]*g[0] + x[1]*g[1] + x[2]*g[2]) > 0 for x in N])
   
   while g_classification != classification:
      #print g
     
      incorrect = [p for p in N if classification[N.index(p)] != g_classification[N.index(p)]]
      #print len(incorrect)
      index = randrange(0,len(incorrect))
      #print [classification[N.index(incorrect[index])] * x for x in incorrect[index]]
      g = [p[0] + p[1] for p in zip(g, [classification[N.index(incorrect[index])] * x for x in incorrect[index]])]
      g_classification = map(y_function, [(x[0]*g[0] + x[1]*g[1] + x[2]*g[2]) > 0 for x in N])
      iterations += 1
      #print classification[N.index(incorrect[index])]
      #print incorrect[index], N.index(incorrect[index])
      #print classification, g_classification
      
#      raw_input('Press any key...')
#   for j in range(1000):
#      x = [uniform(-1,1), uniform(-1,1), 1]
#      if ((x[0]*target[0] + x[1]*target[1] + x[2]*target[2]) > 0) != ((x[0]*g[0] + x[1]*g[1] + x[2]*g[2]) > 0):
#         error += 1
iterations /= 10000.0
#error /= 1000000.0
print iterations


