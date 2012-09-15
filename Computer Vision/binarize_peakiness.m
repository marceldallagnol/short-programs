%inImage: image file name
%numBins: number of columns in histogram
%minDist: minimum distance between peaks

function outImage = binarize_peakiness(inImage,numBins,minDist)

i = imread(inImage);

if length(size(i)) == 3
    img = im2double(rgb2gray(i));
else
    img = im2double(i);
end

[histFreq histVals] = hist(img(:),numBins);
[sortedValues,sortIndex] = sort(histFreq(:),'descend');

first = 1;
second = 2;
maxPeak = 0;

while first < length(histFreq) && maxPeak < sortedValues(second)/sortedValues(length(sortedValues))
    while second <= length(histFreq) && maxPeak < sortedValues(second)/sortedValues(length(sortedValues))
        distance = sortIndex(second) - sortIndex(first);
        if abs(distance) >= minDist
            if distance > 0
                [valley valIndex] = min(histFreq(sortIndex(first):sortIndex(second)));
            else
                [valley valIndex] = min(histFreq(sortIndex(second):sortIndex(first)));
            end
            peak = sortedValues(second)/valley;
            if peak > maxPeak
                maxPeak = peak;
                divider = valIndex;
            end
        end
        second = second + 1;
    end
    first = first + 1;
    second = first + 1;
end

thresh = histVals(divider);
[height width] = size(img);
for x = 1:height
    for y = 1:width
        if img(x,y) > thresh
            img(x,y) = 1;
        else
            img(x,y) = 0;
        end
    end
end
%imshow(img)
outImage = img;
end