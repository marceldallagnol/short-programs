im = zeros(401,201);
i = 1;
for x = -1:0.005:1
    j = 1;
    for y = -.5:0.005:.5
        z = sqrt(x^2 + 4*y^2);
        if z <= 1
            normal = [-2*x -8*y 2*z];
            if z == 0
                normal = [0 0 1];
            end
            normal = normal/norm(normal);
            source1 = [1 1 2] - [x y z];
            source1 = source1/norm(source1);
            source2 = [-1 -1 2] - [x y z];
            source2 = source2/norm(source2);
            sum1 = 0;
            sum2 = 0;
            for k = 1:3
                sum1 = sum1 + normal(k)*source1(k);
                sum2 = sum2 + normal(k)*source2(k);
            end
            im(i,j) = .5 * sum2 + .5 * sum1;
        end
        j = j + 1;
    end
    i = i + 1;
end