I = imread('pic.png');     % For the other picture, replace with 'pic.png'
I = imresize(I,[256 256],'bil');
red = I;
red(:,:,2:3) = 0;
green = I;
green(:,:,1:2:3) = 0;
blue = I;
blue(:,:,1:2) = 0;
displayImage = [I green; red blue];
imshow(displayImage);