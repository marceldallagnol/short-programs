from random import uniform, randrange
from numpy.linalg import pinv
from numpy import matrix, array


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

k = 0
in_error = 0
out_error = 0
in_sample = open('in.dta', 'r').readlines()
out_sample = open('out.dta', 'r').readlines()
N = []
classification = []
for line in in_sample: 
   x1,x2,y = map(lambda x: float(x), line.split())
   N.append([1,x1,x2])
   classification.append(y)

M = matrix([[1, x[1], x[2], x[1]*x[2], x[1]**2, x[2]**2, x[1]*x[2], abs(x[1]-x[2]), abs(x[1]+x[2])] for x in N])
#xs = [x*0.01 for x in range(100)]
#M = matrix([[1,x,x+0.0000001] for x in xs] + [[1,x,x-0.0000001] for x in xs])
#classification = [1 for x in range(100)] + [-1 for x in range(100)]
I = []
for i in range(len(M[0])):
   row = []
   for j in range(len(M[0])):
      if i == j:
         row.append(1.0)
      else:
         row.append(0.0)
   I.append(row)
I = matrix(I)
print (M.transpose()*M).diagonal(0)
print (M.transpose()*M+(float(10**k)*I)).diagonal(0)
g = (pinv(M.transpose()*M+(float(10**k)*I))*M.transpose()*matrix([[float(c)] for c in classification])).transpose()
print g
   
g_classification = matrix(map(y_function2, [g*matrix.transpose(matrix(m)) for m in M]))
errors = [x != t for (x,t) in zip(g_classification.tolist()[0], classification)]
for e in errors:
   if e:
      in_error += 1
in_error /= float(len(errors))
print in_error




N = []
classification = []
for line in out_sample:
   x1,x2,y = map(lambda x: float(x), line.split())
   N.append([1,x1,x2])
   classification.append(y)
    
M = [[1, x[1], x[2], x[1]*x[2], x[1]**2, x[2]**2, x[1]*x[2], abs(x[1]-x[2]), abs(x[1]+x[2])] for x in N]
   
g_classification = matrix(map(y_function2, [g*matrix.transpose(matrix(m)) for m in M]))
errors = [x != t for (x,t) in zip(g_classification.tolist()[0], classification)]
for e in errors:
   if e:
      out_error += 1
out_error /= float(len(errors))
print '%.9f'%out_error
