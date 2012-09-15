%imageIn: name of input file
%k: size of the structural element (matrix 2k+1 by 2k+1)
%n: number of iterations the algorithm is applied
%choice: erosion (0) or dilation (1)

function outImage = morphological(imageIn,k,n,choice)
img = imageIn;
strucEl = ones(2*k+1);
[height width] = size(img);
outImage = zeros(height,width);

for i = 1:n
    for x = k+1:height-k
        for y = k+1:width-k
            if sum(sum(strucEl .* img(x-k:x+k,y-k:y+k))) == (~choice)*(2*k+1)^2
                outImage(x,y) = ~choice;
            else
                outImage(x,y) = choice;
            end
        end
    end
    img = outImage;
end
end