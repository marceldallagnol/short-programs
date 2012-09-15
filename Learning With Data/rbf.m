function [w, mu] = rbf(x, y, K, gamm)
   mu = lloyd(K, x);
   N = size(x,1);
   phi = zeros(N, K);
   for i = 1:K
      phi(:,i) = exp(-gamm * sum((x - repmat(mu(i,:), N, 1)).^2,2));
   end
   
   w = pinv(phi' * phi) * phi' * y;
end
