lambda = 1;

for num = 5:5%0:9
   disp(sprintf('Digit: %d',num));
   data = load('features.train');
   x = zeros(length(data), 2);
   x_sizes = zeros(1,10);
   j = 1;
   for digit = 0:9
      digit_examples = find(data(:,1) == digit);
      x_sizes(digit + 1) = length(digit_examples);
      for i = 1:x_sizes(digit + 1)
         x(j,:) = data(digit_examples(i),2:3);
         j += 1;
      end
   end
   N = sum(x_sizes);
   y = -ones(N,1);
   y(sum(x_sizes(1:num))+1:sum(x_sizes(1:num+1))) = ones(x_sizes(num+1),1);
   
   
   M = zeros(N, 6);
   for i = 1:length(data)
      %transform
      M(i,:) = [1, x(i,1), x(i,2),  x(i,1)*x(i,2), x(i,1)^2, x(i,2)^2];
   end
   
   g = pinv(M'*M + lambda*eye(size(M,2))) * M' * y; 
   
   err = 0.0;
   for i = 1:N
      err += (M(i,:) * g - y(i))^2;
   end
   in_error = err / N;
   in_error
   
   
   
   
   
   
   data = load('features.test');
   x = zeros(length(data), 2);
   x_sizes = zeros(1,10);
   j = 1;
   for digit = 0:9
      digit_examples = find(data(:,1) == digit);
      x_sizes(digit + 1) = length(digit_examples);
      for i = 1:x_sizes(digit + 1)
         x(j,:) = data(digit_examples(i),2:3);
         j += 1;
      end
   end
   N = sum(x_sizes);
   y = -ones(N,1);
   y(sum(x_sizes(1:num))+1:sum(x_sizes(1:num+1))) = ones(x_sizes(num+1),1);
   
   M = zeros(N, 6);
   for i = 1:length(data)
      %transform
      M(i,:) = [1, x(i,1), x(i,2),  x(i,1)*x(i,2), x(i,1)^2, x(i,2)^2];
   end
  
   err = 0.0;
   for i = 1:N
      err += (M(i,:) * g - y(i))^2;
   end
   out_error = err / N;
   out_error
   
end
