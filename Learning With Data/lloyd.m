function mu = lloyd(K, x)
   N = size(x,1);
   S_sizes = zeros(K,1);
   mu = (max(x(:)) - min(x(:))) * rand(K, size(x,2)) + min(x(:));
   prev_mu = zeros(K, size(x,2));
   S = zeros(K, size(x,1));
   S_sizes = zeros(K,1);
   for i = 0:N-1
      S(mod(i,K) + 1, floor(i/K) + 1) = i + 1;
      S_sizes(mod(i,K) + 1) += 1;
   end
       
   while any(mu ~= prev_mu)
      prev_mu = mu;
      mu = zeros(K, size(x,2));
      for i = 1:K
         for j = 1:S_sizes(i)
            mu(i,:) +=  x(S(i,j),:);
         end
         if S_sizes(i)
            mu(i,:) /= S_sizes(i);
         end
      end
      S_sizes = zeros(K,1);
      for i = 1:N
         min_dist = [0, Inf];
         for j = 1:K
            dist = sqrt(sum((x(i,:) - mu(j,:)).^2));
            if dist < min_dist(2)
               min_dist = [j, dist];
            end
         end
         S_sizes(min_dist(1)) += 1;
         S(min_dist(1), S_sizes(min_dist(1))) = i;
      end
   end
end
