N = 100;
out_size = 3000;
runs = 1000;
out_sample = [ones(out_size,1) 2*rand(out_size,2)-1];
out_sample_svm = out_sample(:,2:size(out_sample)(2));
y_out = zeros(out_size,1);
avg_error = zeros(1,2);
iterations = 0;
svm_outperform = 0;
support_vectors = 0;

for i = 1:runs
   x1 = 2*rand(1)-1;
   x2 = 2*rand(1)-1;
   y1 = 2*rand(1)-1;
   y2 = 2*rand(1)-1;
   lin = [y1-(y2-y1)/(x2-x1)*x1, (y2-y1)/(x2-x1), 1];
   x = [ones(N,1) 2*rand(N,2)-1];
   y = zeros(N,1);
   for j = 1:N
      if lin * x(j,:)' > 0
         y(j) = 1;
      else
         y(j) = -1;
      end
   end
   for j = 1:out_size
      if lin * out_sample(j,:)' > 0
         y_out(j) = 1;
      else
         y_out(j) = -1;
      end
   end
   
   if sum(y + 1) ~= 0 && sum(y - 1) ~= 0
      g_pla = zeros(1,3);
      misclassified = 1:N;
      num_misclassified = N;
      
      % PLA
      while num_misclassified > 0
         pick = misclassified(ceil(rand(1) * num_misclassified));
         g_pla += y(pick) * x(pick,:);
         
         num_misclassified = 0;
         for j = 1:N
            if g_pla * x(j,:)' * y(j) < 0
               num_misclassified += 1;
               misclassified(num_misclassified) = j;
            end
         end
      end
      
      %SVM
      x_svm = x(:,2:size(x)(2));
      X0 = zeros(N,1);
      H = (y * y') .* (x_svm * x_svm');
      Q = -ones(N,1);
      A = y';
      B = 0;
      LB = zeros(N,1);
      alpha = qp(X0, H, Q, A, B, LB, [], [], zeros(1,N), []);
      g_svm = (alpha .* y)' * x_svm;
      pla_error = 0;
      svm_error = 0;
      for j = 1:out_sample
         if g_pla * out_sample(j,:)' * y_out(j) < 0
            pla_error += 1.0;
         end
         if g_svm * out_sample_svm(j,:)' * y_out(j) < 0
            svm_error += 1.0;
         end
      end
      if svm_error <= pla_error
         svm_outperform += 1.0;
      end
      for j = 1:length(alpha)
         if alpha(j) > 10^-9
            support_vectors += 1.0;
         end
      end
      avg_error += [pla_error, svm_error] / out_size;
      iterations += 1;
   end
end

svm_outperform /= iterations;
avg_error /= iterations;
support_vectors /= iterations;
avg_error
svm_outperform
support_vectors
