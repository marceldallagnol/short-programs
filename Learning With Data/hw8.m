for c = [0.01 0.1 1 10 100]
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
      N = x_sizes(1) + x_sizes(5);
      x = [x(1:x_sizes(1),:); x(sum(x_sizes(1:4))+1:sum(x_sizes(1:5)),:)];


      printf('\n\n C = %f\n\n', c);
      y = ones(N,1);
      y(x_sizes(1)+1:length(y)) = -ones(x_sizes(5),1);
      options = sprintf('-t 1 -d 2 -v 10 -q -g 1 -r 1 -c %f -h 0', c);
      g_svm = svmtrain(y, x, options)(1);
      
      disp('Ein');
      svmpredict(y, x, g_svm);
      
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
      N = x_sizes(1) + x_sizes(5);
      x = [x(1:x_sizes(1),:); x(sum(x_sizes(1:4))+1:sum(x_sizes(1:5)),:)];
      y = ones(N,1);
      y(x_sizes(1)+1:length(y)) = -ones(x_sizes(5),1);
      
      disp('Eout');
      svmpredict(y, x, g_svm);
end
