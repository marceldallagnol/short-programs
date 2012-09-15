gamm = 1.5;
runs = 1000;
N = 100;
N_test = 1000;
k = 9;
nonlinear_runs = 0;
rbf_outperform = 0;
out_total = 0;
in_total = 0;

for i = 1:runs
      
   x = 2*rand(N,2) - 1;
   y = x * [-1;1] + 0.25 * sin(pi*x(:,1));
   y ./= abs(y);
   

      [w, mu] = rbf(x, y, k, gamm);
      phi = zeros(N, k);
      for j = 1:k
         phi(:,j) = exp(-gamm * sum((x - repmat(mu(j,:), N, 1)).^2,2));
      end
      g_rbf = phi * w;
      g_rbf ./= abs(g_rbf);
      in_rbf = sum(g_rbf ~= y) / length(y);
      in_total += in_rbf;
      
      % Out of sample
      x_old = x;
      y_old = y;
      x = 2*rand(N_test,2) - 1;
      y = x * [-1;1] + 0.25 * sin(pi*x(:,1));
      y ./= abs(y);

      % RBF
      phi = zeros(N_test, k);
      for j = 1:k
         phi(:,j) = exp(-gamm * sum((x - repmat(mu(j,:), N_test, 1)).^2,2));
      end
      g_rbf = phi * w;
      g_rbf ./= abs(g_rbf);
      out_rbf = sum(g_rbf ~= y) / length(y);
      out_total += out_rbf;

end

out_total /= runs - nonlinear_runs;
in_total /= runs - nonlinear_runs;
in_total
out_total
